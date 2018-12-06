/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

//import java.awt.Font;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Trida zajistujici vykresleni vsech objektu ve hre
 *
 * @author Vojta
 */
public final class View {

    public final static int HEIGHT = 600;
    public final static int WIDTH = 800;
    private Image grass, brick, monster, heart, coin, turret_image, castle, button, cross, defaultcursor, upgrade, gameover, monster1, monster2, mainmenu, score, reset, menu, help;
    private ArrayList<Image> cros;
    private ArrayList<Image> turrets;

    private final GraphicsContext context;
    private final Model model;

    private void drawImage(Image image, Block block) {
        context.drawImage(image, block.x, block.y, block.width, block.height);
    }

    private void define_cross() {
        cros = new ArrayList<>();
        cros.add(new Image("file:src/thegame/image/turret_cursor.png"));
        cros.add(new Image("file:src/thegame/image/turret2_cursor.png"));
        cros.add(new Image("file:src/thegame/image/tower3_cursor.png"));
        cros.add(new Image("file:src/thegame/image/super_power.png"));
        cros.add(new Image("file:src/thegame/image/rubbish_cursor.png"));
        cros.add(defaultcursor);
    }

    private void define_turrets() {
        turrets = new ArrayList<>();
        turrets.add(new Image("file:src/thegame/image/turret.png"));
        turrets.add(new Image("file:src/thegame/image/turret2.png"));
        turrets.add(new Image("file:src/thegame/image/tower3.png"));
    }

    View(GraphicsContext context, Model model) {
        this.context = context;
        this.model = model;
        grass = new Image("file:src/thegame/image/grass_new.png");
        monster = new Image("file:src/thegame/image/monster_fu.png");
        brick = new Image("file:src/thegame/image/brick_new.png");
        heart = new Image("file:src/thegame/image/heart_pixel.png");
        coin = new Image("file:src/thegame/image/coin.png");
        turret_image = new Image("file:src/thegame/image/turret.png");
        castle = new Image("file:src/thegame/image/castle_new.png");
        button = new Image("file:src/thegame/image/button_new.png");
        defaultcursor = new Image("file:src/thegame/image/cross.png");
        upgrade = new Image("file:src/thegame/image/repair.png");
        gameover = new Image("file:src/thegame/image/gameover.png");
        mainmenu = new Image("file:src/thegame/image/startgame.png");
        monster1 = new Image("file:src/thegame/image/monster1.png");
        monster2 = new Image("file:src/thegame/image/monster2.png");
        score = new Image("file:src/thegame/image/score.png");
        reset = new Image("file:src/thegame/image/reset.png");
        menu = new Image("file:src/thegame/image/menu.png");
        help = new Image("file:src/thegame/image/help.png");
        cross = defaultcursor;
        define_cross();
        define_turrets();
        update();

    }

    public void drawWorld() {
        context.setFill(new Color(0.4f, 0.4f, 0.4f, 1f));
        context.fillRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
        for (int i = 0; i < model.getWorld().HEIGTH_WORLD; i++) {
            for (int j = 0; j < model.getWorld().WIDTH_WORLD; j++) {
                if (model.getWorld().map_contruction[i][j] == true) {
                    drawImage(grass, model.getWorld().block_map[i][j]);
                } else {
                    drawImage(brick, model.getWorld().block_map[i][j]);
                }
            }
        }
        drawImage(castle, model.getWorld().getEnd());
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.strokeLine(World.block_map[0][0].x, World.block_map[0][0].y, World.block_map[model.world.HEIGTH_WORLD - 1][0].x, World.block_map[model.world.HEIGTH_WORLD - 1][0].y + World.blockSize);
        context.strokeLine(World.block_map[0][model.world.WIDTH_WORLD - 1].x + World.blockSize, World.block_map[0][model.world.WIDTH_WORLD - 1].y, World.block_map[model.world.HEIGTH_WORLD - 1][model.world.WIDTH_WORLD - 1].x + World.blockSize, World.block_map[model.world.HEIGTH_WORLD - 1][model.world.WIDTH_WORLD - 1].y + World.blockSize);
        context.strokeLine(World.block_map[model.world.HEIGTH_WORLD - 1][0].x, World.block_map[model.world.HEIGTH_WORLD - 1][0].y + World.blockSize, World.block_map[model.world.HEIGTH_WORLD - 1][model.world.WIDTH_WORLD - 1].x + World.blockSize, World.block_map[model.world.HEIGTH_WORLD - 1][model.world.WIDTH_WORLD - 1].y + World.blockSize);

    }

