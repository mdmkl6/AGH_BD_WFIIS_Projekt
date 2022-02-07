package project.project_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Kontroler sceny dodawania kategorii
 */
public class Kategoria_Controller {

    @FXML
    private TextField nazwa;

    @FXML
    private TextField opis;

    /**
     * Funkcja dodająca kategorie
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        try {
            String query="INSERT INTO project.kategoria (nazwa, opis) VALUES ('"+nazwa.getText()+"','"+opis.getText()+"')";
            PSQL.c.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }

}
