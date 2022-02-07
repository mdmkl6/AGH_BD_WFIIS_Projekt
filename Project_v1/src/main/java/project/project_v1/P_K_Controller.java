package project.project_v1;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa kontrolera sceny dodającej przedmiot do kategorii
 */
public class P_K_Controller {

    Map<String , Integer> prod = new HashMap<>();
    Map<String , Integer> kat = new HashMap<>();

    /**
     * Funkcja przy inicjalizacji sceny dodająca elementy do comboboxow
     */
    @FXML
    void initialize()
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
            ResultSet rs = PSQL.c.createStatement().executeQuery("SELECT produkt_id, nazwa FROM project.produkt");
            while(rs.next()){
                prod.put(rs.getString(2),rs.getInt(1));
                items.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        set_cbox(produkt,items);

        ObservableList<String> items2 = FXCollections.observableArrayList();
        try {
            ResultSet rs = PSQL.c.createStatement().executeQuery("SELECT kategoria_id, nazwa FROM project.kategoria");
            while(rs.next()){
                kat.put(rs.getString(2),rs.getInt(1));
                items2.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        set_cbox(kategoria,items2);
    }

    /**
     * Funcja dodajaca elementu do comboboxów
     * @param cbox combobox
     * @param items lista elementów
     */
    void set_cbox(ComboBox<String> cbox,ObservableList<String> items)
    {
        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);
        cbox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cbox.getEditor();
            final String selected = cbox.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });
        cbox.setItems(filteredItems);
    }

    @FXML
    private ComboBox<String> kategoria;

    @FXML
    private ComboBox<String> produkt;

    /**
     * Funkcja dodająca produkt do kategorii
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        try {
            String query="INSERT INTO project.kategoria_produkt (kategoria_id,produkt_id) VALUES ("+kat.get(kategoria.getValue())+","+prod.get(produkt.getValue())+")";
            PSQL.c.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }

}
