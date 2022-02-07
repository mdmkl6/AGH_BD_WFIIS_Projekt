package project.project_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Klasa Kontrolera sceny dodającej naprawe
 */
public class Naprawa_Controller {

    public static String id;

    @FXML
    private TextField opis;

    @FXML
    private TextField status;

    /**
     * Funkcja dodająca naprawe
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        try {
            String query="INSERT INTO project.naprawy (id, servisant_id, opis) VALUES ("+id+","+PSQL.userid+",'"+opis.getText()+"')";
            PSQL.c.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String query="UPDATE project.servis SET status = '"+status.getText()+"' WHERE id= "+id;
            PSQL.c.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }

}
