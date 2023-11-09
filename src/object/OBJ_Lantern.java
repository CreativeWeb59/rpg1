package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {
    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = "Lanterne";
        down1 = setup("/resources/objects/lantern", gp.tileSize, gp.tileSize);
        description = "[Lanterne]\nEclaire autour de vous.";
        price = 200;
        lightRadius = 250;
    }
}
