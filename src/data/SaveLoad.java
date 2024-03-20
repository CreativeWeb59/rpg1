package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }
    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();
            // player stats
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.maxMana = gp.player.maxMana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            // player inventory
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemsNames.add(gp.player.inventory.get(i).name);
                ds.itemsAmounts.add(gp.player.inventory.get(i).amount);
            }

            // player equipment
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

            // objets sur la carte
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if(gp.obj[mapNum][i] == null){
                        ds.mapObjectNames[mapNum][i] = "NA";
                    } else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        if(gp.obj[mapNum][i].loot != null){
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }

                }
            }

            // write the dataStorage object
            oos.writeObject(ds);

        } catch (Exception e){
            System.out.println("Save Exception");
        }

    }
    public void load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            DataStorage ds = (DataStorage)ois.readObject();

            // player stats
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.maxMana = ds.maxMana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            // player inventory
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemsNames.size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemsNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemsAmounts.get(i);
            }

            // player equipment
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();

            // objets sur la carte
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                        if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                            gp.obj[mapNum][i] = null;
                        } else {
                            gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                            gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                            gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                            if (ds.mapObjectLootNames[mapNum][i] != null) {
                                gp.obj[mapNum][i].loot = gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]);
                            }
                            gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                            if (gp.obj[mapNum][i].opened) {
                                gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                            }
                        }
                }
            }

            // write the dataStorage object

        } catch (Exception e){
            System.out.println("Save Exception");
        }
    }
}
