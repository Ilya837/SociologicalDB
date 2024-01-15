package SQL.repository;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.sql.SQLException;

public class MednikTable extends BaseTable implements TableOperations {

    public MednikTable() throws SQLException {
        super("mednik");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30)," +
                "index_of_test INTEGER," +
                "question_id INTEGER," +
                "answer CHARACTER VARYING(30)," +
                "originality DOUBLE PRECISION," +
                "PRIMARY KEY(respondent_id, index_of_test, question_id) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {

        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");

        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (question_id) REFERENCES mednik_questions(question_id)",
                "Cоздан внешний ключ " + tableName + ".question_id -> mednik_questions.question_id");
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
                    String str[] = new String[18];
                    int k = 1;
                    for (int i = 0; i<18; i++)
                    {
                        str[i] = "'" + nextRecord[0] + "', " + TestIndex + ", " + i + ", '" + nextRecord[k] + "', " + nextRecord[k+20];
                        k = k+1;
                        super.executeSqlStatement("INSERT INTO " + tableName +
                                " VALUES ( " + str[i] + " );");

                        if (WriteInfo)
                            System.out.println("В " + tableName + " Добавлена запись " + str[i]);

                    }
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
