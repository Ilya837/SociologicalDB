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

public class WilliamsTable extends SQL.repository.BaseTable implements TableOperations {

    public WilliamsTable() throws SQLException {
        super("williams");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30)," +
                "index_of_test INTEGER," +
                "inquisitiveness INTEGER," +
                "imagination INTEGER," +
                "complexity INTEGER," +
                "risk_appetite INTEGER," +
                "points_sum INTEGER," +
                "level_name CHARACTER VARYING(30)," +
                "PRIMARY KEY(respondent_id, index_of_test) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE " + tableName + " ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");
    }

    @Override
    public void WriteInTable(String filePath, char Separator, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        WriteInTable(filePath, Separator, 0, WriteExpention, WriteInfo);
    }

    public void WriteInTable(String filePath, char Separator, int TestIndex, boolean WriteExpention, boolean WriteInfo) throws SQLException {
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

                    for (int i = 1; i <= 5; i++) {
                        str += ", " + nextRecord[i];
                    }

                    str += ",'" + nextRecord[6] + "'";

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

    public int[] Variation(int[] borders, String column) {
        int[] counts = new int[5];
        String sql = "SELECT " +
                "  COUNT(CASE WHEN " + column + " < ? THEN 1 END) AS count_less_than_x, " +
                "  COUNT(CASE WHEN " + column + " >= ? AND " + column + " < ? THEN 1 END) AS count_between_x_and_y, " +
                "  COUNT(CASE WHEN " + column + " >= ? AND " + column + " < ? THEN 1 END) AS count_between_y_and_z, " +
                "  COUNT(CASE WHEN " + column + " >= ? AND " + column + " < ? THEN 1 END) AS count_between_z_and_w, " +
                "  COUNT(CASE WHEN " + column + " >= ? THEN 1 END) AS count_greater_than_z " +
                "FROM williams";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, borders[0]);
            statement.setDouble(2, borders[0]);
            statement.setDouble(3, borders[1]);
            statement.setDouble(4, borders[1]);
            statement.setDouble(5, borders[2]);
            statement.setDouble(6, borders[2]);
            statement.setDouble(7, borders[3]);
            statement.setDouble(8, borders[3]);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int countLessThanX = resultSet.getInt("count_less_than_x");
                    int countBetweenXAndY = resultSet.getInt("count_between_x_and_y");
                    int countBetweenYAndZ = resultSet.getInt("count_between_y_and_z");
                    int countBetweenZAndW = resultSet.getInt("count_between_z_and_w");
                    int countGreaterThanZ = resultSet.getInt("count_greater_than_z");

                    counts[0] = resultSet.getInt("count_less_than_x");
                    counts[1] = resultSet.getInt("count_between_x_and_y");
                    counts[2] = resultSet.getInt("count_between_y_and_z");
                    counts[3] = resultSet.getInt("count_between_z_and_w");
                    counts[4] = resultSet.getInt("count_greater_than_z");
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return counts;
    }

    public ArrayList<Variation> GetVariation(String column) {
        ArrayList<Variation> list = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "SELECT MIN(" + column + "), MAX(" + column + ") FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int minValue = resultSet.getInt(1);
            int maxValue = resultSet.getInt(2);

            int k = (int) (1 + Math.log(maxValue - minValue + 1) / Math.log(2));
            int h = (maxValue - minValue) / k;

            sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + column + " >= ? AND " + column + " < ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < k; i++) {
                int lowerBound = minValue + i * h;
                int upperBound = lowerBound + h;

                preparedStatement.setInt(1, lowerBound);
                preparedStatement.setInt(2, upperBound);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int frequency = resultSet.getInt(1);

                list.add(new Variation("Интервал [" + lowerBound + "-" + upperBound + ")",String.valueOf(frequency)));

                System.out.println("Интервал [" + lowerBound + "-" + upperBound + "): " + frequency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
