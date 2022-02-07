package project.project_v1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa Kontrolera sceny servisanta
 */
public class Servisant_Controller {

    @FXML
    private TableView<?> Table;

    /**
     * Funkcja powracjąca do sceny startowej
     * @param event
     * @throws IOException
     */
    @FXML
    void Logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funkjca wypisująca naprawy przeprowadzone przez servisanta
     * @param event
     */
    @FXML
    void Naprawy(ActionEvent event) {
        String query = "SELECT id_napraw AS \"ID\",id AS \"ID_zlecenia\",opis FROM project.naprawy Where servisant_id="+PSQL.userid;
        PSQL.create_table(Table,query);
    }

    /**
     * Funkcja wypisująca dane servisanta
     * @param event
     */
    @FXML
    void Servisant(ActionEvent event) {
        String query = "SELECT * FROM project.servisant Where servisant_id = "+PSQL.userid;
        PSQL.create_table(Table,query);
    }

    /**
     * Funkcja wypisująca wszystkie zgłoszenia servisowe pozwalająca spradzić historie napraw zgłoszenia jak i przerpowadzić naprawe na danym zgłoszeniu
     * @param event
     */
    @FXML
    void servis(ActionEvent event) {
        String query="SELECT id, urzadzenie,cechy_produktu,opis_usterki,status FROM project.servis";
        PSQL.create_servis_table(Table,query,"Przeprowdz naprawe",(ObservableList p) -> {
            try {
                Naprawa_Controller.id= (String) p.get(0);
                Parent root = FXMLLoader.load(getClass().getResource("naprawa-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Dodaj Naprawe");
                stage.setScene(new Scene(root, 400, 300));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return p;
        });
    }

}
