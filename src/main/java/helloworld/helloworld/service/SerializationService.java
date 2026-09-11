package helloworld.helloworld.service;

import helloworld.helloworld.model.Game;

import java.io.*;

public class SerializationService {

    private static SerializationService instance;

    private SerializationService() {
    }

    public static SerializationService getInstance() {
        if (instance == null) {
            instance = new SerializationService();
        }
        return instance;
    }

    public void saveGame(Game game, String filePath) throws IOException {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(game);
        }
    }

    public Game loadGame(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream(filePath))) {
            return (Game) inputStream.readObject();
        }
    }
}