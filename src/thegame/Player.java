/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Rectangle;

/**
 * Trida reprezentujici hrace hry
 *
 * @author proko
 */
public class Player {

    private World world;
    private PlayerScore PS = new PlayerScore();
    public String name;

    public static Rectangle HealthBar;
    public static Rectangle CoinBar;
    public static Rectangle ScoreBar;
    public static Rectangle ResetBar;
    public static Rectangle MainMenuBar;
    public static Rectangle HelpBar;

    private int spaceBetweenBars = 10;
    public static int sizeResources = 20;

    public static int health = Loader.data.get("Player_health");
    public static int coins = Loader.data.get("Player_coins");

    /**
     * Kontruktor
     *
     */
    public Player(String name, World world) {
        this.name = name;
        this.world = world;
        define();
        PS.printScore();
    }

    /**
     * Metoda definujici hrace
     *
     */
    public void define() {
        HealthBar = new Rectangle(World.block_map[world.HEIGTH_WORLD - 1][0].x, World.block_map[world.HEIGTH_WORLD - 1][0].y + World.blockSize + World.blockSize / 3, sizeResources, sizeResources);
        CoinBar = new Rectangle(World.block_map[world.HEIGTH_WORLD - 1][0].x, World.block_map[world.HEIGTH_WORLD - 1][0].y + World.blockSize + World.blockSize / 3 + spaceBetweenBars + sizeResources, sizeResources, sizeResources);
        ScoreBar = new Rectangle(World.block_map[0][0].x - World.blockSize - (World.block_map[0][0].x - World.blockSize) / 2, World.block_map[0][0].y, World.blockSize, World.blockSize);
        ResetBar = new Rectangle(World.block_map[0][0].x - World.blockSize - (World.block_map[0][0].x - World.blockSize) / 2, World.block_map[1][0].y, World.blockSize, World.blockSize);
        MainMenuBar = new Rectangle(World.block_map[0][0].x - World.blockSize - (World.block_map[0][0].x - World.blockSize) / 2, World.block_map[2][0].y, World.blockSize, World.blockSize);
        HelpBar = new Rectangle(World.block_map[0][0].x - World.blockSize - (World.block_map[0][0].x - World.blockSize) / 2, World.block_map[3][0].y, World.blockSize, World.blockSize);
    }

    /**
     * Metoda pro ulozeni skore hrace
     *
     */
    public void saveScore(int score) {
        PS.saveScore(score, name);
        PS.saveToFile();
    }

    public PlayerScore getPS() {
        return PS;
    }

}
