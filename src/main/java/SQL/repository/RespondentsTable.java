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
    public void WriteInTable(String filePath,char Separator, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        WriteInTable(filePath, Separator,WriteExpention,WriteInfo,false);
    }

    public void WriteInTable(String filePath,char Separator, boolean WriteExpention, boolean WriteInfo, boolean isSociology, boolean isMednik) throws SQLException {

        try {
            FileReader fileReader = new FileReader(filePath);

            CSVParser parser = new  CSVParserBuilder().
                    withSeparator(Separator).
                    build();

            CSVReader csvReader = new CSVReaderBuilder(fileReader).
                    withCSVParser(parser).
                    build();

            String[] nextRecord;

            csvReader.readNext();

            if(isSociology || isMednik){
                csvReader.readNext();
                csvReader.readNext();
            }

            while((nextRecord = csvReader.readNext()) != null){

                try {

                    super.executeSqlStatement("INSERT INTO " + tableName +
                            " VALUES ( '" + nextRecord[0] + "' );");

                    if(WriteInfo)
                        System.out.println("В " + tableName + " Добавлена запись " + nextRecord[0]);

                }

                catch (Exception e){ if(WriteExpention) System.out.println(e.toString());}

            }

            csvReader.close();
            fileReader.close();

        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
