package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
    public static final String objName = "Porte";
    GamePanel gp;
    public OBJ_Door(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = objName;
        type = type_obstacle;
        down1 = setup("/resources/objects/door", gp.tileSize, gp.tileSize);
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
        dialogues[0][0] = "Vous avez besoin d'une clé !";
    }
    public void interact(){
        startDialogue(this, 0);
    }
}
