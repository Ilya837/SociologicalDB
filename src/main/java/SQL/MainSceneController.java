package SQL;

import SQL.repository.WilliamsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainSceneController {

    ObservableList<String> tableTypeList = FXCollections.observableArrayList("Вариационный ряд", "Корреляционная таблица");
    ObservableList<String> correlationTablesList = FXCollections.observableArrayList("Вильямс");
    ObservableList<String> VariationTablesList = FXCollections.observableArrayList("Вильямс");
    ObservableList<String> WilliamFieldsList = FXCollections.observableArrayList("Любознательность", "Воображение", "Сложность", "Склонность к риску");


    @FXML
    private Label tableTypeLabel;

    @FXML
    private Label tableLabel;

    @FXML
    private Label fieldLabel;

    @FXML
    private ComboBox<String> tableTypeBox;

    @FXML
    private ComboBox<String> fieldBox;

    @FXML
    private ComboBox<String> tableBox;

    @FXML
    private Button button;

    @FXML
    TableView table;

    @FXML
    private void initialize(){
        tableTypeBox.setItems(tableTypeList);
        fieldBox.setVisible(false);
        tableBox.setVisible(false);
        tableLabel.setVisible(false);
        fieldLabel.setVisible(false);
        button.setVisible(false);
        table.setVisible(false);

    }

    @FXML
    private void OnEnteredTableType(){

        tableBox.setValue("");

        fieldLabel.setVisible(false);
        fieldBox.setVisible(false);
        button.setVisible(false);
        table.setVisible(false);

        switch (tableTypeBox.getValue()){
            case ("Вариационный ряд"):{ tableBox.setItems(VariationTablesList); break;}
            case ("Корреляционная таблица"): {tableBox.setItems(correlationTablesList); break;}
        }

        tableLabel.setVisible(true);
        tableBox.setVisible(true);
    }

    @FXML
    private void OnEnteredTable(){

        fieldBox.setValue("");
        table.setVisible(false);
        fieldLabel.setVisible(false);
        fieldBox.setVisible(false);


        switch (tableTypeBox.getValue()){
            case ("Вариационный ряд"): {

                switch (tableBox.getValue()){
                    case("Вильямс"): {
                        fieldBox.setItems(WilliamFieldsList);
                        break;
                    }
                }

                fieldLabel.setVisible(true);
                fieldBox.setVisible(true);
                break;
            }
            case ("Корреляционная таблица"): {
                button.setVisible(true);
                break;
            }

        }

    }

    @FXML
    private void OnEnteredField(){

        table.setVisible(false);
        button.setVisible(true);

    }


    private ArrayList<String> GetVariationSeries(String tableName){

        ArrayList<String> result = new ArrayList<>();

        switch(tableName){

            case ("Вильямс"):{
                String column = "";
                int[] bords = { 0, 6, 12, 18 };

                switch (fieldBox.getValue()){
                    case "Любознательность": column="inquisitiveness"; break;
                    case "Воображение": column="imagination"; break;
                    case "Сложность": column="complexity"; break;
                    case "Склонность к риску": column="risk_appetite"; break;
                }

                String sql = "SELECT " +
                        "  COUNT(CASE WHEN " + column + " < " + bords[0] + " THEN 1 END) AS count_less_than_x, " +
                        "  COUNT(CASE WHEN " + column + " >= " + bords[0] + " AND " + column + " < " + bords[1] + " THEN 1 END) AS count_between_x_and_y, " +
                        "  COUNT(CASE WHEN " + column + " >= " + bords[1] + " AND " + column + " < " + bords[2] + " THEN 1 END) AS count_between_y_and_z, " +
                        "  COUNT(CASE WHEN " + column + " >= " + bords[2] + " AND " + column + " < " + bords[3] + " THEN 1 END) AS count_between_z_and_w, " +
                        "  COUNT(CASE WHEN " + column + " >= " + bords[3] + " THEN 1 END) AS count_greater_than_z " +
                        "FROM williams";


                try {
                    WilliamsTable williamsTable = new WilliamsTable();
                    ArrayList<ArrayList<String>> answer = williamsTable.executeSqlPreparedStatement(sql,5);


                    result = answer.get(0); ;

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                break;
            }

        }

        return result;

    }

    @FXML
    private void OnButtonClick(){

        table.getColumns().clear();
        table.getItems().clear();

        switch (tableTypeBox.getValue()){
            case("Вариационный ряд"):{
                ArrayList<String> result = GetVariationSeries(tableBox.getValue());


                switch (tableBox.getValue()){
                    case ("Вильямс"):{
                        int[] bords = { 0, 6, 12, 18 };
                        String[] intervals = {"Меньше чем " + bords[0], "От " + bords[0] + " до " + (bords[1]-1),
                                "От " + bords[1] + " до " + (bords[2]-1), "От " + bords[2] + " до " + (bords[3]-1),
                                "Больше чем " + bords[3]};


                        table.getColumns().clear();

                        ArrayList<Variation> variations = new ArrayList<>();

                        for(int i = 0; i< intervals.length; i++){
                            variations.add(new Variation(intervals[i], result.get(i)));
                        }

                        TableColumn<Variation,String> intervalCol = new TableColumn<>("Interval");
                        intervalCol.setCellValueFactory(new PropertyValueFactory<>("interval"));

                        TableColumn<Variation,String> countCol = new TableColumn<>("Count");
                        countCol.setCellValueFactory(new PropertyValueFactory<>("score"));

                        table.getColumns().add(intervalCol);
                        table.getColumns().add(countCol);

                        for(int i = 0; i< variations.size();i++){
                            table.getItems().add(variations.get(i));
                        }
                        break;


                    }
                }

                break;
            }
        }

        table.setVisible(true);

    }
}
