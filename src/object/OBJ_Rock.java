package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Rock";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left1 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right1 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/resources/projectile/rock_down_1", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.ammo >= useCost){
            haveResource = true;
        }
        return haveResource;
//        return user.mana >= useCost;
    }
    public void substractResource(Entity user){
        user.ammo -= useCost;
    }
}
