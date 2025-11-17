# Mole Madness 🎯🦫

Mole Madness is a simple Whack-a-Mole style game built with Java and JavaFX.  
You hit moles by clicking on them before they disappear. The game offers three difficulty levels and a small UI with score and lives.

## Features

- Three difficulty levels: **Easy**, **Middle**, **Hard**
- Time-based spawning and despawning of moles
- Lives & score display, plus a simple *Game Over* overlay
- Fixed virtual resolution **1280x720** with automatic scaling to the window size
- Click detection using the standard equation of a circle for the mole hitbox
- Clean separation of responsibilities:

  - `GameWorld` – game logic (spawning, timing, difficulty, lives, score)
  - `GameRenderer` – drawing background and moles at fixed positions
  - `GameController` – connects world, rendering and input
  - `GameLoop` – uses the AnimationTimer class from JavaFX

## Controls

- **Mouse click** – hit a mole
- **ESC** – restart game after *Game Over*
- **TAB** – return to main menu after *Game Over*

## Difficulty

The difficulty parameter constants (time to hit the mole before it becomes invisible, spawn rate, etc.) are defined in the `GameWorld` class.  
They are not yet perfectly tuned, so the game can be a bit too hard or too easy in some situations.

- **Easy** – longer visible time, slower spawn
- **Middle** – shorter visible time, medium spawn rate
- **Hard** – shorter visible time, faster spawn

## Technology

- Java (Temurin 23)
- JavaFX (Render Engine)
- Gradle

## Run (prebuilt Windows version)

1. Go to the [Releases](https://github.com/Lyro7/MoleMadness/releases)
2. Download `MoleMadness-windows.zip`.
3. Extract the ZIP and run `MoleMadness.bat` from the `bin` folder.

## Run from source

Requirements:
- JDK 23 (e.g. Eclipse Temurin 23)

Steps:
1. Clone this repository.
2. In the project root, run: `./gradlew run`.
