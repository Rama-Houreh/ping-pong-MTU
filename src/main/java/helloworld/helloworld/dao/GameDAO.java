package helloworld.helloworld.dao;

import helloworld.helloworld.database.DatabaseConnection;
import helloworld.helloworld.model.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class responsible for saving and loading Game data
 * from the relational database.
 * This class isolates SQL code from the rest of the application.
 *
 * @author Rama Houreh
 */
public class GameDAO {

    /**
     * Saves a game record into the database.
     *
     * @param game the game object to save
     * @throws SQLException if the insert fails
     */
    public void save(Game game) throws SQLException {
        String sql = "INSERT INTO game " +
                "(game_name, player_1_name, player_2_name, player_1_score, player_2_score, score_limit) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, game.getGameName());
            preparedStatement.setString(2, game.getPlayer1().getName());
            preparedStatement.setString(3, game.getPlayer2().getName());
            preparedStatement.setInt(4, game.getPlayer1().getScore());
            preparedStatement.setInt(5, game.getPlayer2().getScore());
            preparedStatement.setInt(6, game.getTarget());

            preparedStatement.executeUpdate();
        }
    }

    /**
     * Loads the latest saved game from the database.
     * The object is reconstructed using the Builder pattern.
     *
     * @return the loaded game, or null if no record exists
     * @throws SQLException if the query fails
     */
    public Game loadLatestGame() throws SQLException {
        String sql = "SELECT * FROM game ORDER BY id DESC LIMIT 1";

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return new Game.Builder()
                        .withGameName(resultSet.getString("game_name"))
                        .withPlayer1Name(resultSet.getString("player_1_name"))
                        .withPlayer2Name(resultSet.getString("player_2_name"))
                        .withPlayer1Score(resultSet.getInt("player_1_score"))
                        .withPlayer2Score(resultSet.getInt("player_2_score"))
                        .withScoreLimit(resultSet.getInt("score_limit"))
                        .build();
            }
        }

        return null;
    }
}