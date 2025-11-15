package dev.molemadness;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;

public class GameRenderer {

    private final GraphicsContext gc;

    private final Image background;

    private static final double HOLE_RADIUS = 60;
    private static final double MOLE_SCALE = 2.5;

    public GameRenderer(Canvas canvas) {
        this.gc = canvas.getGraphicsContext2D();
        this.background = new Image(Objects.requireNonNull(
                getClass().getResource("/assets/background.png")).toExternalForm());
    }

    public void render(List<Mole> moles) {
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();

        gc.clearRect(0, 0, width, height);
        gc.drawImage(background, 0, 0, width, height);

        for (Mole mole : moles) {
            Image sprite = mole.getSprite();

            double aspect = sprite.getHeight() / sprite.getWidth();

            double targetW = (HOLE_RADIUS * 2) * MOLE_SCALE;
            double targetH = targetW * aspect;

            double x = mole.getPosition().getX();
            double y = mole.getPosition().getY();

            double drawX = x - targetW / 2.0;
            double drawY = y - targetH / 2.0;

            gc.drawImage(sprite, drawX, drawY, targetW, targetH);
        }
    }

    public Point2D nativeToVirtual(Point2D pNative) {
        double nativeW = background.getWidth();
        double nativeH = background.getHeight();

        double virtualW = gc.getCanvas().getWidth();
        double virtualH = gc.getCanvas().getHeight();

        return new Point2D(pNative.getX() * virtualW / nativeW, pNative.getY() * virtualH / nativeH);
    }

}
