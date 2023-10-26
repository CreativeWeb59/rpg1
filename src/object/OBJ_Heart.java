package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Coeur";
        image = setup("/resources/objects/heart_full");
        image2 = setup("/resources/objects/heart_half");
        image3 = setup("/resources/objects/heart_blank");
    }
}
