package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public static final String objName = "Bouclier en bois";
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        name = objName;
        type = type_shield;
        down1 = setup("/resources/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nComposé de bois.";
        price = 50;
    }
}
