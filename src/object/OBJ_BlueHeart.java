package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setup("/resources/objects/blueheart", gp.tileSize, gp.tileSize);

    }
    public void setDialogue() {
        dialogues[0][0] = "Vous récupérer un beau gemme bleue";
        dialogues[0][1] = "Tu vas mourir !!!";
        dialogues[0][2] = "Affronte ton destin !";
    }
    public boolean use(Entity entity){
        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
}
