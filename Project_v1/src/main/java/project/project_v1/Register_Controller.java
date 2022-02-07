package project.project_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;

/**
 * Klasa Kontrolera sceny Rejestracji
 */
public class Register_Controller {

    @FXML
    private TextField adres;

    @FXML
    private TextField email;

    @FXML
    private PasswordField haslo;

    @FXML
    private TextField imie;

    @FXML
    private Label info;

    @FXML
    private TextField nazwisko;

    @FXML
    private TextField telefon;

    /**
     * Funkcja wykonująca rejestracje
     * @param event
     * @throws IOException
     */
    @FXML
    void Register(ActionEvent event) throws IOException {
        String table = switch (PSQL.acces) {
            case 0 -> "urzytkownik";
            case 1 -> "servisant";
            case 2 -> "pracownik";
            default -> "";
        };
        try {
            String query="INSERT INTO project."+table+" (imie, nazwisko, email, adres,telefon ) VALUES ( ?, ?, ?, ?, ? )";
            PreparedStatement pst = PSQL.get_create_user_conn().prepareStatement(query);
            pst.setString(1, imie.getText());
            pst.setString(2, nazwisko.getText());
            pst.setString(3, email.getText());
            pst.setString(4, adres.getText());
            pst.setString(5, telefon.getText());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            info.setVisible(true);
            info.setText("Niepoprawne Dane!");
            e.printStackTrace();
            return;
        }

        try {
            String query="CREATE ROLE \""+email.getText()+ "\" LOGIN PASSWORD '" +haslo.getText()+ "'";
            PreparedStatement pst = PSQL.get_create_user_conn().prepareStatement(query);
            pst.executeUpdate();
            switch (PSQL.acces) {
                case 0 -> query = "GRANT standard_user TO \"" + email.getText() + "\"";
                case 1 -> query = "GRANT servisant_user TO \"" + email.getText() + "\"";
                case 2 -> query = "GRANT pracownik_user TO \"" + email.getText() + "\"";
            }
            pst = PSQL.get_create_user_conn().prepareStatement(query);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            info.setVisible(true);
            info.setText("Nie udana rejestracja\n(Urzytkownik juz istenieje lub brak polaczenia z baza danych)");
            e.printStackTrace();
            return;
        }
        PSQL.login(email.getText(),haslo.getText());
        PSQL.create_connection();
        Parent root = FXMLLoader.load(getClass().getResource(table+"-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funkcja powrotu do sceny logowania
     * @param event
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
