package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 2;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }
    public void getImage(){
        up1 = setup("/resources/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/resources/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/resources/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/resources/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/resources/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/resources/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/resources/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }
    public void setDialogue(){
        dialogues[0][0] = "Salut, lad.";
        dialogues[0][1] = "Alors, tu viens sur cette île pour trouver\nle trésor ?";
        dialogues[0][2] = "J'étais le meilleur mage... mais\nmaintenant je suis trop vieux pour\ncontinuer l'aventure.";
        dialogues[0][3] = "Bien, bonne chance à toi.";

        dialogues[1][0] = "Si tu te sens épuisé, repose toi près de l'eau";
        dialogues[1][1] = "Toutefois, les monstres réapparaissent si tu te reposes.\nlJe ne sais pas pourquoi, mais c'est comme ça.";
        dialogues[1][2] = "J'étais le meilleur mage... mais\nmaintenant je suis trop vieux pour\ncontinuer l'aventure.";
        dialogues[1][3] = "Dans tous les cas, fais attention.";

        dialogues[2][0] = "Je me demande comment on ouvre une porte...";
//        dialogues[2][1] = "";
//        dialogues[2][2] = "";
//        dialogues[2][3] = "";
    }
    public void setAction(){
        if(onPath == true){
            // mode find chemin
            // int goalCol = 12;
            // int goalRow = 9;

            // mode suivi du joueur
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter ++;

            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100)+1; // random from 1 to 100
                if(i <=25){
                    direction = "up";
                }
                if (i > 25 && i <= 50){
                    direction = "down";
                }
                if (i > 50 && i <= 75){
                    direction = "left";
                }
                if (i > 75 && i <= 100){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    public void speak(){
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null){
            dialogueSet--;
        }

//        onPath = true;
    }
}
