package project.project_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Klasa kontrolera dodającego magazyn
 */
public class Magazyn_Controller {

    @FXML
    private TextField adres;

    @FXML
    private TextField nazwa;

    /**
     * Funkcja dodająca magazyn
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        try {
            String query="INSERT INTO project.magazyn (nazwa, adres) VALUES ('"+nazwa.getText()+"','"+adres.getText()+"')";
            PSQL.c.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }

}
