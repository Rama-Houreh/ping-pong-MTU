package helloworld.helloworld.service;

import helloworld.helloworld.dao.GameDAO;
import helloworld.helloworld.model.Game;

import java.sql.SQLException;

/**
 * Service layer class responsible for coordinating
 * game-related database operations.
 *
 * This class acts as the middle layer between the
 * controller and the DAO layer.
 *
 * @author Rama Houreh
 */
public class GameService {

    private final GameDAO gameDAO;

    /**
     * Creates a new GameService with its DAO dependency.
     */
    public GameService() {
        this.gameDAO = new GameDAO();
    }

    /**
     * Saves the given game to the database.
     *
     * @param game the game to save
     * @throws SQLException if the database operation fails
     */
    public void saveGame(Game game) throws SQLException {
        gameDAO.save(game);
    }

    /**
     * Loads the latest saved game from the database.
     *
     * @return the loaded game, or null if no record exists
     * @throws SQLException if the database operation fails
     */
    public Game loadLatestGame() throws SQLException {
        return gameDAO.loadLatestGame();
    }
}