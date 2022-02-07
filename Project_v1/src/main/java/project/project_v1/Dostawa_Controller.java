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
 * Klasa kontrolera sceny Dostawa
 */
public class Dostawa_Controller {

    Map<String , Integer> mag = new HashMap<>();
    Map<String , Integer> prze = new HashMap<>();

    /**
     * Funkcja wykonywana przy inicjalizacji sceny dodająca elemeny do comboboxów
     */
    @FXML
    void initialize()
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
            ResultSet rs = PSQL.c.createStatement().executeQuery("SELECT produkt_id, nazwa FROM project.produkt");
            while(rs.next()){
                  prze.put(rs.getString(2),rs.getInt(1));
                  items.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        set_cbox(przedmiot,items);

        ObservableList<String> items2 = FXCollections.observableArrayList();
        try {
            ResultSet rs = PSQL.c.createStatement().executeQuery("SELECT magazyn_id, nazwa FROM project.magazyn");
            while(rs.next()){
                mag.put(rs.getString(2),rs.getInt(1));
                items2.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        set_cbox(magazyn,items2);
    }

    /**
     * Ustawiene elemntów comboboxa
     * @param cbox combobox
     * @param items listaelementów
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
    private TextField ilosc;

    @FXML
    private ComboBox<String> magazyn;

    @FXML
    private ComboBox<String> przedmiot;

    /**
     * Funkcja dodająca przedmioty do magazynu
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        try {
            String query="INSERT INTO project.produkty_w_magazynie (produkt_id, magazyn_id, ilosc) VALUES ("+prze.get(przedmiot.getValue())+","+mag.get(magazyn.getValue())+","+ilosc.getText()+")";
            PSQL.c.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            try {
                String query="UPDATE project.produkty_w_magazynie SET ilosc=ilosc+"+ilosc.getText()+" WHERE produkt_id = "+prze.get(przedmiot.getValue())+" AND magazyn_id = "+mag.get(magazyn.getValue());
                PSQL.c.prepareStatement(query).executeUpdate();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
//            e.printStackTrace();
        }

        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }

}
