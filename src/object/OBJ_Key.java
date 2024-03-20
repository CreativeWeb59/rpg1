package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity {
    public static final String objName = "Clé";
    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        type = type_consumable;
        down1 = setup("/resources/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nOuvre les portes.";
        price = 100;
        stackable = true;
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "Vous utilisez la " + name + " pour ouvrir la porte";
        dialogues[1][0] = "Que faites vous ? ";
    }
    public boolean use(Entity entity){
        int objIndex = getDetected(entity, gp.obj, "Porte");
        if(objIndex != 999){
            startDialogue(this, 0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }
}
