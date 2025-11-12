package dev.molemadness;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoleMadnessApplication extends Application {

    private StackPane root;
    private Menu menu;
    private String difficulty;
    private Scene scene;

    private final double virtualWidth = 1280;
    private final double virtualHeight = 720;

    @Override
    public void start(Stage stage){
        root = new StackPane();;
        menu = new Menu(this::startGame, this::showDifficultyMenu, this::onDifficultySelected);

        Node menuScreen = menu.createMainMenu();
        root.getChildren().add(menuScreen);

        scene = new Scene(root, virtualWidth, virtualHeight);

        stage.setMinWidth(800);
        stage.setMinHeight(600);

        stage.setWidth(1280);
        stage.setHeight(720);

        stage.setTitle("Mole Madness");
        stage.setScene(scene);
        stage.show();
    }

    public void init() {
        root = new StackPane();
        menu = new Menu(this::startGame, this::showDifficultyMenu, this::onDifficultySelected);
    }

    private void showMainMenu() {
        switchScene(menu.createMainMenu());
    }

    private void showDifficultyMenu() {
        switchScene(menu.createDifficultyMenu());
    }

    private void onDifficultySelected(String difficulty) {
        this.difficulty = difficulty;
        showMainMenu();
    }

    public void switchScene(Node scene) {
        root.getChildren().clear();
        root.getChildren().add(scene);
    }

    public Node createGame() {
        Canvas canvas = new Canvas(virtualWidth, virtualHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Label scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 28));
        scoreLabel.setTextFill(Color.web("#c62828"));

        StackPane gameLayout = new StackPane();
        gameLayout.getChildren().addAll(canvas, scoreLabel);

        StackPane.setMargin(scoreLabel, new Insets(10, 0, 0, 15));
        StackPane.setAlignment(canvas, Pos.CENTER);
        StackPane.setAlignment(scoreLabel, Pos.TOP_LEFT);

        gameLayout.setUserData(gc);

        gameLayout.scaleXProperty().bind(scene.widthProperty().divide(virtualWidth));
        gameLayout.scaleYProperty().bind(scene.heightProperty().divide(virtualHeight));

        return gameLayout;
    }

    public void startGame() {
        Pane canvasContainer = (Pane) createGame();
        GraphicsContext gc = (GraphicsContext) canvasContainer.getUserData();

        GameRenderer gameRenderer = new GameRenderer(gc);

        GameController gameController = new GameController(difficulty);

        List<Point2D> nativeHoles = gameController.getGameWorld().getHoles();
        List<Point2D> virtualHoles = new ArrayList<>();

        for (Point2D nativeHole : nativeHoles) {
            Point2D virtualHole = gameRenderer.nativeToVirtual(nativeHole);
            virtualHoles.add(virtualHole);
        }

        gameController.getGameWorld().setHoles(virtualHoles);

        gameController.start(gameRenderer);

        switchScene(canvasContainer);
    }

}
