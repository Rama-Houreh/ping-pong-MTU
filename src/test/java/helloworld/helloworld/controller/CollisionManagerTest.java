package helloworld.helloworld.controller;

import helloworld.helloworld.model.Ball;
import helloworld.helloworld.model.Racket;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the CollisionManager class.
 * These tests verify that the ball correctly bounces
 * from the left and right rackets and does not bounce
 * when no collision occurs.
 * @author Rama Houreh
 */
public class CollisionManagerTest {

    /**
     * Tests collision with the left racket.
     * The ball should bounce and reverse its horizontal direction.
     */
    @Test
    public void testBounceOnLeftRacket() {
        CollisionManager manager = new CollisionManager();

        Ball ball = new Ball(35, 110, -3, 0, 15);
        Racket leftRacket = new Racket(20, 100, 20, 80, Color.YELLOW);
        Racket rightRacket = new Racket(560, 100, 20, 80, Color.LIGHTBLUE);

        boolean bounced = manager.handleRacketBounce(ball, leftRacket, rightRacket);

        assertTrue(bounced);
        assertTrue(ball.getDx() > 0);
        assertEquals(40.0, ball.getPosX(), 0.001);
    }

    /**
     * Tests collision with the right racket.
     * The ball should bounce and reverse its horizontal direction.
     */
    @Test
    public void testBounceOnRightRacket() {
        CollisionManager manager = new CollisionManager();

        Ball ball = new Ball(550, 110, 3, 0, 15);
        Racket leftRacket = new Racket(20, 100, 20, 80, Color.YELLOW);
        Racket rightRacket = new Racket(560, 100, 20, 80, Color.LIGHTBLUE);

        boolean bounced = manager.handleRacketBounce(ball, leftRacket, rightRacket);

        assertTrue(bounced);
        assertTrue(ball.getDx() < 0);
        assertEquals(545.0, ball.getPosX(), 0.001);
    }

    /**
     * Tests that no bounce occurs when the ball
     * does not intersect with either racket.
     */
    @Test
    public void testNoBounceWhenBallMissesRacket() {
        CollisionManager manager = new CollisionManager();

        Ball ball = new Ball(300, 20, 3, 0, 15);
        Racket leftRacket = new Racket(20, 100, 20, 80, Color.YELLOW);
        Racket rightRacket = new Racket(560, 100, 20, 80, Color.LIGHTBLUE);

        boolean bounced = manager.handleRacketBounce(ball, leftRacket, rightRacket);

        assertFalse(bounced);
        assertEquals(3.0, ball.getDx(), 0.001);
        assertEquals(300.0, ball.getPosX(), 0.001);
    }
}