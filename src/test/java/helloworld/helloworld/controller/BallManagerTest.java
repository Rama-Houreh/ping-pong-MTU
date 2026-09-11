package helloworld.helloworld.controller;

import helloworld.helloworld.model.Game;
import helloworld.helloworld.view.LabCanvas;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the BallManager class.
 * These tests verify important game logic such as
 * detecting goals and determining when the game ends.
 * @author Rama Houreh
 */
public class BallManagerTest {

    Controller controller = new Controller();
    Game game = controller.getGame();
    LabCanvas canvas = new LabCanvas(600, 600);
    BallManager manager = new BallManager(controller, canvas);

    /**
     * Initialises common test values before each test runs.
     */
    @Before
    public void initialise() {
        game.setDimensionX(600);
        game.setDimensionY(600);
        game.setTarget(10);
    }

    /**
     * Tests whether the game correctly detects
     * when the target score has been reached.
     */
    @Test
    public void testEndOfGame() {

        game.getPlayer1().setScore(11);
        game.getPlayer2().setScore(7);

        assertTrue(manager.checkEndOfGame(game));

        game.getPlayer1().setScore(7);
        game.getPlayer2().setScore(7);

        assertFalse(manager.checkEndOfGame(game));
    }

    /**
     * Tests whether player 2 scoring is correctly detected
     * when the ball reaches the left side of the screen.
     */
    @Test
    public void testGoalPlayer2() {

        game.getBall().setPosX(1);
        assertTrue(manager.player2Scores(game));

        game.getBall().setPosX(100);
        assertFalse(manager.player2Scores(game));
    }
}