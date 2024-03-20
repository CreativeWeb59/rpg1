package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public static final String objName = "Hache simple";
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        name = objName;
        type = type_axe;
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        down1 = setup("/resources/objects/axe", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nSert à couper du bois";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
