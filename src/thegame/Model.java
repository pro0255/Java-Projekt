/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Point;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import static thegame.World.blockSize;
import static thegame.World.block_map;

/**
 * Trida obsahujici interakci mezi hrou, vykresleni hry..
 *
 * @author Vojta
 */
public class Model {

    public static int spawnTime = 0;
    public static int spawnFrame = 0;
    public static int OptionPanel = -1;
    public static Point mousePointMove = null;
    public static Point mousePointClicked = null;
    public static Point2D mousePointClicked2D = null;
    public static Point2D mousePointClicked2DInWorld = null;
    public static Point2D mousePointClicked2Dcursor = null;
    public static Point2D mousePointMove2D = null;
    private boolean isGameOver = false;
    private boolean isMainMenu = true;
    private boolean saveScore = true;

    public void setIsMainMenu() {
        this.isMainMenu = false;
    }

    public Player player;
    public World world;
    public Stage stage;
    public Panel panel;

    private ArrayList<Mob> mobs = new ArrayList<>();
    private ArrayList<TurretObject> turrets = new ArrayList<>();

    public Model() {
        Loader.loadData();
        initGame();
    }

    /**
     * Metoda nastavujici souradnice kliku
     *
     * @author Vojta
     */
    public synchronized void setCursor(double x, double y) {
        mousePointMove2D = new Point2D(x, y);
        mousePointMove = new Point((int) ((x / World.blockSize) - 1), (int) y / World.blockSize);
    }

    /**
     * Metoda ktera resetuje mapu pro novy level
     *
     * @author Vojta
     */
    public synchronized void resetForNewLevel() {
        int mapnumber = (stage.STAGE_level % 6);
        if (mapnumber == 0) {
            mapnumber = 1;
        }
        System.out.println(mapnumber);
        String map = "map" + Integer.toString(mapnumber) + ".txt";
        Loader.loadMap("src/thegame/config/" + map);
        World.map_contruction = Loader.saveMap();
        turrets.clear();
        mobs.clear();
        stage.WAVE_level = 0;
    }

    /**
     * Metoda pro vytvoreni prisery
     *
     * @author Vojta
     */
    public synchronized void spawnMob() {
        if (stage.waveLogicSpawn(mobs) == -1) {
            resetForNewLevel();
        }
        if (stage.waveLogicSpawn(mobs) == 0) {
            mobs.add(new Mob());
            spawnTime = 1;
            Audio.playSound("src/thegame/audio/monsters_move.wav");
        }

    }

    /**
     * Metoda ktera dela logiku magickeho utoku
     *
     * @author Vojta
     */
    public synchronized void utility(ArrayList<Mob> todelete) {
        for (Mob mob : mobs) {
            mob.health -= 0.5 * mob.health;
            if (mob.health < 1) {
                todelete.add(mob);
            }
        }
        panel.optionSetter(5);
    }

    /**
     * Metoda pro zjisteni konce hry a ulozeni skore hrace
     *
     * @author Vojta
     */
    public synchronized void GameOver() {
        if (Player.health < 1) {
            this.isGameOver = true;
            if (saveScore) {
                int score = stage.STAGE_level * 50 + stage.WAVE_level * 5;
                player.saveScore(score);
                System.out.println(score);
                saveScore = false;
            }
        }
    }

