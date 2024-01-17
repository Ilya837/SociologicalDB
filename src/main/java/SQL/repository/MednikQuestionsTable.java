package SQL.repository;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.sql.SQLException;

public class MednikQuestionsTable extends BaseTable implements TableOperations {

    public MednikQuestionsTable() throws SQLException {
        super("mednik_questions");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "question_id INTEGER PRIMARY KEY," +
                "specification  CHARACTER VARYING(30) );" , "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
    }

    @Override
    public void WriteInTable(String filePath ,char Separator, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        WriteInTable(filePath, Separator, 0,WriteExpention,WriteInfo);
    }

    public void WriteInTable(String filePath ,char Separator, int TestIndex, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        try {
            FileReader fileReader = new FileReader(filePath);

            CSVParser parser = new CSVParserBuilder().
                    withSeparator(Separator).
                    build();

            CSVReader csvReader = new CSVReaderBuilder(fileReader).
                    withCSVParser(parser).
                    build();

            String[] nextRecord;
            String[] str = new String[18];
            int k = 1;
            try {
                nextRecord = csvReader.readNext();
                for (int i = 0; i < 18; i++) {
                    str[i] = i + ", '" + nextRecord[k] + "; ";
                    k = k+2;
                }
                k = 1;
                nextRecord = csvReader.readNext();
                for (int i = 0; i < 18; i++) {
                    str[i] = str[i] + nextRecord[k] + "; ";
                    k = k+2;
                }
                nextRecord = csvReader.readNext();
                for (int i = 0; i < 18; i++) {
                    str[i] = str[i] + nextRecord[i+1] + "'";
                    super.executeSqlStatement("INSERT INTO " + tableName +
                            " VALUES ( " + str[i] + " );");
                    if (WriteInfo)
                        System.out.println("В " + tableName + " Добавлена запись " + str[i]);
                }

            }
            catch (Exception e){ if(WriteExpention) System.out.println(e.toString());}

            csvReader.close();
            fileReader.close();

        }
        catch (Exception e){
            if (WriteExpention) System.out.println(e.toString());
        }
    }

}
