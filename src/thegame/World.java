/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Point;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * Trida reprezentujici mapu hry
 *
 * @author Vojta
 */
public class World {

    public int WIDTH_WORLD = Loader.data.get("WIDTH_WORLD");
    public int HEIGTH_WORLD = Loader.data.get("HEIGTH_WORLD");
    public static int blockSize = 52;

    public static Block block_map[][];
    /*
    public static boolean map_contruction[][] = new boolean[][]{
        {true, true, true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true, true, true},
        {false, false, true, false, false, false, false, false, false, false, false, true, true},
        {true, false, true, false, true, true, true, true, true, true, false, true, true},
        {true, false, true, false, true, false, false, false, false, false, false, true, true},
        {true, false, true, false, true, false, true, true, true, true, true, true, true},
        {true, false, false, false, true, false, false, false, false, false, false, false, false},
        {true, true, true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true, true, true},};
     */

    public static boolean map_contruction[][] = Loader.saveMap();

    /**
     * Kontruktor
     *
     */
    public World() {
        block_map = new Block[HEIGTH_WORLD][WIDTH_WORLD];
        this.define();
    }

    /**
     * Metoda definujici umisteni jednotlivych bloku
     *
     */
    public void define() {
        for (int i = 0; i < block_map.length; i++) {
            for (int j = 0; j < block_map[0].length; j++) {
                block_map[i][j] = new Block((View.WIDTH / 2) - ((WIDTH_WORLD * blockSize) / 2) + (j * blockSize), (i * blockSize), blockSize, blockSize);
            }
        }
    }

    /**
     * Metoda kontrolujici jestli je klik ve svete
     *
     */
    public boolean isInWorld(Point2D point2D, Point point) {
        if ((point2D.getX() >= block_map[0][0].x && point2D.getX() < (block_map[0][WIDTH_WORLD - 1].x + blockSize))
                && (point2D.getY() >= block_map[0][0].y && point2D.getY() < (block_map[HEIGTH_WORLD - 1][0].y + blockSize))
                && point.x >= 0 && point.x < WIDTH_WORLD && point.y >= 0 && point.y < HEIGTH_WORLD) {
            return true;
        } else {
            System.out.println("no");
            return false;
        }
    }

    /**
     * Metoda kontrolujici jestli je klik mimo cestu na ktere chodi prisery
     *
     */
    public boolean isOutOfRoad(Point2D point2D, Point point) {
        if (map_contruction[point.y][point.x] == true) {
            return true;
        }
        return false;
    }

    /**
     * Metoda vracejici vstup pro prisery
     *
     */
    public Point getEntrance() {
        for (int y = 0; y < block_map.length; y++) {
            if (map_contruction[y][0] == false) {
                Point entrance = new Point(y, 0);
                return entrance;
            }
        }
        return null;
    }

    /**
     * Metoda vracejici konec cesty
     *
     */
    public Block getEnd() {

        for (int y = 0; y < block_map.length; y++) {
            if (map_contruction[y][WIDTH_WORLD - 1] == false) {
                return block_map[y][WIDTH_WORLD - 1];
            }
        }
        return null;
    }

}
