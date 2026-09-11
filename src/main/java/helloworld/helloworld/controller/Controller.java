package helloworld.helloworld.controller;

import helloworld.helloworld.model.Game;
import helloworld.helloworld.service.GameService;

import java.sql.SQLException;

/**
 * Central controller that provides access to the game model.
 * This class acts as the connection between the View layer
 * (JavaFX interface) and the Model layer (game data).
 * It also acts as a bridge to the service layer.
 * @author Rama Houreh
 */
public class Controller {

    private Game game = new Game();
    private final GameService gameService = new GameService();

    /**
     * Returns the current game model.
     * @return the game instance
     */
    public Game getGame() {
        return game;
    }

    /**
     * Replaces the current game model with another instance.
     * @param game the new game model
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Saves the current game to the database using the service layer.
     * @throws SQLException if the database operation fails
     */
    public void saveGameToDatabase() throws SQLException {
        gameService.saveGame(game);
    }

    /**
     * Loads the latest saved game from the database using the service layer.
     * If a game is found, it replaces the current game model.
     * @throws SQLException if the database operation fails
     */
    public boolean loadLatestGameFromDatabase() throws SQLException {
        Game loadedGame = gameService.loadLatestGame();
        if (loadedGame != null) {
            this.game = loadedGame;
            return true;
        }
        return false;
    }
}