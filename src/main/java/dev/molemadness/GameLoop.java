package dev.molemadness;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    private final GameWorld gameWorld;
    private final GameRenderer gameRenderer;

    private final Runnable onPulse;

    private long lastTime = 0;

    public GameLoop(Runnable onPulse, GameRenderer gameRenderer, GameWorld gameWorld) {
        this.onPulse = onPulse;

        this.gameRenderer = gameRenderer;
        this.gameWorld = gameWorld;
    }

    @Override
    public void handle(long currentTime) {
        if (lastTime == 0) {
            lastTime = currentTime;
            return;
        }

        double delta = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        // Update Labels, check for game over
        onPulse.run();

        update(delta);
        render(delta);
    }

    private void update(double delta) {
        gameWorld.updateWorld(delta);
    }

    private void render(double delta) {
        gameRenderer.render(gameWorld.getVisibleMoles());
    }

}
