package monster;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    GamePanel gp;
    public static final String monName = "Skeleton Lord";

    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = monName;
        type = type_monster;
        boss = true;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        knockBackPower = 5;
        sleep = true;

        int size = gp.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage() {
        int i = 5;

        if (!inRage) {
            up1 = setup("/resources/monster/skeletonlord_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/resources/monster/skeletonlord_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/resources/monster/skeletonlord_down_1", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/resources/monster/skeletonlord_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/resources/monster/skeletonlord_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/resources/monster/skeletonlord_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/resources/monster/skeletonlord_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/resources/monster/skeletonlord_right_2", gp.tileSize * i, gp.tileSize * i);
        } else {
            up1 = setup("/resources/monster/skeletonlord_phase2_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/resources/monster/skeletonlord_phase2_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/resources/monster/skeletonlord_phase2_down_1", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/resources/monster/skeletonlord_phase2_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/resources/monster/skeletonlord_phase2_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/resources/monster/skeletonlord_phase2_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/resources/monster/skeletonlord_phase2_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/resources/monster/skeletonlord_phase2_right_2", gp.tileSize * i, gp.tileSize * i);
        }

    }

    public void getAttackImage() {
        int i = 5;
        if (!inRage) {
            attackUp1 = setup("/resources/monster/skeletonlord_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("/resources/monster/skeletonlord_attack_up_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("/resources/monster/skeletonlord_attack_down_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("/resources/monster/skeletonlord_attack_down_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("/resources/monster/skeletonlord_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("/resources/monster/skeletonlord_attack_left_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("/resources/monster/skeletonlord_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("/resources/monster/skeletonlord_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
        } else {
            attackUp1 = setup("/resources/monster/skeletonlord_phase2_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("/resources/monster/skeletonlord_phase2_attack_up_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("/resources/monster/skeletonlord_phase2_attack_down_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("/resources/monster/skeletonlord_phase2_attack_down_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("/resources/monster/skeletonlord_phase2_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("/resources/monster/skeletonlord_phase2_attack_left_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("/resources/monster/skeletonlord_phase2_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("/resources/monster/skeletonlord_phase2_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
        }
    }

    public void setDialogue() {
        dialogues[0][0] = "Personne ne peut voler mon trésor";
        dialogues[0][1] = "Tu vas mourir !!!";
        dialogues[0][2] = "Affronte ton destin !";
    }

    public void setAction() {
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 2;
        }
        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(60);

        } else {
            // get a random direction
            getRandomDirection(120);

            // check if it attacks
            if (!attacking) {
                checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
            }
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;

    }

    @Override
    public void checkDrop() {
        gp.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;
        // restore the previous music
        gp.stopMusic();
        gp.playMusic(19);

        // remove the iron doors
        for (int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_iron.objName)){
                gp.playSE(21);
                gp.obj[gp.currentMap][i] = null;
            }
        }


        // cast a die
        int i = new Random().nextInt(100) + 1;

        // set the monster drop
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
