package helloworld.helloworld.model;

import java.io.Serializable;

/**
 * Abstract base class for all game objects that have a position
 * in the game area. It stores the horizontal (X) and vertical (Y)
 * coordinates of an object.
 *
 * Classes such as Ball and Racket extend this class so they
 * automatically inherit position handling functionality.
 * @author Rama Houreh
 */
public abstract class PositionedObject implements Serializable {

    private static final long serialVersionUID = 1L;

    protected double posX;
    protected double posY;

    /**
     * Creates a positioned object with the specified coordinates.
     *
     * @param posX horizontal position
     * @param posY vertical position
     */
    public PositionedObject(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Returns the horizontal position of the object.
     *
     * @return X coordinate
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Returns the vertical position of the object.
     *
     * @return Y coordinate
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Sets the horizontal position of the object.
     *
     * @param posX new X coordinate
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Sets the vertical position of the object.
     *
     * @param posY new Y coordinate
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }
}