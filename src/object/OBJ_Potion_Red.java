package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    public static final String objName = "Potion rouge";
    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        value = 5;
        type = type_consumable;
        down1 = setup("/resources/objects/potion_red", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nRedonne " + value + " points de vie.";
        price = 25;
        stackable = true;
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "Vous récupérez " + value + " points de vie.";
    }
    public boolean use(Entity entity){
        startDialogue(this, 0);
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}
