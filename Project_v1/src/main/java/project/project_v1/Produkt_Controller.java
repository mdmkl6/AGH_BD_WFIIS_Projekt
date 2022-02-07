package project.project_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Klasa Kontrolera sceny dodającej produkt
 */
public class Produkt_Controller {

    @FXML
    private TextField cena;

    @FXML
    private TextField nazwa;

    @FXML
    private TextField opis;

    /**
     * Funkcja dodająca produkt
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        try {
            String query="INSERT INTO project.produkt (nazwa, opis_produktu, cena) VALUES ('"+nazwa.getText()+"','"+opis.getText()+"',"+cena.getText()+")";
            PSQL.c.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }
}
