package dev.molemadness;

import javafx.geometry.Point2D;

import java.util.*;

import static dev.molemadness.Mole.MoleState;

public class GameWorld {

    private List<Point2D> holes  = new ArrayList<>();

    private final List<Mole> allMoles = new ArrayList<>();
    private final List<Mole> visibleMoles= new ArrayList<>();

    private final Random random = new Random();

    private int maxVisibleMoles;

    private double spawnMin;
    private double spawnMax;

    private double spawnCooldown = 0.0;
    private double nextSpawn = 1.0;

    private String difficulty;

    private double mVisible;
    private double mInvisible;

    private int lives = 4;
    private int score;

    public GameWorld(String difficulty) {
        this.difficulty = difficulty;

        initializeMap();
        initializeDifficulty();
    }

    public void updateWorld(double delta) {
        // Backwards iterating, so no concurrent modification can occur
        for (int i = allMoles.size() - 1; i >= 0; i--) {
            Mole mole = allMoles.get(i);
            mole.update(delta);
            manageMole(mole);
        }

        spawnCooldown += delta;
        if (visibleMoles.size() < maxVisibleMoles && spawnCooldown >= nextSpawn) {
            Point2D free = chooseFreeHole();
            if (free != null) {
                spawnMole(free);
                spawnCooldown = 0.0;
                rollNextSpawnTimer();
            }
        }
    }

    private void rollNextSpawnTimer() {
        nextSpawn = spawnMin + random.nextDouble() * (spawnMax - spawnMin);
    }

    private void initializeDifficulty() {
        // If no difficulty is selected, choose easy one
        if (difficulty == null) {
            difficulty = "Easy";
        }

        switch (difficulty) {
            case "Easy":
                this.mVisible = 2;
                this.mInvisible = 3;
                this.maxVisibleMoles = 2;
                this.spawnMin = 0.45;
                this.spawnMax = 1;
                break;
            case "Middle":
                this.mVisible = 1.4;
                this.mInvisible = 1.8;
                this.maxVisibleMoles = 3;
                this.spawnMin = 0.20;
                this.spawnMax = 0.65;
                break;
            case "Hard":
                this.mVisible = 1;
                this.mInvisible = 1.5;
                this.maxVisibleMoles = 5;
                this.spawnMin = 0.15;
                this.spawnMax = 0.40;
                break;
        }
    }

    private void spawnMole(Point2D position) {
        Mole mole = new Mole(position, mVisible, mInvisible);
        allMoles.add(mole);
    }

    /* Native px coordinates of the holes */
    private void initializeMap() {
        holes.add(new Point2D(460, 265));
        holes.add(new Point2D(1075, 265));
        holes.add(new Point2D(750, 465));
        holes.add(new Point2D(225, 560));
        holes.add(new Point2D(1310, 560));
        holes.add(new Point2D(515, 765));
        holes.add(new Point2D(990, 755));
    }

    private Point2D chooseFreeHole() {
        Set<Point2D> occupied = new HashSet<>();
        for (Mole mole : visibleMoles) {
            occupied.add(mole.getPosition());
        }
        List<Point2D> candidates = new ArrayList<>();
        for (Point2D hole : holes) {
            if (!occupied.contains(hole)) {
                candidates.add(hole);
            }
        }
        if (candidates.isEmpty()) {
            return null;
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

    private void manageMole(Mole mole) {
        if (!mole.hasSwitched()) {
            return;
        }

        MoleState moleState = mole.getMoleState();
        switch (moleState) {
            case VISIBLE:
                if (!visibleMoles.contains(mole)) {
                    visibleMoles.add(mole);
                }
                break;
            case INVISIBLE:
                visibleMoles.remove(mole);
                allMoles.remove(mole);
                lives--;
                break;
        }
    }

    public void manageHit(double x, double y) {
        for (int j = visibleMoles.size() - 1; j >= 0; j--) {
            Mole mole = visibleMoles.get(j);
            if (mole.contains(x, y)) {
                visibleMoles.remove(mole);
                allMoles.remove(mole);
                score++;
            }
        }
    }

    public List<Mole> getVisibleMoles() {
        return visibleMoles;
    }

    public void setHoles(List<Point2D> holes) {
        this.holes = holes;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public List<Point2D> getHoles() {
        return holes;
    }

}
