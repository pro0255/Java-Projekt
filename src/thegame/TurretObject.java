/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Trida repezentujici abstraktni object pro vez
 *
 * @author Vojta
 */
public abstract class TurretObject extends Rectangle {

    public int areaRadius;
    public int damage;
    public int numberoftargets;
    public int turretID;
    public int level;
    public Rectangle area;
    public ArrayList<Mob> targets;
    public static Rectangle turretInfo = new Rectangle(0, World.block_map[World.map_contruction.length - 1][0].y + World.blockSize, View.WIDTH, View.HEIGHT - World.block_map[World.map_contruction.length - 1][0].y);
    public static Rectangle upgrade = new Rectangle(View.WIDTH - View.WIDTH / 4, World.block_map[World.map_contruction.length - 1][0].y + World.blockSize, View.WIDTH / 4, View.HEIGHT - World.block_map[World.map_contruction.length - 1][0].y - World.blockSize);
    public static int upgradeCost = 50;

    public TurretObject(Block block) {
        setBounds(block.x, block.y, block.width, block.height);
        targets = new ArrayList<>();
    }

    /**
     * Metoda nastavujici target pro vezku
     *
     */
    public void setTarget(Mob target) {
        if (targets.size() < numberoftargets) {
            targets.add(target);
        }
    }

    /**
     * Metoda zajistujici strileni na prisery, targety pro danou vez
     *
     */
    public boolean fire() {
        if (targets.isEmpty()) {
            return false;
        }
        for (Mob mob : targets) {
            if (mob != null) {
                mob.health -= this.damage;
            }

        }
        return true;
    }

    /**
     * Metoda zajistujici upgrade vezky
     *
     */
    public void upgrade() {
        Audio.playSoundNoControl("src/thegame/audio/upgrade.wav");
        damage++;
        level++;
        Player.coins -= TurretObject.upgradeCost;
    }

    /**
     * Metoda nastavujici scopu pro vez
     *
     */
    public void setArea() {
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        area = new Rectangle(centerX - areaRadius, centerY - areaRadius, 2 * areaRadius, 2 * areaRadius);
    }

    
    /**
     * Getter
     *
     */
    public Rectangle getArea() {
        return area;
    }

}
