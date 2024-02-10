package SQL;

import SQL.repository.SchwartzTable;
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
    ObservableList<String> VariationTablesList = FXCollections.observableArrayList("Вильямс", "Шварц");
    ObservableList<String> WilliamFieldsList = FXCollections.observableArrayList("Любознательность", "Воображение", "Сложность", "Склонность к риску");
    ObservableList<String> SchwartzFieldsList = FXCollections.observableArrayList("Безопасность", "Конформность", "Традиция", "Самостоятельность", "Риск–новизна", "Гедонизм", "Достижение","Власть–богатство","Благожелательность", "Универсализм");

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
                    case("Шварц"): {
                        fieldBox.setItems(SchwartzFieldsList);
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


    private ArrayList<Variation> GetVariationSeries(String tableName){

        ArrayList<Variation> result = new ArrayList<>();

        switch(tableName){

            case ("Вильямс"):{
                String column = "";

                switch (fieldBox.getValue()){
                    case "Любознательность": column="inquisitiveness"; break;
                    case "Воображение": column="imagination"; break;
                    case "Сложность": column="complexity"; break;
                    case "Склонность к риску": column="risk_appetite"; break;
                }
                try {
                    WilliamsTable williamsTable = new WilliamsTable();

                    result = williamsTable.GetVariation(column);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case("Шварц"):{
                String column = "";

                switch (fieldBox.getValue()){
                    case "Безопасность": column="safety"; break;
                    case "Конформность": column="comfort"; break;
                    case "Традиция": column="tradition"; break;
                    case "Самостоятельность": column="independence"; break;
                    case "Риск–новизна": column="risk_novelty"; break;
                    case "Гедонизм": column="hedonism"; break;
                    case "Власть–богатство": column="power"; break;
                    case "Достижение": column="achievement"; break;
                    case "Благожелательность": column="benevolence"; break;
                    case "Универсализм": column="universalism"; break;
                }
                try {
                    SchwartzTable schwartzTable = new SchwartzTable();

                    result = schwartzTable.GetVariation(column);

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
                ArrayList<Variation> result = GetVariationSeries(tableBox.getValue());

                table.getColumns().clear();

                TableColumn<Variation,String> intervalCol = new TableColumn<>("Interval");
                intervalCol.setCellValueFactory(new PropertyValueFactory<>("interval"));

                TableColumn<Variation,String> countCol = new TableColumn<>("Count");
                countCol.setCellValueFactory(new PropertyValueFactory<>("score"));

                table.getColumns().add(intervalCol);
                table.getColumns().add(countCol);

                for(int i = 0; i< result.size();i++){
                    table.getItems().add(result.get(i));
                }

                break;
            }
        }

        table.setVisible(true);

    }
}
