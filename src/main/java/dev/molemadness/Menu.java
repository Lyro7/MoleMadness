package dev.molemadness;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

public class Menu {

    private final Runnable onPlay;
    private final Runnable onDiff;
    private final Consumer<String> onDiffSel;

    public Menu(Runnable onPlay, Runnable onDiff, Consumer<String> onDiffSel) {
        this.onPlay = onPlay;
        this.onDiff = onDiff;
        this.onDiffSel = onDiffSel;
    }

    public Node createMainMenu() {
        Label title = createTitle();

        Button playButton = new Button("Play");
        playButton.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 32));
        playButton.setTextFill(Color.web("#c62828"));
        playButton.setPrefWidth(300);
        playButton.setOnAction(e -> onPlay.run());

        Button difficultyButton = new Button("Difficulty");
        difficultyButton.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 32));
        difficultyButton.setTextFill(Color.web("#c62828"));
        difficultyButton.setPrefWidth(300);
        difficultyButton.setOnAction(e -> onDiff.run());

        Button exitButton = new Button("Exit");
        exitButton.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 32));
        exitButton.setTextFill(Color.web("#c62828"));
        exitButton.setPrefWidth(300);
        exitButton.setOnAction(e -> System.exit(0));

        VBox titleBox = new VBox(10);
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.TOP_CENTER);

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(titleBox, buttonBox);

        return layout;
    }

    public Node createDifficultyMenu() {
        Label title = createTitle();

        Button button1 = createDifficultyButton("Easy");
        Button button2 = createDifficultyButton("Middle");
        Button button3 = createDifficultyButton("Hard");

        VBox titleBox = new VBox(10);
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.TOP_CENTER);

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(button1, button2, button3);
        buttonBox.setAlignment(Pos.CENTER);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(titleBox, buttonBox);

        return layout;
    }

    private Button createDifficultyButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 32));
        button.setTextFill(Color.web("#c62828"));
        button.setPrefWidth(300);
        button.setOnAction(e -> onDiffSel.accept(name));
        return button;
    }

    private Label createTitle() {
        Label title = new Label("Mole Madness");
        title.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 64));
        title.setTextFill(Color.web("#c62828"));
        title.setPadding(new Insets(20));

        DropShadow glow = new DropShadow();
        glow.setOffsetX(0);
        glow.setOffsetY(0);
        glow.setRadius(8);
        glow.setColor(Color.color(1, 1, 1, 0.8));

        title.setEffect(glow);

        return title;
    }

}
