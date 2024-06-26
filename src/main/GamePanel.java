package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
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
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 3;
    // for full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    // fps
    int FPS = 60;

    // system
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutSceneManager csManager = new CutSceneManager(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public  UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    Thread gameThread;

    // entity and object
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
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
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutsceneState = 11;

    // others
    public boolean bossBattleOn = false;

    // area
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;

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
        eManager.setup();

//        playMusic(0);
        gameState = titleState;
        currentArea = outside;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if(fullScreenOn == true){
            setFullScreen();
        }
    }
    public void resetGame(boolean restart){
        stopMusic();
        currentArea = outside;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if(restart){
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }

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
                drawToTempScreen(); // draw everything to the buffered image
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
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null; // mort du monstre
                    }
                }
            } // projectile
            for (int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][i] != null){
                    if(projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
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
            for (int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
            // environnement lumiere
            eManager.update();

        } else if(gameState == pauseState){
            // nothing
        }

    }
    public void drawToTempScreen(){
        // debug
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        // title screen
        if(gameState == titleState){
            ui.draw(g2);
        }
        // mini map
        else if(gameState == mapState){
            map.drawFullMapScreen(g2);
        }
        // others
        else {
            // tiles
            tileM.draw(g2);

            // interactive tile
            for (int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }

            // add entities to the list
            entityList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }
            // projectile
            for (int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][i] != null){
                    entityList.add(projectile[currentMap][i]);
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

            // environement
            eManager.draw(g2);

            // mini map
            map.drawMiniMap(g2);

            // cutScene
            csManager.draw(g2);
            // Ui
            ui.draw(g2);
        }

        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);

            int x = 20;
            int y = 200;
            int lineHeight = 20;

            g2.drawString("WorldX : " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY : " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col : " + (player.worldX + player.solidArea.x) / tileSize, x, y); y += lineHeight;
            g2.drawString("Row : " + (player.worldY + player.solidArea.y) / tileSize, x, y); y += lineHeight;
            g2.drawString("Draw Time : " + passed, x, y);  y += lineHeight;
            g2.drawString("God Mode : " + keyH.godModeOn, x, y);
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

    /**
     * gere le changement de zone : exterieur / donjon et inversement
     */
    public void changeArea(){
        if(nextArea != currentArea){
            stopMusic();
            if(nextArea == outside){
                playMusic(0);
            }
            if(nextArea == indoor){
                playMusic(18);
            }
            if(nextArea == dungeon){
                playMusic(19);
            }
            aSetter.setNPC();
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }

    /**
     * supprime un objet temporaire
     */
    public void removeTempEntity(){
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            for (int i = 0; i < obj[1].length; i++) {
                if(obj[mapNum][i] != null && obj[mapNum][i].temp){
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}

