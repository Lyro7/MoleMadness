package dev.molemadness;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class Mole {

    private final Point2D position;
    private final float radius;

    private final double maxVisibleTime;
    private final double maxInvisibleTime;

    private double tVisible;
    private double tInvisible;

    private boolean switched;

    public enum MoleState { VISIBLE, INVISIBLE }
    private MoleState moleState;

    private static final Image IMAGE = new Image(Objects.requireNonNull(
            Mole.class.getResource("/assets/mole.png")).toExternalForm());

    private final Image sprite;

    public Mole(Point2D pos, double maxVisibleTime, double maxInvisibleTime) {
        this.position = pos;
        this.radius = 70;
        this.maxVisibleTime = maxVisibleTime;
        this.maxInvisibleTime = maxInvisibleTime;
        this.switched = false;
        this.tInvisible = Math.random() * maxInvisibleTime;
        this.moleState = MoleState.INVISIBLE;
        this.sprite = IMAGE;
    }

    public void update(double delta) {
        switched = false;
        switch (moleState) {
            case VISIBLE:
                tVisible += delta;
                if (tVisible >= maxVisibleTime) {
                    hide();
                    tVisible = 0;
                    tInvisible = 0;
                    switched = true;
                }
                break;
            case INVISIBLE:
                tInvisible += delta;
                if (tInvisible >= maxInvisibleTime) {
                    show();
                    tVisible = 0;
                    tInvisible = 0;
                    switched = true;
                }
                break;
        }
    }

    private void show() {
        moleState = MoleState.VISIBLE;
    }

    private void hide() {
        moleState = MoleState.INVISIBLE;
    }

    public Point2D getPosition() {
        return position;
    }

    public MoleState getMoleState() {
        return moleState;
    }

    public Image getSprite() {
        return sprite;
    }

    public boolean hasSwitched() {
        return switched;
    }

    /* Bounding box of mole is a circle */
    public boolean contains(double x, double y) {
        double off = 25;
        // Point-in-circle test
        return Math.pow((x - position.getX()), 2) + Math.pow(y - (position.getY() + off), 2) < Math.pow(radius, 2);
    }

}
