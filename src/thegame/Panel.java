/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Trida reprezentujici panel ve hre
 *
 * @author proko
 */
public class Panel {

    private int panelSize = 6;
    private int panelSpace = 10;
    private int cellSize = 60;

    private int choosedOption;

    public ArrayList<Integer> prices;
    public ArrayList<Integer> options;
    public ArrayList<Rectangle> panel;

    /**
     * Kontruktor
     *
     */
    public Panel(int height, int width) {
        options = new ArrayList<>();
        choosedOption = panelSize - 1;
        define(height, width);
    }

    /**
     * Metoda definujici panel
     *
     */
    public void define(int height, int width) {
        panel = new ArrayList<>();
        for (int i = 0; i < panelSize; i++) {
            panel.add(new Rectangle((View.WIDTH / 2) - ((panelSize * (cellSize + 5)) / 2) + ((cellSize + 5) * i), World.block_map[height - 1][0].y + World.blockSize + 20, cellSize, cellSize));
        }
        definePrices();
    }

    /**
     * Metoda nastavujici, ktera moznost z panelu je zvolena
     *
     */
    public void optionSetter(int choose) {
        if (!(choose == -1)) {
            choosedOption = choose;
            Audio.playSoundNoControl("src/thegame/audio/selected.wav");
            Model.mousePointClicked2D = null;
        }
    }

    /**
     * Metoda ktera definuje ceny panelu
     *
     */
    public void definePrices() {
        prices = new ArrayList<>();
        prices.add(TurretSingle.turretPrice);
        prices.add(TurretMulti.turretPrice);
        prices.add(TurretSuper.turretPrice);
        prices.add(300);
        prices.add(null);
        prices.add(null);
    }

    /**
     * Metoda kontrolujici jestli je data moznost zvolena pro nejakou vezku
     *
     */
    public boolean isOptionForBuilding() {
        if (this.choosedOption == 0 || this.choosedOption == 1 || this.choosedOption == 2) {
            return true;
        }
        return false;
    }

    /**
     * Metoda kontrolujici jestli je dana moznost pro prodavani (kos)
     *
     */
    public boolean isOptionForSell() {
        if (this.choosedOption == 4) {
            return true;
        }
        return false;
    }

    /**
     * Metoda kontrolujici jestli je dana moznost pro magicky utok
     *
     */
    public boolean isOptionForUtility() {
        //System.out.println(this.choosedOption);
        return this.choosedOption == 3;
    }

    /**
     * Getter pro moznost
     *
     */
    public int getChoosedOption() {
        return choosedOption;
    }

}
