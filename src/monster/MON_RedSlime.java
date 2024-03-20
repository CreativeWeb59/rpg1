package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Entity {
    GamePanel gp;

    public MON_RedSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Red Slime";
        type = type_monster;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
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
        up1 = setup("/resources/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/resources/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/resources/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/resources/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/resources/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/resources/monster/redslime_down_2", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        if(onPath == true){
            // si le joueur s'écarte de 20 cases
            // le monstre perd l'aggro
            checkStopChasingOrNot(gp.player, 15, 100);

            // mode suivi du joueur
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

            // lancer de projectiles
            // en fonction uniquement si aggro du joueur
            // sinon à placer en dehors de la boucle principale
            checkShootOrNot(200, 30);
        } else {
            checkStartChasingOrNot(gp.player, 5, 100);
            getRandomDirection();
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
