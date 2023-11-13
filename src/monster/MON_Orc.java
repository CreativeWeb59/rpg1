package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;

    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Orc";
        type = type_monster;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 85;

        getImage();
        getAttackImage();
    }
    public void getImage(){
        up1 = setup("/resources/monster/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/resources/monster/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/monster/orc_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/monster/orc_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/resources/monster/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/resources/monster/orc_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/resources/monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/resources/monster/orc_right_2", gp.tileSize, gp.tileSize);
    }
    public void getAttackImage(){
        attackUp1 = setup("/resources/monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/resources/monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/resources/monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/resources/monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/resources/monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/resources/monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/resources/monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/resources/monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }
    public void setAction(){
        if(onPath == true){
            // si le joueur s'écarte de 20 cases
            // le monstre perd l'aggro
            checkStopChasingOrNot(gp.player, 1520, 100);

            // mode suivi du joueur
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            } else {
            checkStartChasingOrNot(gp.player, 5, 100);
            getRandomDirection();

            // check if it attacks
            if(attacking == false){
                checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
            }
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
//        direction = gp.player.direction;    // s'écarte du joueur en prenant la direction oposée au joueur
        onPath = true;
    }

    @Override
    public void checkDrop() {
        // cast a die
        int i = new Random().nextInt(100)+1;

        // set the monster drop
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75 && i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }

    }
}
