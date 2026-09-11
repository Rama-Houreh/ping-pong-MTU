package helloworld.helloworld.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Listens for keyboard events and keeps track of
 * which keys are currently pressed.
 * This allows other controller classes to check
 * continuous key presses for racket movement.
 * @author Rama Houreh
 */
public class KeyboardListener implements EventHandler<KeyEvent> {

    private final Set<KeyCode> pressed = new HashSet<>();
    private boolean escapeHandled = false;

    /**
     * Creates a keyboard listener for tracking pressed keys.
     */
    public KeyboardListener() {
    }

    /**
     * Handles keyboard press and release events.
     * Pressed keys are added to the set and released keys
     * are removed from it.
     * @param event the keyboard event received from JavaFX
     */
    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            pressed.add(event.getCode());
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            pressed.remove(event.getCode());

            if (event.getCode() == KeyCode.ESCAPE) {
                escapeHandled = false;
            }
        }
    }

    /**
     * Checks whether a specific key is currently pressed.
     * @param code the key code to check
     * @return true if the key is currently pressed, false otherwise
     */
    public boolean isPressed(KeyCode code) {
        return pressed.contains(code);
    }

    /**
     * Returns true only once per ESC key press.
     * This prevents pause from being toggled repeatedly
     * while the key is held down.
     * @return true if ESC has just been pressed
     */
    public boolean consumeEscapePress() {
        if (pressed.contains(KeyCode.ESCAPE) && !escapeHandled) {
            escapeHandled = true;
            return true;
        }
        return false;
    }
}