package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        name = "Hache simple";
        type = type_axe;
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        down1 = setup("/resources/objects/axe", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nSert à couper du bois";
        price = 75;
    }
}