    /**
     * Metoda ktera v zavislosti na bloku vraci vez postavenou na danem bloku
     *
     * @author Vojta
     */
    public synchronized int sellTurret(Block block) {
        for (int i = 0; i < turrets.size(); i++) {
            if (block.x == turrets.get(i).x && block.y == turrets.get(i).y) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Metoda postaveni vezicky
     *
     * @author Vojta
     */
    public synchronized void buildTurret(TurretObject turret) {
        turrets.add(turret);
        Audio.playSound("src/thegame/audio/building_turret.wav");
    }

    /**
     * Metoda pro nastaveni nove hry
     *
     * @author Vojta
     */
    public synchronized void initGame() {
        String mapfile = "src/thegame/config/map1.txt";
        Loader.loadMap(mapfile);
        world = new World();
        player = new Player("Pepa", world);
        stage = new Stage();
        panel = new Panel(world.HEIGTH_WORLD, world.WIDTH_WORLD);
    }

    /**
     * Metoda nastavujici souradnice kliku
     *
     * @author Vojta
     */
    public synchronized void setClickedCursor(double x, double y) {
        mousePointClicked = new Point((int) ((x / World.blockSize) - 1), (int) y / World.blockSize);
        mousePointClicked2D = new Point2D(x, y);
        Audio.playSoundNoControl("src/thegame/audio/click.wav");

        if ((x >= block_map[0][0].x && x < (block_map[0][World.map_contruction[0].length - 1].x + blockSize))
                && (y >= block_map[0][0].y && y < (block_map[World.map_contruction.length - 1][0].y + blockSize))) {
            mousePointClicked2DInWorld = new Point2D(x, y);
        }

    }

    /**
     * Metoda kontrolujici jestli je dana pozice vhodna pro postaveni vezicky
     *
     * @author Vojta
     */
    public synchronized boolean isValidPositionForTurret() {
        for (TurretObject turret : turrets) {
            if (world.isInWorld(mousePointClicked2D, mousePointClicked)) {
                if (turret.x == World.block_map[mousePointClicked.y][mousePointClicked.x].x && turret.y == World.block_map[mousePointClicked.y][mousePointClicked.x].y) {
                    System.out.println("Invalid position");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Metoda umoznujici vylepsit vezicku
     *
     * @author Vojta
     */
    public synchronized boolean goUpgradeTurret() {
        return mousePointClicked2D.getX() >= TurretObject.upgrade.x && mousePointClicked2D.getX() <= TurretObject.upgrade.x + TurretObject.upgrade.width
                && mousePointClicked2D.getY() >= TurretObject.upgrade.y && mousePointClicked2D.getY() <= TurretObject.upgrade.y + TurretObject.upgrade.height
                && TurretObject.upgradeCost <= Player.coins;
    }

    /**
     * Metoda vracejici ktera vezicka je oznacena
     *
     * @author Vojta
     */
    public synchronized int SelectedTurret() {
        if (mousePointClicked2DInWorld != null) {
            for (int i = 0; i < turrets.size(); i++) {
                if (mousePointClicked2DInWorld.getX() >= turrets.get(i).x && mousePointClicked2DInWorld.getX() < turrets.get(i).x + turrets.get(i).width
                        && mousePointClicked2DInWorld.getY() >= turrets.get(i).y && mousePointClicked2DInWorld.getY() < turrets.get(i).y + turrets.get(i).height) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Metoda vracejici pres kterou vezicku jsme projeli myskou
     *
     * @author Vojta
     */
    public synchronized int equal() {
        for (int i = 0; i < turrets.size(); i++) {
            if (turrets.get(i).x == (mousePointMove.x * World.blockSize + World.block_map[0][0].x) && turrets.get(i).y == mousePointMove.y * World.blockSize) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Metoda vracejici na ktery panel jsme klikli
     *
     * @author Vojta
     */
    public synchronized int optionClickedGetter() {

        if (mousePointClicked2D != null) {
            for (int i = 0; i < panel.panel.size(); i++) {
                if (mousePointClicked2D.getX() >= panel.panel.get(i).x && mousePointClicked2D.getX() < panel.panel.get(i).x + panel.panel.get(i).width
                        && mousePointClicked2D.getY() >= panel.panel.get(i).y && mousePointClicked2D.getY() < panel.panel.get(i).y + panel.panel.get(i).height) {
                    return i;

                }
            }
        }
        return -1;
    }

    /**
     * Metoda vracejici pres ktery panel jsme projeli mysi
     *
     * @author Vojta
     */
    public synchronized int equalPanelIndex() {

        if (mousePointMove != null) {
            for (int i = 0; i < panel.panel.size(); i++) {
                if (mousePointMove2D.getX() >= panel.panel.get(i).x && mousePointMove2D.getX() < panel.panel.get(i).x + panel.panel.get(i).width
                        && mousePointMove2D.getY() >= panel.panel.get(i).y && mousePointMove2D.getY() < panel.panel.get(i).y + panel.panel.get(i).height) {
                    return i;
                }
            }
        }
        return -1;
    }

    public synchronized boolean equalScoreBar() {
        if (mousePointMove2D != null) {
            if (mousePointMove2D.getX() >= Player.ScoreBar.x && mousePointMove2D.getX() <= Player.ScoreBar.x + World.blockSize
                    && mousePointMove2D.getY() >= Player.ScoreBar.y && mousePointMove2D.getY() <= Player.ScoreBar.y + World.blockSize) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gettery
     *
     * @author Vojta
     */
    public World getWorld() {
        return world;
    }

    public static Point getMousePointClicked() {
        return mousePointClicked;
    }

    public Player getPl() {
        return player;
    }

    public ArrayList<TurretObject> getTurrets() {
        return turrets;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public Point getMousePoint() {
        return mousePointMove;
    }

    public Panel getPanel() {
        return panel;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean getIsMainMenu() {
        return isMainMenu;
    }
}
