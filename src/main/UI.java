package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0 : the first screen, 1 : the second screen
    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/resources/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/resources/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        // create hud object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
    }
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);

    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(maruMonica);
//        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // title state
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        // play state
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        // pause state
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        // dialogue state
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        // character state
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }
        // options state
        if(gp.gameState == gp.optionState){
            drawOptionsScreen();
        }
        // game Over state
        if(gp.gameState == gp.gameOverState ){
            drawGameOverScreen();
        }
    }
    // affichage vie du joueur
    public void drawPlayerLife(){
//        gp.player.life = 3;
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // draw blank heart
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        // reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        // draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if( i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
        // draw max mana
        x = (gp.tileSize/2) - 5;
        y = (int) (gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }
        // draw mana
        x = (gp.tileSize/2) - 5;
        y = (int) (gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }
    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for (int i = 0; i < message.size(); i++) {
            if(message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i) , messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i) , messageX, messageY);
                int counter = messageCounter.get(i) + 1; // messagecounter ++
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawTitleScreen(){
        if(titleScreenState == 0){
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // title name
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 96F));
            String text = "Blue Boy Adventure";
            int x = getXForCenterText(text);
            int y = gp.tileSize*3;

            // shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);
            // main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // blue boy image
            x = gp.screenWidth/2 -  (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // menu
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
            text = "Nouveau jeu";
            x = getXForCenterText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Charger jeu";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Quitter";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            // class selection screen
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Sélectionnez votre classe !";
            int x = getXForCenterText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Guerrier";
            x = getXForCenterText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Voleur";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Sorcier";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Retour";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }

    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSE";
        int x = getXForCenterText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen(){
        // window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        // affichage du texte
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawCharacterScreen(){
        // create a frame
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // names
        g2.drawString("Niveau", textX, textY);
        textY += lineHeight;
        g2.drawString("Vie", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Force", textX, textY);
        textY += lineHeight;
        g2.drawString("Dextérité", textX, textY);
        textY += lineHeight;
        g2.drawString("Attaque", textX, textY);
        textY += lineHeight;
        g2.drawString("Défense", textX, textY);
        textY += lineHeight;
        g2.drawString("Expérience", textX, textY);
        textY += lineHeight;
        g2.drawString("Prochain niveau", textX, textY);
        textY += lineHeight;
        g2.drawString("Pièces", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Arme", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Bouclier", textX, textY);
        textY += lineHeight;

        // values
        int tailX = (frameX + frameWidth) - 30;
        // reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.life + "/" + gp.player.maxLife;
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.mana + "/" + gp.player.maxMana;
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);
    }
    public void drawInventory(){
        // frame
        int frameX = gp.tileSize*12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        // draw player's items
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            // equip cursor
            if (gp.player.inventory.get(i) == gp.player.currentWeapon ||
                    gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        // cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;

        // draw description text
        int textX = dFrameX +20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();
        if(itemIndex < gp.player.inventory.size()){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for (String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }
    public void drawGameOverScreen(){
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));

        text = "Game Over";
        // shadow
        g2.setColor(Color.BLACK);
        x = getXForCenterText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);

        // main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        // retry
        g2.setFont(g2.getFont().deriveFont( 50F));
        text = "Recommencer";
        x = getXForCenterText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        // back to the title screen
        text = "Quitter";
        x = getXForCenterText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
    }
    public void drawOptionsScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // sub window
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed =false;
    }
    public void options_top(int frameX, int frameY){
        int textX;
        int textY;

        // title
        String text = "Options";
        textX = getXForCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // full screen on/off
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Plein écran", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                if(gp.fullScreenOn == false){
                    gp.fullScreenOn = true;
                }
                else if(gp.fullScreenOn == true){
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        // music volume
        textY += gp.tileSize;
        g2.drawString("Musique", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX -25, textY);
        }

        // SE volume
        textY += gp.tileSize;
        g2.drawString("Effets sonnores", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX -25, textY);
        }

        // control
        textY += gp.tileSize;
        g2.drawString("Controles", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
            }
        }

        // end game
        textY += gp.tileSize;
        g2.drawString("Quitter", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 3;
                commandNum = 0;
            }
        }

        // back
        textY += gp.tileSize*2;
        g2.drawString("Retour", textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        // full screen check box
        textX = frameX + gp.tileSize*5;
        textY = frameY + gp.tileSize*2+24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn == true){
            g2.fillRect(textX, textY, 24, 24);
        }

        // music volume
        textY += gp.tileSize;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // se volume
        textY += gp.tileSize;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }
    public void options_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize -24 ;
        int textY = frameY + gp.tileSize*3;
        currentDialogue = "Les changements prendront\neffet au redemarrage\ndu jeu";
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        textX += 24; // remise en place du x
        // back
        textY = frameY + gp.tileSize *9;
        g2.drawString("Retour", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
            }
        }
    }
    public void options_control(int frameX, int frameY){
        int textX;
        int textY;
        // title
        String text = "Controle";
        textX = getXForCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Déplacement", textX, textY); textY += gp.tileSize;
        g2.drawString("Attaque", textX, textY); textY += gp.tileSize;
        g2.drawString("Tirer", textX, textY); textY += gp.tileSize;
        g2.drawString("Personnage", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;
        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("ZQSD", textX, textY); textY += gp.tileSize;
        g2.drawString("Entrer", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("Echap", textX, textY); textY += gp.tileSize;

        // back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Retour", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;
        currentDialogue = "Quitter le jeu\net retourner à l'écran\nprincipal ?";
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY +=40;
        }
        // yes
        String text = "Oui";
        textX = getXForCenterText(text);
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }
        // no
        text = "Non";
        textX = getXForCenterText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public int getItemIndexOnSlot(){
        int itemIndex = slotCol + (slotRow*5);

        return itemIndex;
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10,  height - 10, 25, 25);
    }
    public int getXForCenterText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public int getXForAlignRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
