package helloworld.helloworld.view;

import helloworld.helloworld.controller.MenuListener;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Creates and manages the menu bar used in the game window.
 * The menu allows the user to configure game settings such as
 * player names, ball speed, racket size, game target score,
 * pause/resume functionality, restart and exiting the application.
 * @author Rama Houreh
 */
public class GameMenu {

    private MenuBar menuBar;
    private Menu menuFile;
    private Menu menuHelp;

    private MenuItem menuItemExit;
    private MenuItem menuItemAbout;
    private MenuItem menuItemGameLimit;
    private MenuItem menuItemGameName;
    private MenuItem menuItemPlayer1Name;
    private MenuItem menuItemPlayer2Name;
    private MenuItem menuItemBallSpeed;
    private MenuItem menuItemRacketSize;
    private MenuItem menuItemSpeedIncrease;
    private MenuItem menuItemPause;
    private MenuItem menuItemRestart;
    private MenuItem menuItemSaveGame;
    private MenuItem menuItemLoadGame;
    private MenuItem menuItemSaveToDatabase;
    private MenuItem menuItemLoadFromDatabase;

    private MenuListener menuListener;

    /**
     * Constructs the menu bar and initialises all menu items.
     * The menu items are connected to the controller through
     * the MenuListener so that user actions trigger game logic.
     *
     * @param listener the controller responsible for handling menu events
     */
    public GameMenu(MenuListener listener) {
        this.menuListener = listener;

        menuBar = new MenuBar();

        Menu menuGame = new Menu("Game");
        Menu menuSettings = new Menu("Settings");
        Menu menuHelp = new Menu("Help");

        menuItemExit = new MenuItem("Exit");
        menuItemAbout = new MenuItem("About");
        menuItemGameLimit = new MenuItem("Set Game Target");
        menuItemGameName = new MenuItem("Set Game Name");
        menuItemPlayer1Name = new MenuItem("Set Player 1 Name");
        menuItemPlayer2Name = new MenuItem("Set Player 2 Name");
        menuItemBallSpeed = new MenuItem("Set Ball Speed");
        menuItemRacketSize = new MenuItem("Set Racket Size");
        menuItemSpeedIncrease = new MenuItem("Set Speed Increase Interval");
        menuItemPause = new MenuItem("Pause / Resume Game");
        menuItemRestart = new MenuItem("Restart Game");
        menuItemSaveGame = new MenuItem("Save Game to File");
        menuItemLoadGame = new MenuItem("Load Game to File");
        menuItemSaveToDatabase = new MenuItem("Save To Database");
        menuItemLoadFromDatabase = new MenuItem("Load From Database");

        menuGame.getItems().addAll(
                menuItemRestart,
                menuItemPause,
                menuItemSaveGame,
                menuItemLoadGame,
                menuItemSaveToDatabase,
                menuItemLoadFromDatabase,
                menuItemExit
        );

        menuSettings.getItems().addAll(
                menuItemGameName,
                menuItemPlayer1Name,
                menuItemPlayer2Name,
                menuItemGameLimit,
                menuItemBallSpeed,
                menuItemRacketSize,
                menuItemSpeedIncrease
        );

        menuHelp.getItems().add(menuItemAbout);

        menuBar.getMenus().addAll(menuGame, menuSettings, menuHelp);

        handleClicking();
    }

    /**
     * Connects menu items to their corresponding actions in the controller.
     * Each menu item triggers a specific method inside the MenuListener.
     */
    private void handleClicking() {
        menuItemExit.setOnAction(e -> menuListener.setExit());
        menuItemAbout.setOnAction(e -> menuListener.setAbout());
        menuItemGameLimit.setOnAction(e -> menuListener.setGameLimit());
        menuItemGameName.setOnAction(e -> menuListener.setGameName());
        menuItemPlayer1Name.setOnAction(e -> menuListener.setPlayerName(1));
        menuItemPlayer2Name.setOnAction(e -> menuListener.setPlayerName(2));
        menuItemBallSpeed.setOnAction(e -> menuListener.setBallSpeed());
        menuItemRacketSize.setOnAction(e -> menuListener.setRacketSize());
        menuItemSpeedIncrease.setOnAction(e -> menuListener.setSpeedIncreaseInterval());
        menuItemPause.setOnAction(e -> menuListener.togglePause());
        menuItemRestart.setOnAction(e -> menuListener.restartGame());
        menuItemSaveGame.setOnAction(e -> menuListener.saveGameToFile());
        menuItemLoadGame.setOnAction(e -> menuListener.loadGameFromFile());
        menuItemSaveToDatabase.setOnAction(e -> menuListener.saveGameToDatabase());
        menuItemLoadFromDatabase.setOnAction(e -> menuListener.loadGameFromDatabase());
    }

    /**
     * Returns the menu bar so it can be placed in the main layout.
     *
     * @return the configured MenuBar
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Sets a different menu bar instance if needed.
     *
     * @param menuBar the menu bar to assign
     */
    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }
}