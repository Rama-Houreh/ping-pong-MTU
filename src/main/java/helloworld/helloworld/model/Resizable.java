package helloworld.helloworld.model;

/**
 * Defines behaviour for objects that must adjust their size
 * and position when the game window is resized.
 * Classes implementing this interface should scale their
 * horizontal and vertical properties proportionally to
 * the provided resize factor.
 *  @author Rama Houreh
 */
public interface Resizable {

    /**
     * Resizes horizontal properties of an object based on
     * the provided scaling factor.
     *
     * @param factor the horizontal resize factor
     */
    void resizeX(double factor);

    /**
     * Resizes vertical properties of an object based on
     * the provided scaling factor.
     *
     * @param factor the vertical resize factor
     */
    void resizeY(double factor);
}