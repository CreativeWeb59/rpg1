package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Potion rouge";
        value = 5;
        type = type_consumable;
        down1 = setup("/resources/objects/potion_red", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nRedonne " + value + " points de vie.";
        price = 25;
        stackable = true;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Vous récupérez " + value + " points de vie.";
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}
