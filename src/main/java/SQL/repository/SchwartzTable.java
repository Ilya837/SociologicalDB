package SQL.repository;

import SQL.Variation;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchwartzTable extends BaseTable implements TableOperations {

    public SchwartzTable() throws SQLException {
        super("schwartz");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30)," +
                "index_of_test INTEGER," +
                "safety DOUBLE PRECISION," +
                "comfort DOUBLE PRECISION," +
                "tradition DOUBLE PRECISION," +
                "independence DOUBLE PRECISION," +
                "risk_novelty DOUBLE PRECISION," +
                "hedonism DOUBLE PRECISION," +
                "achievement DOUBLE PRECISION," +
                "power DOUBLE PRECISION," +
                "benevolence DOUBLE PRECISION," +
                "universalism DOUBLE PRECISION," +
                "level_name CHARACTER VARYING(30)," +
                "PRIMARY KEY(respondent_id, index_of_test) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");
    }

    @Override
    public void WriteInTable(String filePath,char Separator, boolean WriteExpention, boolean WriteInfo) throws SQLException {

        WriteInTable(filePath,Separator,0,WriteExpention,WriteInfo);

    }

    public void WriteInTable(String filePath,char Separator,int TestIndex, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        try {
            FileReader fileReader = new FileReader(filePath);

            CSVParser parser = new CSVParserBuilder().
                    withSeparator(Separator).
                    build();

            CSVReader csvReader = new CSVReaderBuilder(fileReader).
                    withCSVParser(parser).
                    build();

            String[] nextRecord;

            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {

                try {
                    String str = "";

                    str += "'" + nextRecord[0] + "', " + TestIndex;

                    for (int i = 1; i <= 10; i++) {
                        str += ", " + nextRecord[i].replace(',','.');
                    }

                    str += ",'" + nextRecord[11] + "'";

                    super.executeSqlStatement("INSERT INTO " + tableName +
                            " VALUES ( " + str + " );");

                    if (WriteInfo)
                        System.out.println("В " + tableName + " Добавлена запись " + str);

                } catch (Exception e) {
                    if (WriteExpention) System.out.println(e.toString());
                }
            }

            csvReader.close();
            fileReader.close();

        } catch (Exception e) {
            if (WriteExpention) System.out.println(e.toString());
        }
    }

    public ArrayList<Variation> GetVariation(String column) {
        ArrayList<Variation> list = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "SELECT MIN(" + column + "), MAX(" + column + ") FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            double minValue = resultSet.getInt(1);
            double maxValue = resultSet.getInt(2);

            int k = (int) (1 + Math.log(maxValue - minValue + 1) / Math.log(2));
            double h = (maxValue - minValue) / k;

            sql = "SELECT COUNT(*) FROM " +  tableName + " WHERE " + column + " >= ? AND " + column + " < ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < k; i++) {
                double lowerBound = minValue + i * h;
                double upperBound = lowerBound + h;

                preparedStatement.setDouble(1, lowerBound);
                preparedStatement.setDouble(2, upperBound);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int frequency = resultSet.getInt(1);

                list.add(new Variation("Интервал [" + Math.floor(lowerBound * 100.0) / 100.0 + "-" + Math.floor(upperBound * 100.0) / 100.0 + ")",String.valueOf(frequency)));

                System.out.println("Интервал [" + lowerBound + "-" + upperBound + "): " + frequency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
