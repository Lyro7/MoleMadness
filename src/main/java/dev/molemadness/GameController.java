package dev.molemadness;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final GameRenderer gameRenderer;
    private final GameWorld gameWorld;
    private final GameLoop gameLoop;

    private final Runnable onUi;
    private final Runnable onGameOver;

    public GameController(Runnable onUi, Runnable onGameOver, Canvas canvas, String difficulty) {
        gameRenderer = new GameRenderer(canvas);
        gameWorld = new GameWorld(difficulty);
        gameLoop = new GameLoop(this::pulse, gameRenderer, gameWorld);

        this.onUi = onUi;
        this.onGameOver = onGameOver;

        // Update mole positions to virtual space
        nativeToVirtual();
        initMouseListener(canvas);
    }

    private void initMouseListener(Canvas canvas) {
        canvas.setOnMouseClicked(e  -> {
            double x = e.getX();
            double y = e.getY();
            gameWorld.manageHit(x, y);
        });
    }

    public void pulse() {
        onUi.run();

        if (gameWorld.getLives() <= 0) {
            gameLoop.stop();
            onGameOver.run();
        }
    }

    public void start() {
        gameLoop.start();
    }

    private void nativeToVirtual() {
        List<Point2D> nativeHoles = gameWorld.getHoles();
        List<Point2D> virtualHoles = new ArrayList<>();

        for (Point2D nativeHole : nativeHoles) {
            Point2D virtualHole = gameRenderer.nativeToVirtual(nativeHole);
            virtualHoles.add(virtualHole);
        }

        gameWorld.setHoles(virtualHoles);
    }

    public int getScore() {
        return gameWorld.getScore();
    }

    public int getLives() {
        return gameWorld.getLives();
    }


}
