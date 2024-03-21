package entity;

import main.GamePanel;
import object.OBJ_Door_iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class NPC_BigRock extends Entity {
    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gp) {
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
        up2 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
        down2 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
        left1 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
        left2 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
        right1 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
        right2 = setup("/resources/npc/bigrock", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "C'est un gros rocher";
//        dialogues[2][1] = "";
//        dialogues[2][2] = "";
//        dialogues[2][3] = "";
    }

    public void setAction() {

    }

    public void update() {

    }

    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;

        if (dialogues[dialogueSet][0] == null) {
            dialogueSet--;
        }

//        onPath = true;
    }

    /**
     * deplace l'objet en fonction de la direction du joueur
     *
     * @param d pour direction du joueur
     */
    public void move(String d) {
        this.direction = d;
        checkCollision();
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        detectPlate();
    }

    /**
     * detecte la collision avec la plaque
     */
    public void detectPlate() {
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rocklist = new ArrayList<>();

        // create a plate list
        for (int i = 0; i < gp.iTile[1].length; i++) {
            if (gp.iTile[gp.currentMap][i] != null &&
                    gp.iTile[gp.currentMap][i].name != null &&
                    gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }
        // create a rock list
        for (int i = 0; i < gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {
                rocklist.add(gp.npc[gp.currentMap][i]);
            }
        }
        int count = 0;

        // scan the plate list
        for (int i = 0; i < plateList.size(); i++) {
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);

            // ecart entre la plaque et le rocher
            if (distance < 8) {
                if (linkedEntity == null) {
                    linkedEntity = plateList.get(i);
                    gp.playSE(3);
                    System.out.println("Rocher placé");
                }
            } else {
                if (linkedEntity == plateList.get(i)) {
                    linkedEntity = null;
                }
            }
        }

        // scan the rock list
        for (int i = 0; i < rocklist.size(); i++) {
            // count the rock on a plate
            if (rocklist.get(i).linkedEntity != null) {
                count++;
            }
        }
        // if all the rocks are on the plates, the iron door opens
        if(count == rocklist.size()){
            for (int i = 0; i < gp.obj[1].length; i++) {
                if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_iron.objName)){
                    gp.obj[gp.currentMap][i] = null;
                    gp.playSE(21);
                    System.out.println("Ouverture de la porte");
                }
            }
        }
    }
}
