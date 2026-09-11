package helloworld.helloworld.model;

/**
 * Represents the ball in the ping pong game.
 * The ball stores its position, movement direction and size,
 * and supports movement, resizing and speed adjustment.
 * @author Rama Houreh
 */
public class Ball extends PositionedObject implements Resizable {

    private static final long serialVersionUID = 1L;

    private double dx;
    private double dy;
    private double radius;

    private final double MIN_SPEED = 1.0;
    private final double MAX_SPEED = 10.0;

    /**
     * Creates a ball with the given starting position,
     * movement values and radius.
     *
     * @param startX initial horizontal position
     * @param startY initial vertical position
     * @param dx horizontal movement step
     * @param dy vertical movement step
     * @param radius radius of the ball
     */
    public Ball(double startX, double startY, double dx, double dy, double radius) {
        super(startX, startY);
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
    }

    /**
     * Returns the horizontal movement value of the ball.
     *
     * @return horizontal speed
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the vertical movement value of the ball.
     *
     * @return vertical speed
     */
    public double getDy() {
        return dy;
    }

    /**
     * Returns the radius of the ball.
     *
     * @return ball radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the horizontal movement value of the ball.
     *
     * @param dx new horizontal speed
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical movement value of the ball.
     *
     * @param dy new vertical speed
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Sets the radius of the ball.
     *
     * @param radius new ball radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Moves the ball by adding its current speed values
     * to its current position.
     */
    public void move() {
        posX += dx;
        posY += dy;
    }

    /**
     * Increases the speed of the ball while keeping
     * its current movement direction.
     * The speed is increased proportionally and is capped
     * at the maximum allowed value.
     *
     * @param factor multiplication factor for the speed increase
     */
    public void increaseSpeed(double factor) {
        double signX = Math.signum(dx);
        double signY = Math.signum(dy);

        double newDx = Math.abs(dx) * factor;
        double newDy = Math.abs(dy) * factor;

        if (newDx > MAX_SPEED) {
            newDx = MAX_SPEED;
        }
        if (newDy > MAX_SPEED) {
            newDy = MAX_SPEED;
        }

        dx = newDx * signX;
        dy = newDy * signY;
    }

    /**
     * Resizes the horizontal properties of the ball
     * when the game width changes.
     *
     * @param factor horizontal resize factor
     */
    @Override
    public void resizeX(double factor) {
        this.posX = this.posX * factor;
        this.radius = this.radius * factor;
        this.dx = clamp(this.dx * factor, MIN_SPEED, MAX_SPEED);
    }

    /**
     * Resizes the vertical properties of the ball
     * when the game height changes.
     *
     * @param factor vertical resize factor
     */
    @Override
    public void resizeY(double factor) {
        this.posY = this.posY * factor;
        this.radius = this.radius * factor;
        this.dy = clamp(this.dy * factor, MIN_SPEED, MAX_SPEED);
    }

    /**
     * Restricts a value between a minimum and maximum range
     * while preserving its sign.
     *
     * @param value the value to limit
     * @param min the minimum allowed absolute value
     * @param max the maximum allowed absolute value
     * @return the limited value
     */
    private double clamp(double value, double min, double max) {
        double sign = Math.signum(value);
        double abs = Math.abs(value);

        if (abs < min) {
            abs = min;
        }
        if (abs > max) {
            abs = max;
        }

        return abs * sign;
    }
}