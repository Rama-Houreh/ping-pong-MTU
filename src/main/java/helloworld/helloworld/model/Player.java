package helloworld.helloworld.model;

import java.io.Serializable;

/**
 * Represents a player in the ping pong game.
 * Each player has a name, a score and a racket used
 * to interact with the ball during the game.
 * @author Rama Houreh
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int score;
    private Racket racket;

    /**
     * Creates a new player with the given name and racket.
     * The player score is initialised to zero.
     *
     * @param name the player's name
     * @param racket the racket controlled by the player
     */
    public Player(String name, Racket racket) {
        this.name = name;
        this.racket = racket;
        this.score = 0;
    }

    /**
     * Returns the player's name.
     *
     * @return the player name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current score of the player.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the racket associated with this player.
     *
     * @return the player's racket
     */
    public Racket getRacket() {
        return racket;
    }

    /**
     * Sets a new name for the player.
     *
     * @param name the new player name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the player's score.
     *
     * @param score the new score value
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Assigns a different racket to the player.
     *
     * @param racket the racket to assign
     */
    public void setRacket(Racket racket) {
        this.racket = racket;
    }
}