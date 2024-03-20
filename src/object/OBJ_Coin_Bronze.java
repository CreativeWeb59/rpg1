package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    public static final String objName = "Bronze Coin";
    GamePanel gp;

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        value = 1;
        type = type_pickupOnly;
        down1 = setup("/resources/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage(value + " pièce");
        gp.player.coin += value;
        return true;
    }
}
