package dev.molemadness;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MoleMadnessApplication extends Application {

    private StackPane root;
    private Scene scene;
    private Menu menu;

    private GameController gameController;

    private StackPane gameOverOverlay;

    private String difficulty;

    private Label livesLabel;
    private Label scoreLabel;

    private final double virtualWidth = 1280;
    private final double virtualHeight = 720;

    @Override
    public void start(Stage stage){
        root = new StackPane();;
        root.setStyle("-fx-background-color: #2e7d32");

        menu = new Menu(this::startGame, this::showDifficultyMenu, this::onDifficultySelected);

        Node menuScreen = menu.createMainMenu();
        root.getChildren().add(menuScreen);

        scene = new Scene(root, virtualWidth, virtualHeight);
        scene.setFill(Color.BLACK);

        stage.setMinWidth(1280);
        stage.setMinHeight(720);

        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);

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

        livesLabel = new Label("Lives: 0");
        livesLabel.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 28));
        livesLabel.setTextFill(Color.web("#c62828"));

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 28));
        scoreLabel.setTextFill(Color.web("#c62828"));

        VBox labelBox = new VBox(20, livesLabel, scoreLabel);
        labelBox.setPadding(new Insets(20));

        labelBox.setMouseTransparent(true);

        StackPane gameLayout = new StackPane();
        gameLayout.getChildren().addAll(canvas, labelBox);

        StackPane.setAlignment(canvas, Pos.CENTER);
        StackPane.setAlignment(labelBox, Pos.TOP_LEFT);
        StackPane.setMargin(labelBox, new Insets(10, 0, 0, 15));

        gameLayout.setUserData(canvas);

        canvas.scaleXProperty().bind(scene.widthProperty().divide(virtualWidth));
        canvas.scaleYProperty().bind(scene.heightProperty().divide(virtualHeight));

        return gameLayout;
    }

    public void startGame() {
        Pane canvasContainer = (Pane) createGame();
        Canvas canvas = (Canvas) canvasContainer.getUserData();

        gameController = new GameController(this::updateUI, this::gameOver, canvas, difficulty);

        gameController.start();

        switchScene(canvasContainer);
    }

    public void updateUI() {
        livesLabel.setText("Lives: " + gameController.getLives());
        scoreLabel.setText("Score: " + gameController.getScore());
    }

    public void gameOver() {
        if(gameOverOverlay != null) {
            root.getChildren().remove(gameOverOverlay);
        }

        Label gameOverLabel = new Label("Game Over! \nPress ESC to retry\nPress TAB for menu");
        gameOverLabel.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 30));
        gameOverLabel.setTextFill(Color.web("#c62828"));
        gameOverLabel.setPadding(new Insets(10));
        gameOverLabel.setStyle("""
                -fx-border-color: white;
                -fx-border-width: 3;
                -fx-border-radius: 10;
                -fx-background-color: transparent;
                """);

        gameOverOverlay = new StackPane(gameOverLabel);

        StackPane.setAlignment(gameOverLabel, Pos.TOP_CENTER);
        StackPane.setMargin(gameOverLabel, new Insets(15, 0, 0, 0));

        root.getChildren().add(gameOverOverlay);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE -> {
                    root.getChildren().remove(gameOverOverlay);
                    gameOverOverlay = null;

                    startGame();

                    scene.setOnKeyPressed(null);
                }
                case TAB -> {
                    root.getChildren().remove(gameOverOverlay);
                    gameOverOverlay = null;

                    showMainMenu();

                    scene.setOnKeyPressed(null);
                }
            }
        });
    }

}
