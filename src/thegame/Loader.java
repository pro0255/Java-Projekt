/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Trida zarizujici nahrani dat ze souboru
 *
 * @author Vojta
 */
public class Loader {

    public static Map<String, Integer> data = new HashMap();
    public static boolean map[][];

    /**
     * Metoda pro nahrání konfiguračních dat
     *
     * @author Vojta
     */
    public static boolean loadData() {
        Scanner sc;
        try {//"file:src/thegame/image/turret_cursor.png" // C:\\Users\\Vojta\\Desktop\\TowerDeffensee\\src\\thegame\\config\\config.txt
            sc = new Scanner(new File("src/thegame/config/config.txt"));
            while (sc.hasNextLine()) {
                String line[] = sc.nextLine().split("\t");
                data.put(line[0], Integer.parseInt(line[1]));
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("File NOT Found!");
        }
        return false;
    }

    /**
     * Metoda pro nahrani mapy
     *
     * @author Vojta
     * @param mapfile
     * @return boolean
     */
    public static boolean loadMap(String mapfile) {

        Scanner sc;
        try {
            int y = 0;
            sc = new Scanner(new File(mapfile));
            map = new boolean[data.get("HEIGTH_WORLD")][data.get("WIDTH_WORLD")];
            while (sc.hasNextLine()) {
                String line[] = sc.nextLine().split("  ");
                for (int i = 0; i < line.length; i++) {
                    map[y][i] = (Integer.parseInt(line[i]) == 1) ? false : true;
                }
                y++;
            }

            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot load MAP!");
        }
        return false;
    }

    /**
     * Metoda pro nahrani topscore
     *
     * @author Vojta
     */
    public static String[][] loadScore() {
        Scanner sc;
        try {
            int y = 0;
            sc = new Scanner(new File("src/thegame/save/score.txt"));
            String score[][] = new String[10][2];
            int i = 0;
            while (sc.hasNextLine()) {
                String line[] = sc.nextLine().split(" ");
                score[i][0] = line[0];
                score[i][1] = line[1];
                i++;
            }
            return score;
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot load SCORE!");
        }
        return null;
    }

    /**
     * Metoda pro ulozeni mapy
     *
     * @author Vojta
     */
    public static boolean[][] saveMap() {
        System.out.println("returned map");
        return map;
    }

    /**
     * Metoda na tisk dat nahranych z konfiguraniho souboru
     *
     * @author Vojta
     */
    public static void printData() {
        Iterator i = data.entrySet().iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }

}
