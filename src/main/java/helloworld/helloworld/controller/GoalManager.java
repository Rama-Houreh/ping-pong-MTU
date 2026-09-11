package helloworld.helloworld.controller;

import helloworld.helloworld.model.Ball;
import helloworld.helloworld.model.Game;

/**
 * Detects when a goal is scored and updates the game state.
 * This includes increasing the correct player's score,
 * checking for a winner and preparing the ball reset after a goal.
 * @author Rama Houreh
 */
public class GoalManager {

    private static final long GOAL_MESSAGE_MS = 2000;

    /**
     * Checks whether the ball has passed the left or right boundary.
     * If so, the appropriate player is awarded a point.
     * If a player reaches the target score, the game ends and
     * the winner is declared.
     * @param game the current game state
     * @return true if a goal was detected, false otherwise
     */
    public boolean handleGoalIfNeeded(Game game) {
        if (game.isGameOver()) {
            return false;
        }

        Ball ball = game.getBall();
        double width = game.getDimensionX();

        if (ball.getPosX() + ball.getRadius() < 0) {
            int newScore = game.getPlayer2().getScore() + 1;
            game.getPlayer2().setScore(newScore);

            if (newScore >= game.getTarget()) {
                game.endGame(game.getPlayer2().getName());
                return true;
            }

            game.showMessage("GOAL " + game.getPlayer2().getName(), GOAL_MESSAGE_MS);
            game.scheduleBallReset(-Math.abs(ball.getDx()), ball.getDy());
            return true;
        }

        if (ball.getPosX() > width) {
            int newScore = game.getPlayer1().getScore() + 1;
            game.getPlayer1().setScore(newScore);

            if (newScore >= game.getTarget()) {
                game.endGame(game.getPlayer1().getName());
                return true;
            }

            game.showMessage("GOAL " + game.getPlayer1().getName(), GOAL_MESSAGE_MS);
            game.scheduleBallReset(Math.abs(ball.getDx()), ball.getDy());
            return true;
        }

        return false;
    }
}