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
public class TurretMulti extends TurretObject {

    public static int turretPrice = Loader.data.get("TurretMulti_turretPrice");

    public TurretMulti(Block block) {
        super(block);
        this.damage = Loader.data.get("TurretMulti_damage"); ;
        this.areaRadius = Loader.data.get("TurretMulti_areaRadius");;
        this.numberoftargets = Loader.data.get("TurretMulti_numberoftargets");;
        this.turretID = 1;
        this.level = 1;

        this.setArea();
    }

}
