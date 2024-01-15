package SQL.repository;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.sql.SQLException;

public class MednikOriginalityTable extends BaseTable implements TableOperations {

    public MednikOriginalityTable() throws SQLException {
        super("mednik_originality");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30)," +
                "index_of_test INTEGER," +
                "originality DOUBLE PRECISION," +
                "level_name CHARACTER VARYING(30)," +
                "PRIMARY KEY(respondent_id, index_of_test) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");
    }

    @Override
    public void WriteInTable(String filePath, boolean WriteExpention, boolean WriteInfo) throws SQLException {

        WriteInTable(filePath,0,WriteExpention,WriteInfo);

    }

    public void WriteInTable(String filePath,int TestIndex, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        try {
            FileReader fileReader = new FileReader(filePath);

            CSVParser parser = new CSVParserBuilder().
                    withSeparator(';').
                    build();

            CSVReader csvReader = new CSVReaderBuilder(fileReader).
                    withCSVParser(parser).
                    build();

            String[] nextRecord;

            csvReader.readNext();
            csvReader.readNext();
            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {

                try {
                    String str = "";

                    str += "'" + nextRecord[0] + "', " + TestIndex + ", " + nextRecord[19] + ", '" + nextRecord[20] + "'";

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
}
