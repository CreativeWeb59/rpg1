package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public static final String objName = "Bouclier bleue";
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        name = objName;
        type = type_shield;
        down1 = setup("/resources/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nBouclier bleue";
        price = 250;
    }
}
