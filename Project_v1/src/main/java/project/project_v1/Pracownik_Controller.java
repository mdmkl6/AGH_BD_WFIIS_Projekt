package project.project_v1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Klasa Kontrolera sceny pracownik
 */
public class Pracownik_Controller {

    @FXML
    private TableView<?> Table;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private ComboBox<String> kategoria;

    @FXML
    private Label label1;

    /**
     * Funkcja otwierająca scene dostawy
     * @param event
     * @throws IOException
     */
    @FXML
    void Dostawa(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dostawa-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dostawa");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    /**
     * Funkaja wyszukująca przedmity z wybranej katgorii
     * @param event
     */
    @FXML
    void Find(ActionEvent event) {
        String query="SELECT \"ID\",\"Nazwa\",\"Opis\",\"Cena\",\"Ilosc w Magazynie\" FROM project.kategory_prod WHERE \"Kategoria\" LIKE '"+kategoria.getValue()+"' ORDER BY \"ID\"";
        crete_sklep_table(query);
    }

    /**
     * Funkcja dokonująca zakupu wszyskich przedmiotów z koszyka
     * @param event
     */
    @FXML
    void KUP(ActionEvent event) {
        String query;

        Iterator iter = Basket.koszyk.keySet().iterator();
        while (iter.hasNext()) {
            String pr= (String) iter.next();
            String il= String.valueOf(Basket.koszyk.get(pr));
            query="INSERT INTO project.tranzakcje_stacjonarne (produkt_id, pracownik_id, ilosc,status) VALUES ( "+pr+","+PSQL.userid+","+il+",'Zlozone' )";
            try {
                PSQL.c.prepareStatement(query).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
        Basket.koszyk.clear();
        Table.getColumns().clear();
    }

    /**
     * Funkcja wypisująca przedmioty w koszyku
     * @param event
     */
    @FXML
    void Koszyk(ActionEvent event) {
        if(Basket.koszyk.size()==0)
            return;
        button1.setVisible(false);
        button2.setVisible(true);
        button3.setVisible(true);
        label1.setText("Koszyk");
        kategoria.setVisible(false);
        create_koszyk_table();
    }

    /**
     * Funkcja przenosząca do sceny podstawowej
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
     * Funkcja wypisująca magazyny
     * @param event
     */
    @FXML
    void Magazyn(ActionEvent event) {
        button1.setVisible(false);
        kategoria.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        label1.setText("Magazyny:");
        String query = "SELECT * FROM project.magazyn";
        PSQL.create_table(Table,query);
    }

    /**
     * Funkcja wypisująca dane o urzytkowniku
     * @param event
     */
    @FXML
    void Pracownik(ActionEvent event) {
        button1.setVisible(false);
        kategoria.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        label1.setText("Pracownik");
        String query = "SELECT * FROM project.pracownik Where pracownik_id = "+PSQL.userid;
        PSQL.create_table(Table,query);
    }

    /**
     * Funkcja odświerzająca koszyk
     * @param event
     */
    @FXML
    void Refrseh(ActionEvent event) {
        create_koszyk_table();
    }

    /**
     * Funkcja wypisująca liste produktów z możliwością dodania ich do koszyka
     * @param event
     */
    @FXML
    void Sklep(ActionEvent event) {
        button1.setVisible(true);
        kategoria.setVisible(true);
        button2.setVisible(false);
        button3.setVisible(false);
        label1.setText("Sklep     Kategoria:");

        crete_sklep_table("SELECT * FROM project.lista_prod ORDER BY \"ID\"");
        try {
            ResultSet rs = PSQL.c.createStatement().executeQuery("SELECT nazwa FROM project.kategoria");
            kategoria.getItems().clear();
            while(rs.next()){
                kategoria.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funkcja wypisująca dokonane tranzakcje
     * @param event
     */
    @FXML
    void Tranzakcje(ActionEvent event) {
        button1.setVisible(false);
        kategoria.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        label1.setText("Historia Traznzakcji");
        String query = "SELECT \"ID\",\"ID_Produktu\",\"Nazwa_Produktu\",\"Ilosc\",\"Status_Zakowienia\" FROM project.t_stacjonarne Where pracownik_id="+PSQL.userid;
        PSQL.create_table(Table,query);
    }

    /**
     * Funkcja przenosząca do sceny dodjącej kategorie
     * @param event
     * @throws IOException
     */
    @FXML
    void add_kategory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("kategory-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dostawa");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    /**
     * Funkcja przenosząca do sceny dodjącej magazyn
     * @param event
     * @throws IOException
     */
    @FXML
    void add_magazyn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("magazyn-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dostawa");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    /**
     * Funkcja przenosząca do sceny dodjącej produkt
     * @param event
     * @throws IOException
     */
    @FXML
    void add_produkt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("produkt-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dostawa");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    /**
     * Funkcja przenosząca do sceny dodjącej produkt do kategorii
     * @param event
     * @throws IOException
     */
    @FXML
    void add_produkt_to_kategory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("p-k-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dostawa");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    /**
     * Funkcja przenosząca do sceny dodjącej zgłoszenie servisowe
     * @param event
     * @throws IOException
     */
    @FXML
    void add_servis(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("u-servis-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dodaj zgłoszenie servisowe");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    /**
     * Funkcja tworząca tabele sklepu
     * @param SQL
     */
    void crete_sklep_table(String SQL)
    {
        PSQL.create_table_buttons(Table,SQL,"Dodaj do koszyka",(ObservableList p) -> {
            try {
                Buy_Controller.n=1;
                Buy_Controller.set=false;
                Basket.max=Integer.parseInt(String.valueOf(p.get(4)));
                Basket.id= (String) p.get(0);
                Parent root = FXMLLoader.load(getClass().getResource("buy-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Dodaj do koszyka");
                stage.setScene(new Scene(root, 200, 300));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return p;
        });
    }

    /**
     * Funkcja tworząca tabele koszyka
     */
    void create_koszyk_table()
    {
        String query="SELECT * FROM project.lista_prod WHERE \"ID\" IN (";
        Iterator iter = Basket.koszyk.keySet().iterator();
        query+=iter.next();
        while (iter.hasNext()) {
            query+=", ";
            query+=iter.next();
        }
        query+=") ORDER BY \"ID\"";
        PSQL.create_table_koszyk(Table,query,(ObservableList p) -> {
            try {
                Buy_Controller.n = Basket.koszyk.get(p.get(0));
                Buy_Controller.set = true;
                Basket.max = Integer.parseInt(String.valueOf(p.get(4)));
                Basket.id = (String) p.get(0);
                Parent root = FXMLLoader.load(getClass().getResource("buy-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Zmień Ilość");
                stage.setScene(new Scene(root, 200, 300));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return p;
        });
    }

}
