package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Clé";
        down1 = setup("/resources/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nOuvre les portes.";
    }
}
