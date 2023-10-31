package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_CoinBronze extends Entity {
    GamePanel gp;

    public OBJ_CoinBronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Bronze Coin";
        value = 1;
        type = type_pickupOnly;
        down1 = setup("/resources/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage(value + " pièce");
        gp.player.coin += value;
    }
}
