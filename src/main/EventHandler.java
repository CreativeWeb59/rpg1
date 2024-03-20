package main;

import entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    Entity eventMaster;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventMaster = new Entity(gp);
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue() {
        eventMaster.dialogues[0][0] = "Vous tombez dans un trou";
        eventMaster.dialogues[1][0] = "L'eau du lac vous régènere\ntoute votre vie et votre mana.\n Progression sauvegardée";
        eventMaster.dialogues[1][1] = "Humm ! que c'est bon !";
    }

    public void checkEvent() {
        // check if the player character is so more than 1 tile away from the last event
        int xDistance = Math.abs((gp.player.worldX - previousEventX));
        int yDistance = Math.abs((gp.player.worldY - previousEventY));
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent == true) {
            // dégats
            if (hit(0, 27, 16, "right") == true) {
                damagePit(gp.dialogueState);
            }
//            if(hit(23, 19, "any") == true){damagePit(27, 16, gp.dialogueState);}
            // recuperation vies
            else if (hit(0, 23, 12, "up") == true) {
                healingPool(gp.dialogueState);
            }
            // teleportation
//            if(hit(20, 37, "left") == true){teleport(gp.dialogueState);}
            else if (hit(0, 10, 39, "any") == true) {
                teleport(1, 12, 13); // to the merchant's house
            } else if (hit(1, 12, 13, "any") == true) {
                teleport(0, 10, 39); // to outside
            } else if (hit(1, 12, 9, "up") == true) {
                speak(gp.npc[1][0]);
            } else if (hit(0, 12, 9, "any") == true) {
                teleport(2, 9, 41); // to the dungeon
            } else if (hit(2, 9, 41, "any") == true) {
                teleport(0, 12, 9); // to outside
            }else if (hit(2, 8, 7, "any") == true) {
                teleport(3, 26, 41); // to B2
            } else if (hit(3, 26, 41, "any") == true) {
                teleport(2, 8, 7); // to B1
            }

        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;
        // permet d'activer les dégats une seule fois
        //        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
    }

    /**
     * Teleporte d'une carte à l'autre
     *
     * @param map carte en cours
     * @param col x cord du joueur
     * @param row y cord du joueur
     */
    public void teleport(int map, int col, int row) {
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
//        gp.currentMap = map;
//        gp.player.worldX = gp.tileSize * col;
//        gp.player.worldY = gp.tileSize * row;
//        previousEventX = gp.player.worldX;
//        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSE(13);
    }

    /**
     * Teleporte d'une case à l'autre
     *
     * @param gameState
     */
    public void teleportTile(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Téléportation !";
        gp.player.worldX = gp.tileSize * 37;
        gp.player.worldY = gp.tileSize * 10;

    }

    public void speak(Entity entity) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
