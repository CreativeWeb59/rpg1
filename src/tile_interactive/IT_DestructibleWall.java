package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.awt.*;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTile {
    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/resources/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_pickaxe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE(){
        gp.playSE(20);
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
    public Color getParticleColor(){
        Color color = new Color(65, 65, 65);
        return color;
    }
    public int getParticleSize(){
        int size = 6;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }

    /**
     * permet de recuperer un objet en cassant un mur
     */
    public void checkDrop() {
        // cast a die
        int i = new Random().nextInt(100)+1;

        // set the monster drop
        if(i < 30){
            dropItem(new OBJ_Rock(gp));
        }
        if(i >= 30 && i < 95){
//            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 95 && i < 100){
            dropItem(new OBJ_Heart(gp));
        }
    }
}
