package project.project_v1;
import java.sql.*;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * Klasa do zażądzania i przechowywania informacji o bazie danych
 */
public class PSQL {
    public static String url="";
    public static String user="";
    public static String pass="";
    public static String userid="";
    public static Connection c = null;
    public static int acces=0;

    /**
     * Zapisanie zalogowanego urzytkownika
     * @param _user login
     * @param _pass haslo
     */
    public static void login(String _user,String _pass)
    {
        user=_user;
        pass=_pass;
    }

    /**
     * Stworzenie połączenie z bazą danych
     * @return prawda jesli udało się stworzyć połącznie fałsz jezeli nie
     */
    public static boolean create_connection()
    {
        try {
            c = DriverManager.getConnection(url, user, pass);
            set_userid();
            return true;
        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Funkcja znajdująca i zpisująca id urzytkownika
     */
    static void set_userid()
    {
        String query="";
        switch (PSQL.acces) {
            case 0 -> query="{call project.u_id_by_mail('"+user+"')}";
            case 1 -> query="{call project.s_id_by_mail('"+user+"')}";
            case 2 -> query="{call project.p_id_by_mail('"+user+"')}";
        }
        try {
            ResultSet rs = c.prepareCall(query).executeQuery();
            if(rs.next())
                userid=rs.getString(1);
            else
                return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Funkcja tworząca połączenie z bazą danych służcze do tworzenia urzytkowników
     * @return
     */
    public static Connection get_create_user_conn()
    {
        try {
            Connection c2= DriverManager.getConnection(url, "role_creator","pass");
            return c2;
        } catch (SQLException se) {
            return null;
        }
    }

    /**
     * Funkcja twożaąca tabele wyjsciową polecenia sql z przyciskami
     * @param tableview tabela
     * @param SQL Wykonywane polecenie sql
     * @param button_name nazwa przycisku
     * @param function funkcja wykonywana przez przycisk przyjmująca ObservableList jako liste z elementami wiersza
     */
    public static void create_table_buttons(TableView tableview, String SQL, String button_name, Function< ObservableList, ObservableList> function){
        tableview.getColumns().clear();
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);

            }
            TableColumn col = new TableColumn(button_name);
            col.setCellFactory(ActionButtonTableCell.<ObservableList>forTableColumn(button_name,function));
            tableview.getColumns().addAll(col);

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                data.add(row);

            }
            tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Funkcja twożaąca tabele wyjsciową polecenia sql z przyciskami dostosowaną do koszyka
     * @param tableview tabela
     * @param SQL Wykonywane polecenie sql
     * @param function funkcja wykonywana przez przycisk przyjmująca ObservableList jako liste z elementami wiersza
     */
    public static void create_table_koszyk(TableView tableview, String SQL, Function< ObservableList, ObservableList> function){
        tableview.getColumns().clear();
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);

            }

            TableColumn col = new TableColumn("Ilosc");
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(5).toString());
                }
            });

            tableview.getColumns().addAll(col);

            col = new TableColumn("Zmien Ilosc");
            col.setCellFactory(ActionButtonTableCell.<ObservableList>forTableColumn("Zmien Ilosc",function));
            tableview.getColumns().addAll(col);
//
//
            col = new TableColumn("USUN");
            col.setCellFactory(ActionButtonTableCell.<ObservableList>forTableColumn("USUN",(ObservableList p) -> {
                Basket.koszyk.remove(p.get(0));
                tableview.getItems().remove(p);
                return p;
            }));
            tableview.getColumns().addAll(col);

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                row.add(String.valueOf(Basket.koszyk.get(rs.getString(1))));
                data.add(row);

            }
            tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Funkcja twożaąca tabele wyjsciową polecenia sql
     * @param tableview tabela
     * @param SQL Wykonywane polecenie sql
     */
    public static void create_table(TableView tableview,String SQL){
        tableview.getColumns().clear();
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);

            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                data.add(row);

            }
            tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Funkcja twożaąca tabele wyjsciową polecenia sql z przyciskami dostosowaną do servisu
     * @param tableview tabela
     * @param SQL Wykonywane polecenie sql
     * @param button_name nazwa przycisku
     * @param function funkcja wykonywana przez przycisk przyjmująca ObservableList jako liste z elementami wiersza
     */
    public static void create_servis_table(TableView tableview, String SQL, String button_name, Function< ObservableList, ObservableList> function){
        tableview.getColumns().clear();
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);

            }
            TableColumn col = new TableColumn(button_name);
            col.setCellFactory(ActionButtonTableCell.<ObservableList>forTableColumn(button_name,function));
            tableview.getColumns().addAll(col);

            col = new TableColumn("HistoriaNapraw");
            col.setCellFactory(ActionButtonTableCell.<ObservableList>forTableColumn("HistoriaNapraw",(ObservableList p) -> {
                String query = "SELECT id_napraw AS \"ID\",servisant_id AS \"ID_Servisanta\",opis FROM project.naprawy Where id="+p.get(0);
                create_table(tableview,query);
                return p;
            }));
            tableview.getColumns().addAll(col);

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                data.add(row);

            }
            tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
