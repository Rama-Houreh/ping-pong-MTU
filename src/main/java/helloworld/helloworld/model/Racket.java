package helloworld.helloworld.model;

import javafx.scene.paint.Color;

/**
 * Represents a player's racket in the ping pong game.
 * A racket has a position, size, thickness and colour,
 * and can move vertically within the boundaries of the game area.
 * @author Rama Houreh
 */
public class Racket extends PositionedObject implements Resizable {

    private static final long serialVersionUID = 1L;

    private double thickness;
    private double size;
    private transient Color color;

    /**
     * Creates a new racket with the given position, size and colour.
     *
     * @param x horizontal position of the racket
     * @param y vertical position of the racket
     * @param thickness width of the racket
     * @param size height of the racket
     * @param color colour used when drawing the racket
     */
    public Racket(double x, double y, double thickness, double size, Color color) {
        super(x, y);
        this.thickness = thickness;
        this.size = size;
        this.color = color;
    }

    /**
     * Returns the width of the racket.
     *
     * @return racket thickness
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * Returns the height of the racket.
     *
     * @return racket size
     */
    public double getSize() {
        return size;
    }

    /**
     * Returns the colour of the racket.
     *
     * @return racket colour
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the racket thickness.
     *
     * @param thickness new racket width
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    /**
     * Sets the racket height.
     *
     * @param size new racket height
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Sets the colour of the racket.
     *
     * @param color new racket colour
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Ensures the racket stays inside the vertical boundaries
     * of the game area.
     *
     * @param gameHeight the height of the game window
     */
    private void clampY(double gameHeight) {
        if (posY < 0) posY = 0;
        if (posY + size > gameHeight) posY = gameHeight - size;
        if (posY < 0) posY = 0;
    }

    /**
     * Moves the racket upward while keeping it inside the game area.
     *
     * @param step movement distance
     * @param gameHeight height of the game window
     */
    public void moveUp(double step, double gameHeight) {
        this.posY = this.posY - step;
        clampY(gameHeight);
    }

    /**
     * Moves the racket downward while keeping it inside the game area.
     *
     * @param step movement distance
     * @param gameHeight height of the game window
     */
    public void moveDown(double step, double gameHeight) {
        this.posY = this.posY + step;
        clampY(gameHeight);
    }

    /**
     * Resizes horizontal properties when the window width changes.
     *
     * @param factor scaling factor
     */
    @Override
    public void resizeX(double factor) {
        this.posX = this.posX * factor;
        this.thickness = this.thickness * factor;
    }

    /**
     * Resizes vertical properties when the window height changes.
     *
     * @param factor scaling factor
     */
    @Override
    public void resizeY(double factor) {
        this.posY = this.posY * factor;
        this.size = this.size * factor;
    }
}