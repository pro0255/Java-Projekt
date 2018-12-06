/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * Trida reprezentujici main soubor pro hru
 * 
 * @author Vojta
 */
public class TheGame extends Application {

    private final Model model;
    private View view;
    private Controller controller;


    public TheGame() {
        model = new Model();
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane basePane = new AnchorPane();
        Button btnStart = new Button();
        btnStart.setText("Start game");

        btnStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (controller.isRunning()) {
                    controller.stop();
                    btnStart.setText("Unpause game");

                } else {
                    controller.start();
                    model.setIsMainMenu();
                    btnStart.setText("Pause game");
                }
            }
        });

        basePane.getChildren().add(btnStart);
        AnchorPane.setTopAnchor(btnStart, 0.0);
        AnchorPane.setLeftAnchor(btnStart, 0.0);
        AnchorPane.setRightAnchor(btnStart, 0.0);

        Pane root = new Pane();
        Canvas canvas = new Canvas(View.WIDTH, View.HEIGHT);
        root.getChildren().add(canvas);

        canvas.scaleXProperty().bind(root.widthProperty().multiply(1.0 / View.WIDTH));
        canvas.scaleYProperty().bind(root.heightProperty().multiply(1.0 / View.HEIGHT));
        canvas.translateXProperty().bind(root.widthProperty().subtract(View.WIDTH).divide(2));
        canvas.translateYProperty().bind(root.heightProperty().subtract(View.HEIGHT).divide(2));

        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                canvas.setCursor(javafx.scene.Cursor.NONE);
                model.setCursor(event.getX(), event.getY());
                view.update();
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                model.setClickedCursor(event.getX(), event.getY());
            }
        });

        view = new View(canvas.getGraphicsContext2D(), model);
        controller = new Controller(view, model);

        basePane.getChildren().add(root);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 30.0);

        Scene scene = new Scene(basePane, 800, 630);
        primaryStage.setTitle("Tower Deffense");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

}
