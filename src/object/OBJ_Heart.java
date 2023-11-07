package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Coeur";
        type = type_pickupOnly;
        value = 2;
        down1 = setup("/resources/objects/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/resources/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/resources/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/resources/objects/heart_blank", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("+ " + value + " vies");
        entity.life += value;
        return true;
    }
}
