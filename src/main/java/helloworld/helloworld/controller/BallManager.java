package helloworld.helloworld.controller;

import helloworld.helloworld.model.Ball;
import helloworld.helloworld.model.Game;
import helloworld.helloworld.view.LabCanvas;
import javafx.application.Platform;

/**
 * Controls ball movement during the game.
 * This class runs on a separate thread so the ball
 * can move independently of racket movement.
 * It is responsible for moving the ball, handling
 * wall collisions, detecting racket bounces,
 * increasing speed after a number of bounces
 * and checking for goals.
 * @author Rama Houreh
 */
public class BallManager implements Runnable {

    private final Controller controller;
    private final LabCanvas canvas;

    private final CollisionManager collisionManager = new CollisionManager();
    private final GoalManager goalManager = new GoalManager();

    /**
     * Creates a BallManager with access to the controller
     * and the canvas used for redrawing the game.
     * @param controller the main controller
     * @param canvas the canvas used to render the game
     */
    public BallManager(Controller controller, LabCanvas canvas) {
        this.controller = controller;
        this.canvas = canvas;
    }

    /**
     * Main execution loop for ball movement.
     * This loop runs continuously in its own thread,
     * updates the ball position, handles collisions,
     * increases speed after a chosen number of bounces
     * and redraws the game.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                return;
            }

            Game game = controller.getGame();

            if (game.isGameOver()) {
                Platform.runLater(() -> canvas.drawGame(controller.getGame()));
                continue;
            }

            game.processPendingResetIfNeeded();

            if (!game.isPaused()) {
                moveBallAndBounceWalls(game);

                boolean bounced = collisionManager.handleRacketBounce(
                        game.getBall(),
                        game.getPlayer1().getRacket(),
                        game.getPlayer2().getRacket()
                );

                if (bounced) {
                    game.setBounceCount(game.getBounceCount() + 1);

                    if (game.getSpeedIncreaseEvery() > 0
                            && game.getBounceCount() % game.getSpeedIncreaseEvery() == 0) {
                        game.getBall().increaseSpeed(1.15);
                    }
                }

                goalManager.handleGoalIfNeeded(game);
            }

            Platform.runLater(() -> canvas.drawGame(controller.getGame()));
        }
    }

    /**
     * Moves the ball and handles bouncing from the
     * top and bottom walls of the game area.
     * @param game the current game
     */
    private void moveBallAndBounceWalls(Game game) {
        Ball ball = game.getBall();
        ball.move();

        if (ball.getPosY() <= 0) {
            ball.setPosY(0);
            ball.setDy(Math.abs(ball.getDy()));
        }

        double bottom = game.getDimensionY() - ball.getRadius();
        if (ball.getPosY() >= bottom) {
            ball.setPosY(bottom);
            ball.setDy(-Math.abs(ball.getDy()));
        }
    }

    /**
     * Checks whether player 2 has scored.
     * This method is mainly used in unit testing.
     * @param game the current game instance
     * @return true if the ball is near the left side of the game area
     */
    public boolean player2Scores(Game game) {
        return game.getBall().getPosX() < 10;
    }

    /**
     * Checks whether the game has ended by comparing
     * the highest player score with the target score.
     * This method is mainly used in unit testing.
     * @param game the current game instance
     * @return true if a player has reached or exceeded the target score
     */
    public boolean checkEndOfGame(Game game) {
        int maxScore = Math.max(
                game.getPlayer1().getScore(),
                game.getPlayer2().getScore()
        );

        return game.getTarget() <= maxScore;
    }
}