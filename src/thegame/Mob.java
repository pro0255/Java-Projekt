/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Rectangle;

/**
 * Trida reprezentujici jednotlive prisery
 *
 * @author proko
 */
public class Mob extends Rectangle {

    public int border = 5;
    public int mobSize = 52;
    public int x_block_position, y_block_position;
    public int walkspeed = 0;
    public int up_move = 0;
    public int down_move = 1;
    public int right_move = 2;
    public int left_move = 3;
    public int move_direction;
    public int count_move = 0;
    public boolean isAlive = false;
    public boolean hasUpward = false;
    public boolean hasDownward = false;
    public boolean hasRight = false;
    public boolean hasLeft = false;

    public static int maxhealth = 200;
    public int health;
    public int HEALTHBARHEIGHT = 8;
    public int HEALTHBARSPACE = 2;

    /**
     * Konstruktor
     *
     * @author Vojta
     */
    public Mob() {
        this.spawn();
    }

    /**
     * Metoda reprezentujici pohyb
     *
     * @author Vojta
     */
    public void move() {
        if (this.move_direction == this.up_move) {
            this.y--;
        } else if (this.move_direction == this.right_move) {
            this.x++;
        } else if (this.move_direction == this.left_move) {
            this.x--;
        } else {
            this.y++;
        }

        this.count_move++;
        if (this.count_move % World.blockSize == 0) {
            if (this.move_direction == this.right_move) {
                this.x_block_position++;
                this.hasRight = true;
            } else if (this.move_direction == this.up_move) {
                this.y_block_position--;
                this.hasUpward = true;
            } else if (this.move_direction == this.left_move) {
                this.x_block_position--;
                this.hasLeft = true;
            } else {
                this.y_block_position++;
                this.hasDownward = true;
            }

            if (this.move_direction == this.right_move) {
                if (World.map_contruction[this.y_block_position][this.x_block_position + 1] == true) {
                    find_direction();
                }
            }
            if (this.move_direction == this.left_move) {
                if (World.map_contruction[this.y_block_position][this.x_block_position - 1] == true) {
                    find_direction();
                }
            }
            if (this.move_direction == this.down_move) {
                if (World.map_contruction[this.y_block_position + 1][this.x_block_position] == true) {
                    find_direction();
                }
            }
            if (this.move_direction == this.up_move) {
                if (World.map_contruction[this.y_block_position - 1][this.x_block_position] == true) {
                    find_direction();
                }
            }

            this.count_move = 0;
            this.hasDownward = false;
            this.hasUpward = false;
            this.hasLeft = false;
            this.hasRight = false;
        }
    }

    /**
     * Metoda pro nalezeni nasledneho smeru v mape
     *
     * @author Vojta
     */
    
    public void find_direction() {
        if (World.map_contruction[this.y_block_position][this.x_block_position + 1] == false && !this.hasLeft) {
            this.move_direction = this.right_move;
        } else if (World.map_contruction[this.y_block_position + 1][this.x_block_position] == false && !this.hasUpward) {
            this.move_direction = this.down_move;
        } else if (World.map_contruction[this.y_block_position - 1][this.x_block_position] == false && !this.hasDownward) {
            this.move_direction = this.up_move;
        } else if (World.map_contruction[this.y_block_position][this.x_block_position - 1] == false && !this.hasRight) {
            this.move_direction = this.left_move;
        }
    }

    /**
     * Metoda volana v konstruktoru, kde se nastavuji instancni promenne prisery
     *
     * @author Vojta
     */
    private void spawn() {
        this.mobSize -= this.border * 2;
        for (int y = 0; y < World.block_map.length; y++) {
            if (World.map_contruction[y][0] == false) {
                this.setBounds(World.block_map[y][0].x + this.border, World.block_map[y][0].y + this.border, mobSize, mobSize);
            }
            this.health = maxhealth;
            this.isAlive = true;
            this.x_block_position = 0;
            this.y_block_position = this.y / World.blockSize;
            this.move_direction = right_move;
        }
    }

}
