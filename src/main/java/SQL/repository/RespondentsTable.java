package SQL.repository;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.sql.SQLException;

public class RespondentsTable extends BaseTable implements TableOperations {

    public RespondentsTable() throws SQLException {
        super("respondents");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30) PRIMARY KEY);", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
    }

    @Override
    public void WriteInTable(String filePath) throws SQLException {

        try {
            FileReader fileReader = new FileReader(filePath);

            CSVParser parser = new  CSVParserBuilder().
                                    withSeparator(';').
                                    build();

            CSVReader csvReader = new CSVReaderBuilder(fileReader).
                                            withCSVParser(parser).
                                            build();

            String[] nextRecord;

            while((nextRecord = csvReader.readNext()) != null){
                System.out.println(nextRecord[0]);
            }

            csvReader.close();
            fileReader.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        }
}
