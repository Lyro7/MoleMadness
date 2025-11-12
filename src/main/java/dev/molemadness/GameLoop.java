package dev.molemadness;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    private final GameWorld gameWorld;
    private final GameRenderer gameRenderer;

    private final Runnable onGameOver;
    private long lastTime = 0;

    public GameLoop(GameWorld gameWorld, GameRenderer gameRenderer, Runnable onGameOver) {
        this.gameWorld = gameWorld;
        this.gameRenderer = gameRenderer;
        this.onGameOver = onGameOver;
    }

    @Override
    public void handle(long currentTime) {
        if (lastTime == 0) {
            lastTime = currentTime;
            return;
        }

        double delta = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        update(delta);
        render(delta);
    }

    private void update(double delta) {
        gameWorld.updateWorld(delta);
    }

    private void render(double delta) {
        gameRenderer.render(gameWorld.getAllMoles());
    }

}
