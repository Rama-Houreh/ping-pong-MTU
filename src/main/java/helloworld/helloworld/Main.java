package helloworld.helloworld;

import helloworld.helloworld.controller.BallManager;
import helloworld.helloworld.controller.Controller;
import helloworld.helloworld.controller.KeyboardListener;
import helloworld.helloworld.controller.MenuListener;
import helloworld.helloworld.controller.RacketManager;
import helloworld.helloworld.model.Game;
import helloworld.helloworld.view.GameMenu;
import helloworld.helloworld.view.LabCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main JavaFX application class for the Super Ping_Pong game.
 * It creates the main window, connects the MVC components,
 * handles resizing and starts the game threads.
 *
 * @author Rama Houreh
 */
public class Main extends Application {
    private final Controller labController = new Controller();

    private LabCanvas canvas;
    private GameMenu gameMenu;

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Builds the main game window, initialises the menu and canvas,
     * connects keyboard input and starts the ball and racket threads.
     *
     * @param primaryStage the main application window
     */
    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        canvas = new LabCanvas(600, 400);
        root.setCenter(canvas);

        Runnable refreshUI = () -> {
            Game currentGame = labController.getGame();
            primaryStage.setTitle(currentGame.getGameName());
            canvas.drawGame(currentGame);
        };

        MenuListener menuListener = new MenuListener(labController, refreshUI);
        gameMenu = new GameMenu(menuListener);
        root.setTop(gameMenu.getMenuBar());

        Scene scene = new Scene(root, 600, 440);
        primaryStage.setScene(scene);

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty().subtract(gameMenu.getMenuBar().heightProperty()));

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            Game game = labController.getGame();

            if (oldVal.doubleValue() <= 0) {
                game.setDimensionX(newVal.doubleValue());
                refreshUI.run();
                return;
            }

            double factor = newVal.doubleValue() / game.getDimensionX();
            game.setDimensionX(newVal.doubleValue());
            game.resizeX(factor);
            refreshUI.run();
        });

        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            Game game = labController.getGame();

            if (oldVal.doubleValue() <= 0) {
                game.setDimensionY(newVal.doubleValue());
                refreshUI.run();
                return;
            }

            double factor = newVal.doubleValue() / game.getDimensionY();
            game.setDimensionY(newVal.doubleValue());
            game.resizeY(factor);
            refreshUI.run();
        });

        KeyboardListener keyboardListener = new KeyboardListener();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyboardListener);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyboardListener);

        root.requestFocus();
        primaryStage.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                root.requestFocus();
            }
        });

        primaryStage.show();

        labController.getGame().setDimensionX(canvas.getWidth());
        labController.getGame().setDimensionY(canvas.getHeight());
        refreshUI.run();

        RacketManager racketManager = new RacketManager(labController, canvas, keyboardListener);
        Thread racketThread = new Thread(racketManager);
        racketThread.setDaemon(true);
        racketThread.start();

        BallManager ballManager = new BallManager(labController, canvas);
        Thread ballThread = new Thread(ballManager);
        ballThread.setDaemon(true);
        ballThread.start();
    }
}