    public void drawScore() {
        if (model.equalScoreBar()) {
            context.setFill(Color.GREEN);
            context.fillRect(0, 0, View.WIDTH / 2, View.HEIGHT);
            context.setFill(Color.BLACK);
            context.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 30));
            context.fillText("SCORE", View.WIDTH / 4 - 40, 50);
            context.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, 20));
            for (int i = 0; i < model.player.getPS().topScore.length; i++) {
                context.fillText(model.player.getPS().topScore[i][0] + "\t" + model.player.getPS().topScore[i][1], 50, ((View.HEIGHT - 200) / 10) * i + 100);
            }
        }
    }

    public void drawPlayer() {
        context.drawImage(heart, Player.HealthBar.x, Player.HealthBar.y, Player.HealthBar.width, Player.HealthBar.height);
        context.drawImage(coin, Player.CoinBar.x, Player.CoinBar.y, Player.CoinBar.width, Player.CoinBar.height);
        context.setFill(Color.BLACK);
        context.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
        context.fillText(Integer.toString(Player.health), Player.HealthBar.x + 30, Player.HealthBar.y + 17);
        context.fillText(Integer.toString(Player.coins), Player.HealthBar.x + 30, Player.HealthBar.y + 47);
        context.drawImage(monster, Player.CoinBar.x - 5, Player.CoinBar.y + 20, monster.getWidth() / 17, monster.getHeight() / 17);
        context.fillText("STAGE: " + Integer.toString(model.getStage().getWAVE_level()), Player.HealthBar.x + 30, Player.HealthBar.y + 77);
        context.drawImage(score, Player.ScoreBar.x, Player.ScoreBar.y, Player.ScoreBar.width, Player.ScoreBar.height);
        context.drawImage(reset, Player.ResetBar.x, Player.ResetBar.y, Player.ResetBar.width, Player.ResetBar.height);
        context.drawImage(menu, Player.MainMenuBar.x, Player.MainMenuBar.y, Player.MainMenuBar.width, Player.MainMenuBar.height);
        context.drawImage(help, Player.HelpBar.x, Player.HelpBar.y, Player.HelpBar.width, Player.HelpBar.height);
    }

    public void drawMobHealthBar(Mob mob) {
        context.setFill(Color.BLACK);
        context.fillRect(mob.x, mob.y - mob.HEALTHBARSPACE, mob.width, mob.HEALTHBARHEIGHT);
        context.setFill(Color.RED);
        context.fillRect(mob.x + 1, mob.y - mob.HEALTHBARSPACE + 1, mob.width - 2, mob.HEALTHBARHEIGHT - 2);
        context.setFill(new Color(0.1019f, 1f, 0.1019f, 1f));
        context.fillRect(mob.x + 1, mob.y - mob.HEALTHBARSPACE + 1, (mob.width * (mob.health / (float) mob.maxhealth) - 2), mob.HEALTHBARHEIGHT - 2);
    }

    public void setCursor(int value) {
        if (value != -1 && value != 3) {
            cross = cros.get(value);
        }
    }

    public void updateActiveGame() {

        this.drawWorld();
        this.drawPlayer();

        for (int i = 0; i < model.getPanel().panel.size(); i++) {
            context.setFill(new Color(0.6f, 0.6f, 0.6f, 1f));
            context.fillRect(model.getPanel().panel.get(i).x, model.getPanel().panel.get(i).y, model.getPanel().panel.get(i).width, model.getPanel().panel.get(i).height);

            context.drawImage(cros.get(i), model.getPanel().panel.get(i).x, model.getPanel().panel.get(i).y, model.getPanel().panel.get(i).width, model.getPanel().panel.get(i).height);
            context.drawImage(button, model.getPanel().panel.get(i).x, model.getPanel().panel.get(i).y, model.getPanel().panel.get(i).width, model.getPanel().panel.get(i).height);

            if (model.getPanel().prices.get(i) != null) {
                context.setFill(new Color(.8f, .8f, 0f, 1f));
                context.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
                context.fillText(Integer.toString(model.getPanel().prices.get(i)) + "$", model.getPanel().panel.get(i).x + 11, model.getPanel().panel.get(i).y - 3);
            }
        }

        /*
        for (Rectangle rectangle : model.getPanel().panel) {
            context.setFill(new Color(0.6f, 0.6f, 0.6f, 1f));
            context.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            context.drawImage(turret_image, model.getPanel().panel.get(0).x, model.getPanel().panel.get(0).y, model.getPanel().panel.get(0).width, model.getPanel().panel.get(0).height);
            context.drawImage(button, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
         */
        synchronized (model) {
            for (Mob mob : model.getMobs()) {

                long timeMillis = System.currentTimeMillis();
                if (TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 2 == 0) {
                    context.drawImage(monster1, mob.x, mob.y, mob.mobSize, mob.mobSize);
                } else {
                    context.drawImage(monster2, mob.x, mob.y, mob.mobSize, mob.mobSize);
                }

                drawMobHealthBar(mob);
            }

            for (TurretObject turret : model.getTurrets()) {
                context.drawImage(turrets.get(turret.turretID), turret.x, turret.y, turret.width, turret.height);
                context.setFill(Color.WHITE);
                context.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 10));
                context.fillText("Lv"+Integer.toString(turret.level), turret.x+turret.width/2, turret.y+turret.height);
                for (Mob mob : turret.targets) {
                    context.setStroke(new Color((turret.turretID * 0.2) + 0.2, 0f, 0f, 1f));
                    context.setLineWidth(5);
                    context.strokeLine(turret.x + turret.width / 2, turret.y + turret.height / 2, mob.x + mob.width / 2, mob.y + mob.height / 2);

                }
            }

            if (model.SelectedTurret() != -1) {
                context.setStroke(Color.RED);
                context.setLineWidth(5);
                context.strokeRect(model.getTurrets().get(model.SelectedTurret()).x, model.getTurrets().get(model.SelectedTurret()).y, model.getTurrets().get(model.SelectedTurret()).width, model.getTurrets().get(model.SelectedTurret()).height);
                context.drawImage(upgrade, TurretObject.upgrade.x, TurretObject.upgrade.y, TurretObject.upgrade.width, TurretObject.upgrade.height);
            }

            if (model.equal() != -1) {
                context.setStroke(Color.YELLOW);
                context.setLineWidth(2);
                context.strokeRect(model.getTurrets().get(model.equal()).getArea().getX(), model.getTurrets().get(model.equal()).getArea().getY(), model.getTurrets().get(model.equal()).getArea().getWidth(), model.getTurrets().get(model.equal()).getArea().getHeight());

                context.setFill(Color.BLUE);
                context.fillRect(TurretObject.turretInfo.x, TurretObject.turretInfo.y, TurretObject.turretInfo.width, TurretObject.turretInfo.height);

                context.setFill(Color.BLACK);
                context.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
                context.fillText("DAMAGE : " + Integer.toString(model.getTurrets().get(model.equal()).damage), TurretObject.turretInfo.width / 2, 40 + TurretObject.turretInfo.y);
                context.fillText("AREA RADIUS : " + Integer.toString(model.getTurrets().get(model.equal()).areaRadius), TurretObject.turretInfo.width / 2, 60 + TurretObject.turretInfo.y);
                context.fillText("NUMBER OF TARGETS : " + Integer.toString(model.getTurrets().get(model.equal()).numberoftargets), TurretObject.turretInfo.width / 2, 80 + TurretObject.turretInfo.y);
                context.fillText("TURRET ID : " + Integer.toString(model.getTurrets().get(model.equal()).turretID), TurretObject.turretInfo.width / 2, 100 + TurretObject.turretInfo.y);
                context.fillText("TURRET LEVEL : " + Integer.toString(model.getTurrets().get(model.equal()).level), TurretObject.turretInfo.width / 2, 120 + TurretObject.turretInfo.y);
            }

            if (model.equalPanelIndex() != -1) {
                context.setFill(new Color(0.3f, 0.3f, 0.3f, 0.5f));

                context.fillRect(model.getPanel().panel.get(model.equalPanelIndex()).x, model.getPanel().panel.get(model.equalPanelIndex()).y, model.getPanel().panel.get(model.equalPanelIndex()).width, model.getPanel().panel.get(model.equalPanelIndex()).height);
            }

            if (Model.mousePointMove2D != null) {
                context.drawImage(cross, Model.mousePointMove2D.getX() - cross.getWidth() / 2, Model.mousePointMove2D.getY() - cross.getHeight() / 2);
            }

            drawScore();

            setCursor(model.getPanel().getChoosedOption());

        }

    }

    /*
        for (Rectangle rectangle : model.getPanel().panel) {
            context.setFill(new Color(0.6f, 0.6f, 0.6f, 1f));
            context.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            context.drawImage(turret_image, model.getPanel().panel.get(0).x, model.getPanel().panel.get(0).y, model.getPanel().panel.get(0).width, model.getPanel().panel.get(0).height);
            context.drawImage(button, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
     */
    public void update() {

        if (!model.getIsGameOver() && !model.getIsMainMenu()) {
            updateActiveGame();
        } else if (model.getIsGameOver()) {
            context.drawImage(gameover, 0, 0, View.WIDTH, View.HEIGHT);
            long timeMillis = System.currentTimeMillis();
            for (int i = 0; i < 9; i++) {
                if (TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 2 == 0) {
                    if (i % 2 == 0) {
                        context.drawImage(monster, (0 + (monster.getWidth() / 5) / 2 + 20) * (i + 1), (View.HEIGHT / 2 - (monster.getWidth() / 5) / 2), monster.getWidth() / 5, monster.getHeight() / 5);
                    } else {
                        context.drawImage(monster, (0 + (monster.getWidth() / 5) / 2 + 20) * (i + 1), (View.HEIGHT / 2 - (monster.getWidth() / 5) / 2) - 15, monster.getWidth() / 5, monster.getHeight() / 5);
                    }
                } else {
                    if (i % 2 == 1) {
                        context.drawImage(monster, (0 + (monster.getWidth() / 5) / 2 + 20) * (i + 1), (View.HEIGHT / 2 - (monster.getWidth() / 5) / 2), monster.getWidth() / 5, monster.getHeight() / 5);
                    } else {
                        context.drawImage(monster, (0 + (monster.getWidth() / 5) / 2 + 20) * (i + 1), (View.HEIGHT / 2 - (monster.getWidth() / 5) / 2) - 15, monster.getWidth() / 5, monster.getHeight() / 5);
                    }

                }

            }
        } else if (model.getIsMainMenu()) {
            context.drawImage(mainmenu, 0, 0, View.WIDTH, View.HEIGHT);
        }
        /*
        this.drawWorld();
        this.drawPlayer();

        for (int i = 0; i < model.getPanel().panel.size(); i++) {
            context.setFill(new Color(0.6f, 0.6f, 0.6f, 1f));
            context.fillRect(model.getPanel().panel.get(i).x, model.getPanel().panel.get(i).y, model.getPanel().panel.get(i).width, model.getPanel().panel.get(i).height);

            context.drawImage(cros.get(i), model.getPanel().panel.get(i).x, model.getPanel().panel.get(i).y, model.getPanel().panel.get(i).width, model.getPanel().panel.get(i).height);
            context.drawImage(button, model.getPanel().panel.get(i).x, model.getPanel().panel.get(i).y, model.getPanel().panel.get(i).width, model.getPanel().panel.get(i).height);

            if (model.getPanel().prices.get(i) != null) {
                context.setFill(new Color(.8f, .8f, 0f, 1f));
                context.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
                context.fillText(Integer.toString(model.getPanel().prices.get(i)) + "$", model.getPanel().panel.get(i).x + 11, model.getPanel().panel.get(i).y - 3);
            }
        }

        synchronized (model) {
            for (Mob mob : model.getMobs()) {
                context.drawImage(monster, mob.x, mob.y, mob.mobSize, mob.mobSize);
                drawMobHealthBar(mob);
            }

            for (TurretObject turret : model.getTurrets()) {
                context.drawImage(turrets.get(turret.turretID), turret.x, turret.y, turret.width, turret.height);

                for (Mob mob : turret.targets) {
                    context.setStroke(new Color((turret.turretID * 0.2) + 0.2, 0f, 0f, 1f));
                    context.setLineWidth(5);
                    context.strokeLine(turret.x + turret.width / 2, turret.y + turret.height / 2, mob.x + mob.width / 2, mob.y + mob.height / 2);

                }
            }

            if (model.SelectedTurret() != -1) {
                context.setStroke(Color.RED);
                context.setLineWidth(5);
                context.strokeRect(model.getTurrets().get(model.SelectedTurret()).x, model.getTurrets().get(model.SelectedTurret()).y, model.getTurrets().get(model.SelectedTurret()).width, model.getTurrets().get(model.SelectedTurret()).height);
                context.drawImage(upgrade, TurretObject.upgrade.x, TurretObject.upgrade.y, TurretObject.upgrade.width, TurretObject.upgrade.height);
            }

            if (model.equal() != -1) {
                context.setStroke(Color.YELLOW);
                context.setLineWidth(2);
                context.strokeRect(model.getTurrets().get(model.equal()).getArea().getX(), model.getTurrets().get(model.equal()).getArea().getY(), model.getTurrets().get(model.equal()).getArea().getWidth(), model.getTurrets().get(model.equal()).getArea().getHeight());

                context.setFill(Color.BLUE);
                context.fillRect(TurretObject.turretInfo.x, TurretObject.turretInfo.y, TurretObject.turretInfo.width, TurretObject.turretInfo.height);

                context.setFill(Color.BLACK);
                context.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
                context.fillText("DAMAGE : " + Integer.toString(model.getTurrets().get(model.equal()).damage), TurretObject.turretInfo.width / 2, 40 + TurretObject.turretInfo.y);
                context.fillText("AREA RADIUS : " + Integer.toString(model.getTurrets().get(model.equal()).areaRadius), TurretObject.turretInfo.width / 2, 60 + TurretObject.turretInfo.y);
                context.fillText("NUMBER OF TARGETS : " + Integer.toString(model.getTurrets().get(model.equal()).numberoftargets), TurretObject.turretInfo.width / 2, 80 + TurretObject.turretInfo.y);
                context.fillText("TURRET ID : " + Integer.toString(model.getTurrets().get(model.equal()).turretID), TurretObject.turretInfo.width / 2, 100 + TurretObject.turretInfo.y);
                context.fillText("TURRET LEVEL : " + Integer.toString(model.getTurrets().get(model.equal()).level), TurretObject.turretInfo.width / 2, 120 + TurretObject.turretInfo.y);
            }

            if (model.equalPanelIndex() != -1) {
                context.setFill(new Color(0.3f, 0.3f, 0.3f, 0.5f));

                context.fillRect(model.getPanel().panel.get(model.equalPanelIndex()).x, model.getPanel().panel.get(model.equalPanelIndex()).y, model.getPanel().panel.get(model.equalPanelIndex()).width, model.getPanel().panel.get(model.equalPanelIndex()).height);
            }
            if (Model.mousePointMove2D != null) {
                context.drawImage(cross, Model.mousePointMove2D.getX() - cross.getWidth() / 2, Model.mousePointMove2D.getY() - cross.getHeight() / 2);
            }

            setCursor(model.getPanel().getChoosedOption());
        }
         */
    }
}
