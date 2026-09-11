package helloworld.helloworld.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Represents the full state of the ping pong game.
 * It stores the players, the ball, window dimensions,
 * scoring target, pause state, winner information and
 * temporary messages shown during gameplay.
 * @author Rama Houreh
 */
public class Game implements Resizable, Serializable {

    private static final long serialVersionUID = 1L;


    private Player player1;
    private Player player2;
    private Ball ball;
    private String gameName;

    private int target;
    private double dimensionX;
    private double dimensionY;

    private int speedIncreaseEvery;
    private int bounceCount;

    private String message = "";
    private long messageUntilMillis = 0;

    private long pauseUntilMillis = 0;
    private Double pendingDx = null;
    private Double pendingDy = null;

    private boolean pausedByUser = false;

    private boolean gameOver = false;
    private String winnerName = "";

    /**
     * Creates a new game with default players, rackets,
     * ball position, target score and window dimensions.
     */
    public Game() {
        this.dimensionX = 600;
        this.dimensionY = 400;
        this.gameName = "Super Ping Pong";

        Racket r1 = new Racket(20, 100, 20, 80, Color.YELLOW);
        Racket r2 = new Racket(dimensionX - 40, 100, 20, 80, Color.LIGHTBLUE);

        this.player1 = new Player("Player 1", r1);
        this.player2 = new Player("Player 2", r2);

        this.ball = new Ball(dimensionX / 2, dimensionY / 2, 3, 3, 15);

        this.target = 5;
        this.speedIncreaseEvery = 5;
        this.bounceCount = 0;
    }


    /**
     * Private constructor used by the Builder pattern.
     * It creates a default game first, then applies the
     * loaded values such as game name, player names, scores
     * and target score.
     *
     * @param builder the builder containing loaded values
     */
    private Game(Builder builder) {
        this();

        this.gameName = builder.gameName;
        this.player1.setName(builder.player1Name);
        this.player2.setName(builder.player2Name);
        this.player1.setScore(builder.player1Score);
        this.player2.setScore(builder.player2Score);
        this.target = builder.scoreLimit;

        this.ball.setPosX(this.dimensionX / 2);
        this.ball.setPosY(this.dimensionY / 2);
        this.ball.setDx(3);
        this.ball.setDy(3);
    }


    /**
     * Returns the name of the game.
     *
     * @return game name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the name of the game.
     *
     * @param gameName the new game name
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Returns player 1.
     *
     * @return player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Returns player 2.
     *
     * @return player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Returns the game ball.
     *
     * @return the ball object
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Returns the score required to win the game.
     *
     * @return target score
     */
    public int getTarget() {
        return target;
    }

    /**
     * Sets the score required to win the game.
     *
     * @param target the new target score
     */
    public void setTarget(int target) {
        this.target = target;
    }

    /**
     * Returns the current game width.
     *
     * @return horizontal game dimension
     */
    public double getDimensionX() {
        return dimensionX;
    }

    /**
     * Returns the current game height.
     *
     * @return vertical game dimension
     */
    public double getDimensionY() {
        return dimensionY;
    }

    /**
     * Sets the game width.
     *
     * @param dimensionX new horizontal dimension
     */
    public void setDimensionX(double dimensionX) {
        this.dimensionX = dimensionX;
    }

    /**
     * Sets the game height.
     *
     * @param dimensionY new vertical dimension
     */
    public void setDimensionY(double dimensionY) {
        this.dimensionY = dimensionY;
    }

    /**
     * Returns how often the ball speed should increase.
     *
     * @return number of bounces between speed increases
     */
    public int getSpeedIncreaseEvery() {
        return speedIncreaseEvery;
    }

    /**
     * Sets how often the ball speed should increase.
     *
     * @param speedIncreaseEvery bounce interval for speed increase
     */
    public void setSpeedIncreaseEvery(int speedIncreaseEvery) {
        this.speedIncreaseEvery = speedIncreaseEvery;
    }

    /**
     * Returns the current number of racket bounces.
     *
     * @return bounce count
     */
    public int getBounceCount() {
        return bounceCount;
    }

    /**
     * Sets the bounce counter value.
     *
     * @param bounceCount new bounce count
     */
    public void setBounceCount(int bounceCount) {
        this.bounceCount = bounceCount;
    }

    /**
     * Returns the current message shown on the canvas.
     *
     * @return the current message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Checks whether the current message should still be visible.
     *
     * @return true if the message is still visible, false otherwise
     */
    public boolean isMessageVisible() {
        return System.currentTimeMillis() < messageUntilMillis;
    }

    /**
     * Displays a temporary message for a given duration.
     * The game is paused while the message is visible.
     *
     * @param msg the message text
     * @param durationMs how long the message should remain visible in milliseconds
     */
    public void showMessage(String msg, long durationMs) {
        this.message = msg;
        this.messageUntilMillis = System.currentTimeMillis() + durationMs;
        this.pauseUntilMillis = this.messageUntilMillis;
    }

    /**
     * Stores the next movement values for the ball after a temporary pause ends.
     *
     * @param dxAfter horizontal ball speed after reset
     * @param dyAfter vertical ball speed after reset
     */
    public void scheduleBallReset(double dxAfter, double dyAfter) {
        this.pendingDx = dxAfter;
        this.pendingDy = dyAfter;
    }

    /**
     * Checks whether the game is paused by the user.
     *
     * @return true if paused by the user
     */
    public boolean isPausedByUser() {
        return pausedByUser;
    }

