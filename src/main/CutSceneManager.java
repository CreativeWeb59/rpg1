package main;

import entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_BlueHeart;
import object.OBJ_Door_iron;

import java.awt.*;

public class CutSceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    String endCredit;

    // Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;
    int counter = 0;
    float alpha = 0f;
    int y;

    public CutSceneManager(GamePanel gp) {
        this.gp = gp;
        endCredit = "Program/Music/Art\n" +
                "Delphine" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "Congratulations...\n" +
                "Congratulations...\n" +
                "Congratulations...\n" +
                "Congratulations...\n\n\n\n\n\n\n\n\n" +
                "Merci d'avoir jouer...\n" +
                "";

    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        switch (sceneNum){
            case skeletonLord: scene_skeletonLord(); break;
            case ending: scene_ending(); break;
        }
    }
    public void scene_skeletonLord(){
        if(scenePhase == 0){
            gp.bossBattleOn = true;
            // shut the iron door
            for (int i = 0; i < gp.obj[1].length; i++) {
                if(gp.obj[gp.currentMap][i] == null){
                    gp.obj[gp.currentMap][i] = new OBJ_Door_iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSE(21);
                    break;
                }
            }
            // search a vacant slot for the dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
                if(gp.npc[gp.currentMap][i] == null){
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }
            gp.player.drawing = false;
            scenePhase++;
        }
        if(scenePhase == 1){
            gp.player.worldY -= 2;
            if(gp.player.worldY < gp.tileSize * 16){
                scenePhase++;
            }
        }
        if(scenePhase == 2){
            // search the boss
            for (int i = 0; i < gp.monster[1].length; i++) {
                if(gp.monster[gp.currentMap][i] != null &&
                        gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName){
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        // dialogue du boss
        if(scenePhase == 3){
            gp.ui.drawDialogueScreen();
        }
        // return to the player
        // restore the player position
        if(scenePhase == 4){
            for (int i = 0; i < gp.npc[1].length; i++) {
                if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)){
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                    gp.npc[gp.currentMap][i] = null;
                    System.out.println("supprime la camera");
                    break;
                }
            }
            // start drawing the player
            gp.player.drawing = true;

            // reset
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.playState;

            // change the music
            gp.stopMusic();
            gp.playMusic(22);
        }
    }
    public void scene_ending(){
        if(scenePhase == 0){
            gp.stopMusic();
            gp.ui.npc = new OBJ_BlueHeart(gp);
            scenePhase++;
        }
        // display dialogue
        if(scenePhase == 1){
            gp.ui.drawDialogueScreen();
        }
        // play the fanfare
        if(scenePhase == 2){
            gp.playSE(4);
            scenePhase++;
        }
        // wait until the sound effect ends
        if(scenePhase == 3){
            if(counterReached(300)){
                scenePhase++;
            }
        }
        // dark effect
        if(scenePhase == 4){
            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }
            drawBlackBackground(alpha);
            if(alpha == 1f){
                scenePhase++;
            }
        }
        if(scenePhase == 5){
            drawBlackBackground(1f);

            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }
            String text = "Après une bataille acharnée avec le Skeleton Lord,\n" +
                    " Blue Boy à finalement trouvé le légendaire trésor.\n" +
                    " Mais ce n'est pas la fin de son périple. \n" +
                    " L'aventure de Blue Boy commence à peine.";
            drawString(alpha, 38f, 200, text, 70);
            if(counterReached(600)){
                gp.playMusic(0);
                scenePhase++;
            }
        }
        if(scenePhase == 6){
            drawBlackBackground(1f);
            drawString(1f, 120f, gp.screenHeight /2, "Blue Boy Adventure", 40);
            if(counterReached(480)){
                scenePhase++;
            }
        }
        // affichage du texte de fin
        if(scenePhase == 7){
            drawBlackBackground(1f);
            y = gp.screenHeight/2;
            drawString(1f, 38f, y, endCredit,40);

            if(counterReached(480)){
                scenePhase++;
            }
        }
        // scrolling the credit
        if(scenePhase == 8){
            drawBlackBackground(1f);
            y--;
            drawString(1f, 38f, y, endCredit,40);
            if(counterReached(480)){
                scenePhase++;
            }
        }
    }

    /**
     * verifie le compteur / permet d'attendre la fin du compteur
     * @param target
     * @return
     */
    public boolean counterReached(int target){
        boolean counterReached = false;
        counter++;
        if(counter > target){
            counterReached  = true;
            counter = 0;
        }
        return counterReached;
    }

    /**
     * permet d'effectuer un effet dark sur le fond d'écran
     * @param alpha
     */
    public void drawBlackBackground(float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
    /**
     * ecrit un message qui apparait petit a petit grace à un effet alpha
     */
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for (String line: text.split("\n")){
            int x = gp.ui.getXForCenterText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
