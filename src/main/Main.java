package main;

import javax.swing.*;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Jeu d'aventure en 2D");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // recuperation des options
        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn == true){
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
    public void setIcon(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/player/boy_down_1.png"));
        window.setIconImage(icon.getImage());
    }
}
