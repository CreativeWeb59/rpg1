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
    public Entity getObject(String itemName){
        Entity obj = null;
        switch (itemName){
            case "Hache simple": obj = new OBJ_Axe(gp); break;
            case "Bottes": obj = new OBJ_Boots(gp); break;
            case "Clé": obj = new OBJ_Key(gp); break;
            case "Lanterne": obj = new OBJ_Lantern(gp); break;
            case "Potion rouge": obj = new OBJ_Potion_Red(gp); break;
            case "Bouclier bleue": obj = new OBJ_Shield_Blue(gp); break;
            case "Bouclier en bois": obj = new OBJ_Shield_Wood(gp); break;
            case "Epée": obj = new OBJ_Sword_Normal(gp); break;
            case "Tente": obj = new OBJ_Tent(gp); break;
            case "Coffre": obj = new OBJ_Chest(gp); break;
        }
        return obj;
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
                gp.player.inventory.add(getObject(ds.itemsNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemsAmounts.get(i);
            }

            // write the dataStorage object

        } catch (Exception e){
            System.out.println("Save Exception");
        }
    }
}
