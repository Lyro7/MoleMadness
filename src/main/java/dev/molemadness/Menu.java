package dev.molemadness;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
        playButton.setFont(new Font("Arial", 32));
        playButton.setPrefWidth(300);
        playButton.setOnAction(e -> onPlay.run());

        Button difficultyButton = new Button("Difficulty");
        difficultyButton.setFont(new Font("Arial", 32));
        difficultyButton.setPrefWidth(300);
        difficultyButton.setOnAction(e -> onDiff.run());

        VBox titleBox = new VBox(10);
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.TOP_CENTER);

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(playButton, difficultyButton);
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
        titleBox.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(button1, button2, button3);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane layout = new BorderPane();
        layout.setTop(titleBox);
        layout.setCenter(buttonBox);

        return layout;
    }

    Button createDifficultyButton(String name) {
        Button button = new Button(name);
        button.setFont(new Font("Arial", 32));
        button.setPrefWidth(300);
        button.setOnAction(e -> onDiffSel.accept(name));
        return button;
    }

    Label createTitle() {
        Label title = new Label("Mole Madness");
        title.setFont(new Font("Arial", 64));
        title.setPadding(new Insets(20));
        return title;
    }

}
