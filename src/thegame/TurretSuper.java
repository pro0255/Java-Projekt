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
public class TurretSuper extends TurretObject {

    public static int turretPrice = Loader.data.get("TurretSuper_turretPrice");


    public TurretSuper(Block block) {
        super(block);
        this.damage = Loader.data.get("TurretSuper_damage");
        this.areaRadius = Loader.data.get("TurretSuper_areaRadius");
        this.numberoftargets = Loader.data.get("TurretSuper_numberoftargets");
        this.turretID = 2;
        this.level = 1;
        this.setArea();
    }

}
