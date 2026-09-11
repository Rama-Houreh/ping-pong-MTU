package helloworld.helloworld.controller;

import helloworld.helloworld.model.Game;
import helloworld.helloworld.service.SerializationService;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import java.io.FileNotFoundException;

/**
 * Handles menu actions triggered from the game menu.
 * This controller updates the game model in response to
 * user selections such as changing player names, ball speed,
 * game target, pause state and restart.
 * @author Rama Houreh
 */
public class MenuListener {

    private static final String SAVE_FILE = "savedGame.dat";

    private final Controller controller;
    private final Runnable redraw;

    /**
     * Creates a MenuListener with access to the controller
     * and a redraw action for updating the canvas.
     * @param controller the main controller
     * @param redraw action used to redraw the game window
     */
    public MenuListener(Controller controller, Runnable redraw) {
        this.controller = controller;
        this.redraw = redraw;
    }

    /**
     * Returns the current game from the controller.
     * @return current game
     */
    private Game getGame() {
        return controller.getGame();
    }

    /**
     * Closes the application.
     */
    public void setExit() {
        System.out.println("Game closed");
        Platform.exit();
    }

    /**
     * Displays an information dialog about the application.
     */
    public void setAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Super Ping Pong");
        alert.setHeaderText("Super Ping Pong Game \u26BE");
        alert.setContentText("Developed by: Rama Houreh\n" +
                "Course: BSc (Hons) in Software Development\n" +
                "Module: Object-Oriented Programming\n" +
                "Year: 2026\n\nAll rights reserved");
        alert.showAndWait();
    }

    /**
     * Prompts the user to enter the target score needed to win the game.
     * If the input is valid, the game target is updated.
     */
    public void setGameLimit() {
        TextInputDialog dialog = new TextInputDialog(Integer.toString(getGame().getTarget()));
        dialog.setTitle("Set Game Target");
        dialog.setHeaderText("Enter winning score:");

        dialog.showAndWait().ifPresent(value -> {
            try {
                int number = Integer.parseInt(value);
                getGame().setTarget(number);
                if (redraw != null) {
                    redraw.run();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number for target");
            }
        });
    }

    /**
     * Prompts the user to set a player's name.
     * The selected player's name is updated if the input is valid.
     * @param playerNumber player number, expected to be 1 or 2
     */
    public void setPlayerName(int playerNumber) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Set Player Name");
        dialog.setHeaderText("Enter name for Player " + playerNumber + ":");

        dialog.showAndWait().ifPresent(name -> {
            if (name == null || name.trim().isEmpty()) {
                return;
            }

            String formatted = name.substring(0, 1).toUpperCase() + name.substring(1);

            if (playerNumber == 1) {
                getGame().getPlayer1().setName(formatted);
            } else {
                getGame().getPlayer2().setName(formatted);
            }

            if (redraw != null) {
                redraw.run();
            }
        });
    }

    /**
     * Prompts the user to set the game name.
     * If the input is valid, the game name is updated.
     */
    public void setGameName() {
        TextInputDialog dialog = new TextInputDialog(getGame().getGameName());
        dialog.setTitle("Set Game Name");
        dialog.setHeaderText("Enter the game name:");

        dialog.showAndWait().ifPresent(name -> {
            if (name == null || name.trim().isEmpty()) {
                return;
            }

            getGame().setGameName(name.trim());

            if (redraw != null) {
                redraw.run();
            }
        });
    }

    /**
     * Prompts the user to set a new ball speed.
     * If the input is valid, both horizontal and vertical
     * movement speeds of the ball are updated.
     */
    public void setBallSpeed() {
        TextInputDialog dialog = new TextInputDialog(Double.toString(getGame().getBall().getDx()));
        dialog.setTitle("Set Ball Speed");
        dialog.setHeaderText("Enter ball speed:");

        dialog.showAndWait().ifPresent(value -> {
            try {
                double speed = Double.parseDouble(value);
                getGame().getBall().setDx(speed);
                getGame().getBall().setDy(speed);
                if (redraw != null) {
                    redraw.run();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ball speed");
            }
        });
    }

    /**
     * Prompts the user to change the racket height for both players.
     * If the input is valid, both rackets are updated.
     */
    public void setRacketSize() {
        TextInputDialog dialog = new TextInputDialog(Double.toString(getGame().getPlayer1().getRacket().getSize()));
        dialog.setTitle("Set Racket Size");
        dialog.setHeaderText("Enter racket height:");

        dialog.showAndWait().ifPresent(value -> {
            try {
                double height = Double.parseDouble(value);
                getGame().getPlayer1().getRacket().setSize(height);
                getGame().getPlayer2().getRacket().setSize(height);
                if (redraw != null) {
                    redraw.run();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid racket size");
            }
        });
    }

    /**
     * Prompts the user to set how often the ball speed should increase.
     * The value represents the number of bounces between speed increases.
     */
    public void setSpeedIncreaseInterval() {
        TextInputDialog dialog = new TextInputDialog(Integer.toString(getGame().getSpeedIncreaseEvery()));
        dialog.setTitle("Set Ball Speed Increase Interval");
        dialog.setHeaderText("Enter how often the ball speed should increase (in points):");

        dialog.showAndWait().ifPresent(value -> {
            try {
                int interval = Integer.parseInt(value);
                getGame().setSpeedIncreaseEvery(interval);
            } catch (NumberFormatException e) {
                System.out.println("Invalid interval");
            }
        });
    }

    /**
     * Toggles the pause state of the game.
     * If the game is paused it resumes, otherwise it pauses.
     */
    public void togglePause() {
        getGame().setPausedByUser(!getGame().isPausedByUser());
        if (redraw != null) {
            redraw.run();
        }
    }

    /**
     * Restarts the game and redraws the game window.
     */
    public void restartGame() {
        getGame().restartGame();
        if (redraw != null) {
            redraw.run();
        }
    }

    /**
     * Saves the current game state to a file using the serialization service.
     * The game is paused before saving.
     */
    public void saveGameToFile() {
        try {
            getGame().setPausedByUser(true);
            SerializationService.getInstance().saveGame(getGame(), SAVE_FILE);
            showInformation("Save Game", "Game saved successfully.");
            if (redraw != null) {
                redraw.run();
            }
        } catch (Exception e) {
            showError("Save Error", "Could not save the game.");
        }
    }

    /**
     * Loads the game state from a file using the serialization service.
     * After loading, racket colours are restored and the loaded game
     * replaces the current game in the controller.
     */
    public void loadGameFromFile() {
        try {
            Game loadedGame = SerializationService.getInstance().loadGame(SAVE_FILE);

            loadedGame.getPlayer1().getRacket().setColor(Color.YELLOW);
            loadedGame.getPlayer2().getRacket().setColor(Color.LIGHTBLUE);

            controller.setGame(loadedGame);

            showInformation("Load Game", "Game loaded successfully.");

            if (redraw != null) {
                redraw.run();
            }
        } catch (FileNotFoundException e) {
            showError("Load Game", "No saved file was found.");
        } catch (Exception e) {
            showError("Load Error", "Could not load the game.");
        }
    }
    /**
     * Displays an information popup.
     * @param title alert title
     * @param message alert message
     */
    private void showInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error popup.
     * @param title alert title
     * @param message alert message
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Saves the current game to the database through the controller.
     */
    public void saveGameToDatabase() {
        try {
            controller.saveGameToDatabase();
            showInformation("Database Save", "Game saved to database successfully.");
        } catch (Exception e) {
            showError("Database Save Error", "Could not save the game to the database.");
        }
    }

    /**
     * Loads the latest saved game from the database through the controller.
     * After loading, racket colours are restored and the game is redrawn.
     */
    public void loadGameFromDatabase() {
        try {
            boolean loaded = controller.loadLatestGameFromDatabase();

            if (!loaded) {
                showError("Database Load", "No saved game was found in the database.");
                return;
            }

            getGame().getPlayer1().getRacket().setColor(Color.YELLOW);
            getGame().getPlayer2().getRacket().setColor(Color.LIGHTBLUE);

            System.out.println("Loaded from database:");
            System.out.println(getGame());

            showInformation("Database Load", "Latest game loaded from database successfully.");

            if (redraw != null) {
                redraw.run();
            }
        } catch (Exception e) {
            showError("Database Load Error", "Could not load the game from the database.");
        }
    }
}