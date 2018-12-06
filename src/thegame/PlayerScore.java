/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trida reprezentujici skore hrace
 *
 * @author Vojta
 */
public class PlayerScore {

    public static int score;
    public String topScore[][] = Loader.loadScore();

    /**
     * Metoda ktera tiskne vsechna ulozena skore
     *
     */
    public void printScore() {
        for (int i = 0; i < topScore.length; i++) {
            for (int j = 0; j < topScore[0].length; j++) {
                System.out.print(topScore[i][j]);
            }
            System.out.println("");
        }
    }

    public int findMin() {
        int min = 0;
        for (int i = 1; i < topScore.length; i++) {
            if (Integer.parseInt(topScore[i][0]) < Integer.parseInt(topScore[min][0])) {
                min = i;
            }
        }
        return min;
    }

    public void sort() {
        for (int i = 0; i < topScore.length; i++) {
            for (int j = 0; j < topScore.length - 1; j++) {
                if(Integer.parseInt(topScore[j][0]) < Integer.parseInt(topScore[j+1][0])) {
                    String tmp[] = topScore[j];
                    topScore[j] = topScore[j+1];
                    topScore[j+1] = tmp;
                }
            }
        }
    }

    /**
     * Metoda ktera uklada skore
     *
     */
    public void saveScore(int score, String name) {
        int index = findMin();
        topScore[index][0] = Integer.toString(score);
        topScore[index][1] = name;
        sort();
    }

    /**
     * Metoda ktera zpetne uklada vsechny skore do souboru
     *
     */
    public void saveToFile() {
        File score = new File("src/thegame/save/score.txt");
        try {
            FileWriter sw = new FileWriter(score, false);

            for (int i = 0; i < topScore.length; i++) {

                String score_string = topScore[i][0];
                String name = topScore[i][1];
                sw.write(score_string + " " + name + "\r" + "\n");

            }

            sw.close();
        } catch (IOException ex) {
            Logger.getLogger(PlayerScore.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
