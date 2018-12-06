/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Třída kde probíhá nekonečný běh programu, kontroluje celkový běh hry
 *
 * @author Vojta
 */
public class Controller {

    private final Timeline timer;
    private final View view;
    private final Model model;
    private ArrayList<Mob> toDelete;

    public Controller(View view, Model model) {
        timer = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {

                if (!model.getIsGameOver()) {
                    GameControl();
                }

                model.GameOver();
                view.update();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        this.model = model;
        this.view = view;

    }

    /**
     * Metoda volající jednotlivé bloky pro kontrolu hry
     *
     * @author Vojta
     */
    public void GameControl() {
        synchronized (model) {
            toDelete = new ArrayList<>();
            model.spawnMob();
            buildingProcess();
            sellingProcess();
            utilityProcess();
            upgradeTurretProcess();
            gameLogicProcess();
        }
    }

    /**
     * Metoda zarizuici staveci proces vezek Dochazi, ke kontrole jestli je klik
     * ve svete, kontrola jestli neni v ceste nebo jiz na postavene vezicce
     *
     * @author Vojta
     */
    private void buildingProcess() {
        if (!(Model.mousePointClicked == null)) {
            if (model.getWorld().isInWorld(Model.mousePointClicked2D, Model.mousePointClicked) && model.isValidPositionForTurret()
                    && model.getWorld().isOutOfRoad(Model.mousePointClicked2D, Model.mousePointClicked)) {
                Block block = World.block_map[model.getMousePointClicked().y][model.getMousePointClicked().x];

                if (model.getPanel().isOptionForBuilding()) {
                    int turetPrice;
                    switch (model.getPanel().getChoosedOption()) {
                        case 0:
                            turetPrice = TurretSingle.turretPrice;
                            break;
                        case 1:
                            turetPrice = TurretMulti.turretPrice;
                            break;
                        case 2:
                            turetPrice = TurretSuper.turretPrice;
                            break;
                        default:
                            turetPrice = 9999999;
                    }

                    if (Player.coins >= turetPrice) {
                        TurretObject building = null;
                        switch (model.getPanel().getChoosedOption()) {
                            case 0:
                                building = new TurretSingle(block);
                                break;
                            case 1:
                                building = new TurretMulti(block);
                                break;
                            case 2:
                                building = new TurretSuper(block);
                                break;
                        }

                        model.buildTurret(building);

                        Player.coins -= turetPrice;
                        Model.mousePointClicked = null;
                    }
                }
            }
        }
    }

    /**
     * Metoda zarizuici procesy ve hre, strileni vezek, nastaveni cilu
     *
     *
     * @author Vojta
     */
    private void gameLogicProcess() {
        for (TurretObject turret : model.getTurrets()) {
            turret.targets.clear();
        }

        for (TurretObject turret : model.getTurrets()) {
            for (Mob mob : model.getMobs()) {
                if (mob.intersects(turret.getArea())) {
                    turret.setTarget(mob);
                }
            }
        }

        for (TurretObject turret : model.getTurrets()) {
            if (turret.fire()) {
            }
        }

        for (Mob mob : model.getMobs()) {
            if (mob.health < 1) {
                Player.coins++;
                toDelete.add(mob);
            }
            mob.move();
            if (mob.x == model.getWorld().getEnd().x) {
                toDelete.add(mob);
                Audio.playSound("src/thegame/audio/hitplayer.wav");
                Player.health -= 2;
            }
        }

        model.getPanel().optionSetter(model.optionClickedGetter());
        model.getMobs().removeAll(toDelete);
        Model.mousePointClicked = null;

    }

    /**
     * Metoda zarizujici proces vylepseni vezek
     *
     * @author Vojta
     */
    private void upgradeTurretProcess() {
        if (model.SelectedTurret() != -1 && Model.mousePointClicked2D != null) {
            if (model.goUpgradeTurret()) {
                model.getTurrets().get(model.SelectedTurret()).upgrade();
                Model.mousePointClicked2D = null;
            }
        }
    }

    /**
     * Metoda zarizujici proces prodeje vezek
     *
     * @author Vojta
     */
    private void sellingProcess() {
        if (Model.mousePointClicked != null && model.getWorld().isInWorld(Model.mousePointClicked2D, Model.mousePointClicked)) {

            Block block = World.block_map[model.getMousePointClicked().y][model.getMousePointClicked().x];
            if (model.getPanel().isOptionForSell() && model.sellTurret(block) != -1) {
                model.getTurrets().remove(model.sellTurret(block));
                Audio.playSoundNoControl("src/thegame/audio/sell.wav");
                Player.coins += 20;
            }
        }
    }

    /**
     * Metoda zarizujici proces magickeho utoku ve hre
     *
     * @author Vojta
     */
    private void utilityProcess() {
        if (model.getPanel().isOptionForUtility() && Player.coins > 300) {
            model.utility(toDelete);
            Player.coins -= 300;
            Model.mousePointClicked2D = null;
            Audio.playSoundNoControl("src/thegame/audio/MAGIC.wav");
        }
    }

    public boolean isRunning() {
        return timer.getStatus() == Timeline.Status.RUNNING;
    }

    void stop() {
        timer.stop();
    }

    void start() {
        view.update();
        timer.play();
    }
}
