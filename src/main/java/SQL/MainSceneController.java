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
    private void initialize() throws SQLException {
        tableTypeBox.setItems(tableTypeList);
        fieldBox.setVisible(false);
        tableBox.setVisible(false);
        tableLabel.setVisible(false);
        fieldLabel.setVisible(false);
        button.setVisible(false);
        table.setVisible(false);
        SQL.StockExchangeDB.initialize();
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
        button.setVisible(false);


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




    @FXML
    private void OnButtonClick(){

        table.getColumns().clear();
        table.getItems().clear();

        switch (tableTypeBox.getValue()){
            case("Вариационный ряд"):{
                ArrayList<Variation> result = SQL.StockExchangeDB.GetVariationSeries(tableBox.getValue(), fieldBox.getValue());

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
