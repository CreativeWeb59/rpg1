package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = type_monster;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 20;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

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
    public void update(){
        super.update();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;
        if(onPath == false && tileDistance < 5){
            int i = new Random().nextInt(100) + 1;
            if(i > 50){
                onPath = true;
            }
        }
        // si le joueur s'écarte de 20 cases
        // le monstre perd l'aggro
        if(onPath == true && tileDistance > 20){
            onPath = false;
        }
    }
    public void setAction(){
        if(onPath == true){
            // mode suivi du joueur
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);

            // lancer de projectiles
            // en fonction uniquement si aggro du joueur
            // sinon à placer en dehors de la boucle principale
            int i = new Random().nextInt(200) + 1;
//            if (i > 199 && projectile.alive == false && shotAvailableCounter == 30){
            if (i > 197 && projectile.alive == false && shotAvailableCounter == 30){
                projectile.set(worldX, worldY, direction, true, this);
//                gp.projectileList.add(projectile);
                // check vacancy
                for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                    if (gp.projectile[gp.currentMap][ii] == null) {
                        gp.projectile[gp.currentMap][ii] = projectile;
                        break;
                    }
                }

                shotAvailableCounter = 0;
            }
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
