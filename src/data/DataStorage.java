package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // player stats
    int level;
    int maxLife;
    int maxMana;
    int getMaxMana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    // player inventory
    ArrayList<String> itemsNames = new ArrayList<>();
    ArrayList<Integer> itemsAmounts = new ArrayList<>();
}
