package helloworld.helloworld.view;

import helloworld.helloworld.model.Ball;
import helloworld.helloworld.model.Game;
import helloworld.helloworld.model.Player;
import helloworld.helloworld.model.Racket;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Custom JavaFX canvas used to draw the game interface.
 * It is responsible for rendering the background, ball,
 * rackets, player scores and on-screen messages.
 * @author Rama Houreh
 */
public class LabCanvas extends Canvas {

    /**
     * Creates a canvas with the given width and height.
     *
     * @param width the initial width of the canvas
     * @param height the initial height of the canvas
     */
    public LabCanvas(double width, double height) {
        super(width, height);
    }

    /**
     * Draws the full game state on the canvas.
     * This includes the background, ball, rackets,
     * player score display and temporary game messages.
     *
     * @param game the current game state to render
     */
    public void drawGame(Game game) {
        GraphicsContext gc = this.getGraphicsContext2D();

        drawBackground(gc);
        drawBall(gc, game.getBall());
        drawRacket(gc, game.getPlayer1().getRacket());
        drawRacket(gc, game.getPlayer2().getRacket());
        drawTitle(gc, game.getPlayer1(), true);
        drawTitle(gc, game.getPlayer2(), false);

        if (game.isMessageVisible()) {
            drawMessage(gc, game.getMessage());
        }
    }

    /**
     * Draws the ball on the canvas.
     *
     * @param gc the graphics context used for drawing
     * @param ball the ball to draw
     */
    private void drawBall(GraphicsContext gc, Ball ball) {
        gc.setFill(Color.YELLOWGREEN);
        gc.fillOval(ball.getPosX(), ball.getPosY(), ball.getRadius(), ball.getRadius());
    }

    /**
     * Draws a player's racket on the canvas.
     *
     * @param gc the graphics context used for drawing
     * @param racket the racket to draw
     */
    private void drawRacket(GraphicsContext gc, Racket racket) {
        gc.setFill(racket.getColor());
        gc.fillRect(racket.getPosX(), racket.getPosY(), racket.getThickness(), racket.getSize());
    }

    /**
     * Draws the game background.
     *
     * @param gc the graphics context used for drawing
     */
    private void drawBackground(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Draws the player score and name at the top of the canvas.
     *
     * @param gc the graphics context used for drawing
     * @param player the player whose details are displayed
     * @param leftSide true if the text should be drawn on the left side,
     *                 false if it should be drawn on the right side
     */
    private void drawTitle(GraphicsContext gc, Player player, boolean leftSide) {
        gc.setFill(Color.WHITE);
        double fontSize = Math.max(14, getHeight() * 0.06);
        gc.setFont(Font.font(fontSize));

        String text = player.getScore() + "  " + truncate(player.getName(), 12);

        double x = leftSide ? getWidth() * 0.08 : getWidth() * 0.65;
        double y = getHeight() * 0.10;

        gc.fillText(text, x, y);
    }

    /**
     * Draws a temporary message in the centre area of the canvas.
     * This is used for messages such as goals and winner announcements.
     *
     * @param gc the graphics context used for drawing
     * @param msg the message text to display
     */
    private void drawMessage(GraphicsContext gc, String msg) {
        gc.setFill(Color.WHITE);
        double fontSize = Math.max(18, getHeight() * 0.10);
        gc.setFont(Font.font(fontSize));

        gc.fillText(msg, getWidth() * 0.30, getHeight() * 0.55);
    }

    /**
     * Shortens a string if it is longer than the allowed maximum length.
     * An ellipsis is added when truncation happens.
     *
     * @param text the original text
     * @param maxLength the maximum permitted length
     * @return the original or shortened text
     */
    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}