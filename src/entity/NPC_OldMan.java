package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{


    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
    }
    public void getImage(){
        up1 = setup("/resources/npc/oldman_up_1");
        up2 = setup("/resources/npc/oldman_up_2");
        down1 = setup("/resources/npc/oldman_down_1");
        down2 = setup("/resources/npc/oldman_down_1");
        left1 = setup("/resources/npc/oldman_left_1");
        left2 = setup("/resources/npc/oldman_left_1");
        right1 = setup("/resources/npc/oldman_right_1");
        right2 = setup("/resources/npc/oldman_right_1");
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
}