    /**
     * Sets whether the game is paused by the user.
     *
     * @param pausedByUser true to pause the game, false to resume it
     */
    public void setPausedByUser(boolean pausedByUser) {
        this.pausedByUser = pausedByUser;
    }

    /**
     * Checks whether the game is currently paused.
     * The game may be paused either by the user or temporarily
     * while displaying a message.
     *
     * @return true if the game is paused
     */
    public boolean isPaused() {
        return pausedByUser || System.currentTimeMillis() < pauseUntilMillis;
    }

    /**
     * Resets the ball position and movement after a temporary pause ends.
     * This is used after goals so the ball can restart from the centre.
     */
    public void processPendingResetIfNeeded() {
        if (!isPaused() && pendingDx != null && pendingDy != null && !gameOver) {
            ball.setPosX(dimensionX / 2);
            ball.setPosY(dimensionY / 2);
            ball.setDx(pendingDx);
            ball.setDy(pendingDy);
            pendingDx = null;
            pendingDy = null;
        }
    }

    /**
     * Checks whether the game has ended.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns the name of the winning player.
     *
     * @return winner name
     */
    public String getWinnerName() {
        return winnerName;
    }

    /**
     * Ends the game and stores the winner's name.
     * A winner message is shown on screen and the ball reset is cancelled.
     *
     * @param winner the name of the winning player
     */
    public void endGame(String winner) {
        this.gameOver = true;
        this.winnerName = winner;

        showMessage(winner + " WINS!", 60_000);

        pendingDx = null;
        pendingDy = null;
    }

    /**
     * Restarts the game and restores all values to their initial state.
     * This includes scores, player names, racket positions, ball state,
     * messages, pause state and winner information.
     */
    public void restartGame() {
        gameName = "Super Ping Pong";

        player1.setScore(0);
        player2.setScore(0);

        player1.setName("Player 1");
        player2.setName("Player 2");

        player1.getRacket().setPosX(20);
        player1.getRacket().setPosY(100);
        player1.getRacket().setThickness(20);
        player1.getRacket().setSize(80);
        player1.getRacket().setColor(Color.YELLOW);

        player2.getRacket().setPosX(dimensionX - 40);
        player2.getRacket().setPosY(100);
        player2.getRacket().setThickness(20);
        player2.getRacket().setSize(80);
        player2.getRacket().setColor(Color.LIGHTBLUE);

        ball.setPosX(dimensionX / 2);
        ball.setPosY(dimensionY / 2);
        ball.setDx(3);
        ball.setDy(3);
        ball.setRadius(15);

        bounceCount = 0;

        message = "";
        messageUntilMillis = 0;
        pauseUntilMillis = 0;
        pendingDx = null;
        pendingDy = null;

        pausedByUser = false;
        gameOver = false;
        winnerName = "";
    }

    /**
     * Resizes all horizontal game elements when the window width changes.
     *
     * @param factor horizontal resize factor
     */
    @Override
    public void resizeX(double factor) {
        player1.getRacket().resizeX(factor);
        player2.getRacket().resizeX(factor);
        ball.resizeX(factor);
    }

    /**
     * Resizes all vertical game elements when the window height changes.
     *
     * @param factor vertical resize factor
     */
    @Override
    public void resizeY(double factor) {
        player1.getRacket().resizeY(factor);
        player2.getRacket().resizeY(factor);
        ball.resizeY(factor);
    }

    /**
     * Returns a readable text representation of the game object.
     *
     * @return string containing key game information
     */
    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", player1Name='" + player1.getName() + '\'' +
                ", player2Name='" + player2.getName() + '\'' +
                ", player1Score=" + player1.getScore() +
                ", player2Score=" + player2.getScore() +
                ", scoreLimit=" + target +
                '}';
    }

    /**
     * Static inner Builder class used to reconstruct
     * a Game object step by step.
     *
     * This is mainly used when loading game data from
     * the database.
     */
    public static class Builder {

        private String gameName = "Super Ping Pong";
        private String player1Name = "Player 1";
        private String player2Name = "Player 2";
        private int player1Score = 0;
        private int player2Score = 0;
        private int scoreLimit = 5;

        /**
         * Sets the game name in the builder.
         *
         * @param gameName the game name
         * @return the builder itself
         */
        public Builder withGameName(String gameName) {
            this.gameName = gameName;
            return this;
        }

        /**
         * Sets player 1 name in the builder.
         *
         * @param player1Name player 1 name
         * @return the builder itself
         */
        public Builder withPlayer1Name(String player1Name) {
            this.player1Name = player1Name;
            return this;
        }

        /**
         * Sets player 2 name in the builder.
         *
         * @param player2Name player 2 name
         * @return the builder itself
         */
        public Builder withPlayer2Name(String player2Name) {
            this.player2Name = player2Name;
            return this;
        }

        /**
         * Sets player 1 score in the builder.
         *
         * @param player1Score player 1 score
         * @return the builder itself
         */
        public Builder withPlayer1Score(int player1Score) {
            this.player1Score = player1Score;
            return this;
        }

        /**
         * Sets player 2 score in the builder.
         *
         * @param player2Score player 2 score
         * @return the builder itself
         */
        public Builder withPlayer2Score(int player2Score) {
            this.player2Score = player2Score;
            return this;
        }

        /**
         * Sets the score limit in the builder.
         *
         * @param scoreLimit winning score limit
         * @return the builder itself
         */
        public Builder withScoreLimit(int scoreLimit) {
            this.scoreLimit = scoreLimit;
            return this;
        }

        /**
         * Builds and returns a new Game instance.
         *
         * @return newly constructed Game object
         */
        public Game build() {
            return new Game(this);
        }
    }
}