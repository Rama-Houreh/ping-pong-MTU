package helloworld.helloworld.controller;

import helloworld.helloworld.model.Game;
import helloworld.helloworld.view.LabCanvas;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

/**
 * Controls the movement of both player rackets.
 * This class runs on a separate thread so that racket
 * movement can occur continuously while the game is running.
 * It listens for keyboard input and updates the racket
 * positions accordingly.
 * @author Rama Houreh
 */
public class RacketManager implements Runnable {

    private final Controller controller;
    private final LabCanvas canvas;
    private final KeyboardListener keyboard;

    private final double step = 8;
    private final int delayMs = 10;

    /**
     * Creates a new RacketManager responsible for moving
     * the rackets based on keyboard input.
     * @param controller the main controller
     * @param canvas the canvas used to redraw the game
     * @param keyboard the keyboard listener used to detect key presses
     */
    public RacketManager(Controller controller, LabCanvas canvas, KeyboardListener keyboard) {
        this.controller = controller;
        this.canvas = canvas;
        this.keyboard = keyboard;
    }

    /**
     * Main loop controlling racket movement.
     * This loop runs continuously in its own thread,
     * checking keyboard input and updating racket positions.
     */
    public void run() {
        while (true) {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                return;
            }

            Game game = controller.getGame();

            if (keyboard.consumeEscapePress()) {
                game.setPausedByUser(!game.isPausedByUser());
                Platform.runLater(() -> canvas.drawGame(controller.getGame()));
                continue;
            }

            if (game.isGameOver()) {
                continue;
            }

            if (game.isPaused()) {
                continue;
            }

            boolean moved = false;

            if (keyboard.isPressed(KeyCode.W)) {
                game.getPlayer1().getRacket().moveUp(step, game.getDimensionY());
                moved = true;
            }

            if (keyboard.isPressed(KeyCode.S)) {
                game.getPlayer1().getRacket().moveDown(step, game.getDimensionY());
                moved = true;
            }

            if (keyboard.isPressed(KeyCode.UP)) {
                game.getPlayer2().getRacket().moveUp(step, game.getDimensionY());
                moved = true;
            }

            if (keyboard.isPressed(KeyCode.DOWN)) {
                game.getPlayer2().getRacket().moveDown(step, game.getDimensionY());
                moved = true;
            }

            if (moved) {
                Platform.runLater(() -> canvas.drawGame(controller.getGame()));
            }
        }
    }
}