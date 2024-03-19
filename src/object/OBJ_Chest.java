package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Coffre";
        image = setup("/resources/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/resources/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;

        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void setLoot(Entity loot){
        this.loot = loot;
    }
    public void interact() {
        gp.gameState = gp.dialogueState;

        if (opened == false) {
            gp.playSE(3);
            StringBuilder sb = new StringBuilder();
            sb.append("Vous ouvrez le coffre et vous y trouvez\nune " + loot.name + " !");

            if (gp.player.canObtainItem(loot) == false) {
                sb.append("\n... Vous ne pouvez pas en emporter plus !");
            } else {
                sb.append("\nVous obtenez une \n" + loot.name + " !");
                down1 = image;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        } else {
            gp.ui.currentDialogue = "Le coffre est vide";
        }
    }
}
