package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity {
    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Clé";
        type = type_consumable;
        down1 = setup("/resources/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nOuvre les portes.";
        price = 100;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Porte");
        if(objIndex != 999){
            gp.ui.currentDialogue = "Vous utilisez la " + name + " pour ouvrir la porte";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        } else {
            gp.ui.currentDialogue = "Que faites vous ? ";
            return false;
        }
    }
}
