package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {
    public static final String objName = "Bottes";
    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/resources/objects/boots", gp.tileSize, gp.tileSize);
        price = 50;
    }
}
