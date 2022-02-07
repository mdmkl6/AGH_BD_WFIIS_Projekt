package project.project_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Klasa kontrolera dodjącego zgłoszenie servisowe
 */
public class U_Servis_Controller {

    @FXML
    private TextField cechy;

    @FXML
    private TextField opis;

    @FXML
    private TextField urzadzenie;

    /**
     * Dodanie zgłoszenia servisowego
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        try {
            String query="INSERT INTO project.servis (urzadzenie, urzytkownik_id, cechy_produktu, opis_usterki, status ) VALUES ( ?, ?, ?, ?, ? )";
            PreparedStatement pst = PSQL.c.prepareStatement(query);
            pst.setString(1, urzadzenie.getText());
            pst.setInt(2, Integer.parseInt(PSQL.userid));
            pst.setString(3, cechy.getText());
            pst.setString(4, opis.getText());
            pst.setString(5, "Zlozone");
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }

}
