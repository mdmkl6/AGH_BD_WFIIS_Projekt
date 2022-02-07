package project.project_v1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * Kontroler sceny buy
 */
public class Buy_Controller {

    public static int n=1;
    public static boolean set=false;

    @FXML
    private Label val;

    /**
     * Funkcja wykonywana przy tworzeniu sceny ustawiająca liczbe kupwanych przedmitów
     */
    @FXML
    void initialize()
    {
        val.setText(String.valueOf(n));
    }

    /**
     * Funkcja dodająca wybrany przedmiot do koszyka
     * @param event
     */
    @FXML
    void Dodaj(ActionEvent event) {
        if(set)
        {
            Basket.koszyk.replace(Basket.id,n);
        }
        else {
            if (Basket.koszyk.containsKey(Basket.id)) {
                Basket.koszyk.replace(Basket.id, Basket.koszyk.get(Basket.id) + n);
            } else {
                Basket.koszyk.put(Basket.id, n);
            }
        }
        ((Stage)((Node)(event.getSource())).getScene().getWindow()).close();
    }

    /**
     * Funkcja zmniejszająca liczbe przedmitów do zakupu
     * @param event
     */
    @FXML
    void minus(ActionEvent event) {
        if(n>1)
            n--;
        val.setText(String.valueOf(n));
    }

    /**
     * Funkcja zwiększająca liczbe przedmitów do zakupu
     * @param event
     */
    @FXML
    void plus(ActionEvent event) {
        if(n<Basket.max)
            n++;
        val.setText(String.valueOf(n));
    }

}
