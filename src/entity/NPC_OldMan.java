package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{


    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
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
        dialogues[0] = "Salut, lad.";
        dialogues[1] = "Alors, tu viens sur cette île pour trouver\nle trésor ?";
        dialogues[2] = "J'étais le meilleur mage... mais\nmaintenant je suis trop vieux pour\ncontinuer l'aventure.";
        dialogues[3] = "Bien, bonne chance à toi.";
    }
    public void setAction(){
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
    public void speak(){
        super.speak();
    }
}
