package project.project_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kontroler scceny startowej
 */
public class Main_Controller {

    /**
     * Przejscie na scene logowania dla urzytkownik
     * @param event
     * @throws IOException
     */
    @FXML
    void login_urzytkownik(ActionEvent event) throws IOException {
        PSQL.acces=0;
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Przejscie na scene logowania dla pracownik
     * @param event
     * @throws IOException
     */
    @FXML
    void login_pracownik(ActionEvent event) throws IOException {
        PSQL.acces=2;
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Przejscie na scene logowania dla Servisanta
     * @param event
     * @throws IOException
     */
    @FXML
    void login_servisant(ActionEvent event) throws IOException {
        PSQL.acces=1;
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
