/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Rectangle;

/**
 *Třída reprezentující, jednotlivé části mapy
 * @author Vojta
 */
public class Block extends Rectangle{
        
    public Block(int x, int y, int width, int height){
        setBounds(x, y, width, height);
    }
}
