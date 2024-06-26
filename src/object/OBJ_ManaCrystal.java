package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    public static final String objName = "Cristal de mana";
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_pickupOnly;
        value = 1;
        down1 = setup("/resources/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image = setup("/resources/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/resources/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("+ " + value + " mana");
        entity.mana += value;
        return true;
    }
}
