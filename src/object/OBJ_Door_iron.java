package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_iron extends Entity {
    public static final String objName = "Iron Porte";
    GamePanel gp;
    public OBJ_Door_iron(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = objName;
        type = type_obstacle;
        down1 = setup("/resources/objects/door_iron", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "Rien ne se passe";
    }
    public void interact(){
        startDialogue(this, 0);
    }
}
