package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    // debug
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // title state
        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        // play state
        else if(gp.gameState == gp.playState){
            playState(code);
        }
        // pause state
        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }
        // dialogue state
        else if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        // character state
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }
        // options state
        else if(gp.gameState == gp.optionState){
            optionState(code);
        }
        // game over state
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
    }

    public void titleState(int code){
        if (gp.ui.titleScreenState == 0){
            if(code == KeyEvent.VK_Z){
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.ui.titleScreenState = 1;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){

                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        } else if (gp.ui.titleScreenState == 1){
            if(code == KeyEvent.VK_Z){
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 3;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 3){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    System.out.println("Guerrier avec caractéristiques différentes");
                    gp.gameState = gp.playState;
//                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){
                    System.out.println("Voleur avec caractéristiques différentes");
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 2){
                    System.out.println("Sorcier avec caractéristiques différentes");
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 3){
                    gp.ui.titleScreenState = 0;
                }
            }
        }
    }
    public void playState(int code){
        if(code == KeyEvent.VK_Z){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_Q){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionState;
        }
        // debug
        if(code == KeyEvent.VK_T){
            if(checkDrawTime == false){
                checkDrawTime = true;
            } else if (checkDrawTime == true) {
                checkDrawTime = false;
            }
        }
        if(code == KeyEvent.VK_R){
            switch (gp.currentMap){
                case 0: gp.tileM.loadMap("/resources/maps/worldV3.txt", 0); break;
                case 1: gp.tileM.loadMap("/resources/maps/interior01.txt", 1); break;
            }
        }
    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_Z){
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_Q){
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S){
            if (gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D){
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }
    public void optionState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        // deplacement curseur
        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }

        if(code == KeyEvent.VK_Z){
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }

        // volume
        if(code == KeyEvent.VK_Q){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
            }
            if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5){
                gp.se.volumeScale++;
                gp.playSE(9);
            }
        }
    }
    public void gameOverState(int code){
        if(code == KeyEvent.VK_Z){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum < 1){
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_Z){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_Q){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = false;
        }
    }
}
