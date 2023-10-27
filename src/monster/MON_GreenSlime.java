package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = 2;
        speed = 1;
        maxLife = 20;
        life = maxLife;
        attack = 5;
        defense = 0;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();

    }
    public void getImage(){
        up1 = setup("/resources/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/resources/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/resources/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/resources/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/resources/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/resources/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
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
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
