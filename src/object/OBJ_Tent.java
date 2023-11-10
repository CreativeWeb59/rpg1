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
        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true; // objet réutilisable sinon mettre false
    }
}
