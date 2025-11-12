package dev.molemadness;

public class GameController {

    private final GameWorld gameWorld;
    private GameLoop gameLoop;

    private enum GameState { RUNNING, PAUSED, GAME_OVER }
    private GameState gameState;

    private Runnable onGameOver;

    public GameController(String difficulty) {
        gameWorld = new GameWorld(difficulty);;
    }

    public void start(GameRenderer gameRenderer) {
        if (gameLoop != null) {
            return;
        }
        gameState = GameState.RUNNING;
        gameLoop = new GameLoop(gameWorld, gameRenderer, this::handleGameOver);
        gameLoop.start();
    }

    private void stop() {
        gameState = GameState.GAME_OVER;
        gameLoop.stop();
    }

    private void pause() {
        if (gameState == GameState.RUNNING) {
            gameState = GameState.PAUSED;
            gameLoop.stop();
        }
    }

    private void resume() {
        if (gameState == GameState.PAUSED) {
            gameState = GameState.RUNNING;
            gameLoop.start();
        }
    }

    private void handleGameOver() {
        stop();
        if (onGameOver != null) {
            onGameOver.run();
        }
    }

    public void setOnGameOver(Runnable onGameOver) {
        this.onGameOver = onGameOver;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

}
