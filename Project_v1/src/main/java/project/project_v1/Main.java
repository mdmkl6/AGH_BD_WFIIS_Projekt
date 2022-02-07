package project.project_v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Klasa podstawowa startująca program
 */
public class Main extends Application {

    /**
     * Funkcja tworząca pierwszą scene i wczytująca url z pliku
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("url.txt"));
        PSQL.url=reader.readLine();
        System.out.println(PSQL.url);
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Sklep i Servis Komputerowy");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Funkcja startująca
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}