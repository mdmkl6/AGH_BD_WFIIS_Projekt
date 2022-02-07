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

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Kontroler sceny logowania
 */
public class Login_Controller {

    @FXML
    private TextField email;

    @FXML
    private PasswordField haslo;

    @FXML
    private Label info;

    /**
     * Funkcja logowania
     * @param event
     * @throws IOException
     */
    @FXML
    void Login(ActionEvent event) throws IOException {
        PSQL.login(email.getText(),haslo.getText());
        String query="";
        switch (PSQL.acces) {
            case 0 -> query="{call project.u_id_by_mail('"+PSQL.user+"')}";
            case 1 -> query="{call project.s_id_by_mail('"+PSQL.user+"')}";
            case 2 -> query="{call project.p_id_by_mail('"+PSQL.user+"')}";
        }
        try {
            ResultSet rs = PSQL.get_create_user_conn().prepareCall(query).executeQuery();
            rs.next();
            if(rs.getString(1)==null) {
                info.setVisible(true);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        if(PSQL.create_connection())
        {
            String view = switch (PSQL.acces) {
                case 0 -> "urzytkownik";
                case 1 -> "servisant";
                case 2 -> "pracownik";
                default -> "";
            };
            Parent root = FXMLLoader.load(getClass().getResource(view+"-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            info.setVisible(true);
        }
    }

    /**
     * Funkcja przejscia na scene rejestracji
     * @param event
     * @throws IOException
     */
    @FXML
    void Register(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funkcja powrotu do sceny podstawowej
     * @param event
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
