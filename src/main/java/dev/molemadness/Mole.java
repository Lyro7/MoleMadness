package dev.molemadness;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class Mole {

    private Point2D position;
    private float radius;

    private final double maxVisibleTime;
    private final double maxInvisibleTime;
    private final double maxHitTime;

    private double tVisible;
    private double tInvisible;
    private double tHit;

    private boolean switched;

    public enum MoleState { VISIBLE, INVISIBLE, HIT }
    private MoleState moleState;

    private static final Image IMAGE = new Image(Objects.requireNonNull(
            Mole.class.getResource("/assets/mole.png")).toExternalForm());

    private final Image sprite;

    public Mole(Point2D pos, double maxVisibleTime, double maxInvisibleTime) {
        this.position = pos;
        this.maxVisibleTime = maxVisibleTime;
        this.maxInvisibleTime = maxInvisibleTime;
        this.maxHitTime = 1;
        this.switched = false;
        this.moleState = MoleState.INVISIBLE;
        // Load every mole with same sprite
        this.sprite = IMAGE;
    }

    public void update(double delta) {
        switch (moleState) {
            case VISIBLE:
                tVisible += delta;
                if (tVisible >= maxVisibleTime) {
                    hide();
                    switched = true;
                }
                break;
            case INVISIBLE:
                tInvisible += delta;
                if (tInvisible >= maxInvisibleTime) {
                    show();
                    switched = true;
                }
            case HIT:
                tHit += delta;
                if (tHit >= maxHitTime) {
                    hit();
                    switched = true;
                }
        }
    }

    private void show() {
        moleState = MoleState.VISIBLE;
    }

    private void hide() {
        moleState = MoleState.INVISIBLE;
    }

    private void hit() {
        moleState = MoleState.HIT;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
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

}
