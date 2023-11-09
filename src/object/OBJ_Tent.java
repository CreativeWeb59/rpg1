package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Tente";
        down1 = setup("/resources/objects/tent", gp.tileSize, gp.tileSize);
        description = "[Tente]\nVous pouvez y dormir toute la nuit";
        price = 300;
        stackable = true;
    }
    public boolean use(Entity entity){

    }
}
