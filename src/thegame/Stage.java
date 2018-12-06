/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.util.ArrayList;

/**
 * Trida reprezentujici logiku vln ve hre a levlu
 *
 * @author Vojta
 */
public class Stage {

    private int NUMBEROFMOBS = Loader.data.get("Stage_NUMBEROFMOBS");
    private int DELAY = Loader.data.get("Stage_DELAY");

    private int WAVE_COUNTER = 1;
    private int WAVE_DELAY = Loader.data.get("Stage_WAVE_DELAY");
    public int WAVE_level = 0;
    public int STAGE_level = 1;
    private boolean started = false;

    private boolean WAVE_STARTED = false;

    public static int SPAWN_COUNTER_TIME = 0;
    public int SPAWN_COUNTER = 0;
    private boolean isSpawnedEverything = false;

    /**
     * Metoda ktera v zavislosti na case povoluje vytvoreni prisery
     *
     */
    boolean spawnMob() {

        if (SPAWN_COUNTER_TIME % DELAY == 0 && SPAWN_COUNTER < NUMBEROFMOBS) {
            SPAWN_COUNTER++;
            if (SPAWN_COUNTER == NUMBEROFMOBS) {
                isSpawnedEverything = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Metoda pro logiku vln a levelu
     *
     */
    int waveLogicSpawn(ArrayList<Mob> mobs) {
        if (WAVE_STARTED) {
            SPAWN_COUNTER_TIME++;
            if (spawnMob()) {
                return 0;
            }

            if (isSpawnedEverything) {
                if (mobs.isEmpty()) {
                    WAVE_COUNTER = 0;
                    WAVE_STARTED = false;
                }
            }
        } else {
            WAVE_COUNTER++;
            if (WAVE_COUNTER % WAVE_DELAY == 0) {

                nextLevelUpgrade();
                if (started) {
                    WAVE_level++;
                }
                started = true;
                WAVE_STARTED = true;
                isSpawnedEverything = false;
                SPAWN_COUNTER_TIME = 0;
                SPAWN_COUNTER = 0;
                if ((WAVE_level + 1) % 3 == 0) {
                    STAGE_level++;
                    return -1;
                }
            }
        }
        return 1;
    }

    /**
     * Metoda ktera zlepsuje prisery v zavislosti na levelu
     *
     */
    public void nextLevelUpgrade() {
        Mob.maxhealth += 50;
    }

    /**
     * Getter
     *
     */
    public int getWAVE_level() {
        return WAVE_level;
    }
}
