package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originatlTileSize = 16; // 16x16 tile
    final int scale = 3; // valeur de mise à l'échelle

    public final int tileSize = originatlTileSize * scale;
    public final int maxScreenCol = 20;
    public int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // world settings
    public final  int maxWorldCol = 50;
    public final  int maxWorldRow = 50;
    // for full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    // fps
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public  UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    Thread gameThread;
    // entity and object
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    public InteractiveTile iTile[] = new InteractiveTile[50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // game state
    public int gameState;
    public final int titleState = 0;
    public  final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
//        playMusic(0);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if(fullScreenOn == true){
            setFullScreen();
        }
    }
    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    public void restart(){
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }
    public void setFullScreen(){
        // get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // get full screen width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
                drawTempScreen(); // draw everything to the buffered image
                drawToScreen(); // draw the buffered image to the screen
//                repaint();
                delta --;
                drawCount ++;
            }

            if(timer >= 1000000000){
//                System.out.println("Fps : " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void update(){
        if(gameState == playState){
            // player
            player.update();
            // npc
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null){
                    if(monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if(monster[i].alive == false) {
                        monster[i].checkDrop();
                        monster[i] = null; // mort du monstre
                    }
                }
            } // projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }
            // particle list
            for (int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }
            // interactive tile
            for (int i = 0; i < iTile.length; i++) {
                if(iTile[i] != null){
                    iTile[i].update();
                }
            }
        } else if(gameState == pauseState){
            // nothing
        }

    }
    public void drawTempScreen(){
        // debug
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        // title screen
        if(gameState == titleState){
            ui.draw(g2);
        } // others
        else {
            // tiles
            tileM.draw(g2);

            // interactive tile
            for (int i = 0; i < iTile.length; i++) {
                if(iTile[i] != null){
                    iTile[i].draw(g2);
                }
            }

            // add entities to the list
            entityList.add(player);
            for (int i = 0; i < npc.length; i++) {
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            // projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }
            // particle
            for (int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }
            // sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldX, e2.worldY);
                    return result;
                }
            });
            // draw entities
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // empty entity list
            entityList.clear();

            // Ui
            ui.draw(g2);
        }

        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time " + passed, 10, 400);
            System.out.println("Draw Time " + passed);
        }
    }

    // ancienne methode avant full screen
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//
//        // debug
//        long drawStart = 0;
//        if(keyH.checkDrawTime == true){
//            drawStart = System.nanoTime();
//        }
//
//        // title screen
//        if(gameState == titleState){
//            ui.draw(g2);
//        } // others
//        else {
//            // tiles
//            tileM.draw(g2);
//
//            // interactive tile
//            for (int i = 0; i < iTile.length; i++) {
//                if(iTile[i] != null){
//                    iTile[i].draw(g2);
//                }
//            }
//
//            // add entities to the list
//            entityList.add(player);
//            for (int i = 0; i < npc.length; i++) {
//                if(npc[i] != null){
//                    entityList.add(npc[i]);
//                }
//            }
//            for (int i = 0; i < obj.length; i++) {
//                if(obj[i] != null){
//                    entityList.add(obj[i]);
//                }
//            }
//            for (int i = 0; i < monster.length; i++) {
//                if(monster[i] != null){
//                    entityList.add(monster[i]);
//                }
//            }
//            // projectile
//            for (int i = 0; i < projectileList.size(); i++) {
//                if(projectileList.get(i) != null){
//                    entityList.add(projectileList.get(i));
//                }
//            }
//            // particle
//            for (int i = 0; i < particleList.size(); i++) {
//                if(particleList.get(i) != null){
//                    entityList.add(particleList.get(i));
//                }
//            }
//            // sort
//            Collections.sort(entityList, new Comparator<Entity>() {
//                @Override
//                public int compare(Entity e1, Entity e2) {
//                    int result = Integer.compare(e1.worldX, e2.worldY);
//                    return result;
//                }
//            });
//            // draw entities
//            for (int i = 0; i < entityList.size(); i++) {
//                entityList.get(i).draw(g2);
//            }
//            // empty entity list
//            entityList.clear();
//
////            // objects
////            for (int i = 0; i < obj.length; i++) {
////                if(obj[i] != null){
////                    obj[i].draw(g2, this);
////                }
////            }
////            // Npc
////            for (int i = 0; i < npc.length; i++) {
////                if(npc[i] != null){
////                    npc[i].draw(g2);
////                }
////            }
////            // player
////            player.draw(g2);
//
//            // Ui
//            ui.draw(g2);
//        }
//
//        if(keyH.checkDrawTime == true){
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd - drawStart;
//            g2.setColor(Color.white);
//            g2.drawString("Draw Time " + passed, 10, 400);
//            System.out.println("Draw Time " + passed);
//        }
//
//        g2.dispose();
//    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}

