package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Cristal de mana";
        image = setup("/resources/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/resources/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
    }
}
