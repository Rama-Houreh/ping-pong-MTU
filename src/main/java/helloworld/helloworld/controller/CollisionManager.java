package helloworld.helloworld.controller;

import helloworld.helloworld.model.Ball;
import helloworld.helloworld.model.Racket;

/**
 * Handles collision detection between the ball and the rackets.
 * If the ball collides with a racket, the direction of the ball
 * is reversed to simulate a bounce.
 * @author Rama Houreh
 */
public class CollisionManager {

    /**
     * Checks whether the ball collides with either racket.
     * If a collision occurs, the ball direction is reversed
     * and the ball position is adjusted to prevent overlap.
     * @param ball the game ball
     * @param left the left player's racket
     * @param right the right player's racket
     * @return true if a collision occurred, false otherwise
     */
    public boolean handleRacketBounce(Ball ball, Racket left, Racket right) {

        double bx1 = ball.getPosX();
        double by1 = ball.getPosY();
        double bx2 = bx1 + ball.getRadius();
        double by2 = by1 + ball.getRadius();

        double lx1 = left.getPosX();
        double ly1 = left.getPosY();
        double lx2 = lx1 + left.getThickness();
        double ly2 = ly1 + left.getSize();

        double rx1 = right.getPosX();
        double ry1 = right.getPosY();
        double rx2 = rx1 + right.getThickness();
        double ry2 = ry1 + right.getSize();

        if (ball.getDx() < 0 && intersects(bx1, by1, bx2, by2, lx1, ly1, lx2, ly2)) {
            ball.setPosX(lx2);
            ball.setDx(-ball.getDx());
            return true;
        }

        if (ball.getDx() > 0 && intersects(bx1, by1, bx2, by2, rx1, ry1, rx2, ry2)) {
            ball.setPosX(rx1 - ball.getRadius());
            ball.setDx(-ball.getDx());
            return true;
        }

        return false;
    }

    /**
     * Checks whether two rectangular areas intersect.
     * @param ax1 first rectangle left
     * @param ay1 first rectangle top
     * @param ax2 first rectangle right
     * @param ay2 first rectangle bottom
     * @param bx1 second rectangle left
     * @param by1 second rectangle top
     * @param bx2 second rectangle right
     * @param by2 second rectangle bottom
     * @return true if the rectangles intersect
     */
    private boolean intersects(double ax1, double ay1, double ax2, double ay2,
                               double bx1, double by1, double bx2, double by2) {
        return ax1 < bx2 && ax2 > bx1 && ay1 < by2 && ay2 > by1;
    }
}