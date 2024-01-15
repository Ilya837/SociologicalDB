package SQL.repository;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SociologyTable extends BaseTable implements TableOperations {

    public SociologyTable() throws SQLException {
        super("sociology");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30)," +
                "index_of_test INTEGER," +
                "question_id CHARACTER VARYING(30)," +
                "answer INTEGER," +
                "PRIMARY KEY(respondent_id, index_of_test, question_id) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");

        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (question_id) REFERENCES sociology_questions(question_id)",
                "Cоздан внешний ключ " + tableName + ".question_id -> sociology_questions.question_id");
    }

    @Override
    public void WriteInTable(String filePath, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        WriteInTable(filePath,0,WriteExpention,WriteInfo);
    }

    public void WriteInTable(String filePath, int TestIndex, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        try {
            List<String> questionNumbers = new ArrayList<>();

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

            nextRecord = csvReader.readNext();

            for(int i=5; i< nextRecord.length; i++)
            {
                if (i>-5 && i<9){
                    String tmp = "0." + (i-4);
                    questionNumbers.add(tmp);
                }
                else if (i>=11) {
                    questionNumbers.add(nextRecord[i]);
                }
            }

            while ((nextRecord = csvReader.readNext()) != null) {
                try {
                    int recordIndex = 5;

                    for (int i = 0; i < questionNumbers.size(); i++) {
                        String str = "";

                        while (recordIndex == 9 || recordIndex == 10) {
                            recordIndex++;
                        }

                        str += "'" + nextRecord[0] + "', " + TestIndex + ", '" + questionNumbers.get(i) + "', ";

                        if (nextRecord[recordIndex] == "" || nextRecord[recordIndex] == "#Н/Д") {
                            str += "NULL";
                        } else {
                            String[] multipleAnswers = nextRecord[recordIndex].split(" ");

                            int parsedAnswer = Integer.parseInt(multipleAnswers[0]);
                            str += parsedAnswer;
                        }

                        recordIndex++;

                        super.executeSqlStatement("INSERT INTO " + tableName +
                                " VALUES ( " + str + " );");

                        if (WriteInfo)
                            System.out.println("В " + tableName + " Добавлена запись " + str);
                        }
                } catch (Exception e) {
                    if (WriteExpention) System.out.println(e.toString());
                    System.out.println(nextRecord[0]);
                }
            }

            csvReader.close();
            fileReader.close();

        }
        catch (Exception e){
            if (WriteExpention) System.out.println(e.toString());
        }
    }
}
