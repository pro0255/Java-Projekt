/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

/**
 * Trida repezentujici specialni vez
 * 
 * @author Vojta
 */
public class TurretSingle extends TurretObject {
    
    public static int turretPrice = Loader.data.get("TurretSingle_turretPrice");;
    
    public TurretSingle(Block block) {
        super(block);
        this.damage = Loader.data.get("TurretSingle_damage");;
        this.areaRadius = Loader.data.get("TurretSingle_areaRadius");;
        this.numberoftargets = Loader.data.get("TurretSingle_numberoftargets");;
        this.turretID = 0;
        this.level = 1;
        this.setArea();
    }
}
