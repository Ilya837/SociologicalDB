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

public class SociologyQuestionsTable extends BaseTable implements TableOperations {

    public SociologyQuestionsTable() throws SQLException {
        super("sociology_questions");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "question_id CHARACTER VARYING(30) PRIMARY KEY," +
                "specification  CHARACTER VARYING(255))", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
    }

    @Override
    public void WriteInTable(String filePath,char Separator, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        WriteInTable(filePath,Separator,0,WriteExpention,WriteInfo);
    }

    public void WriteInTable(String filePath,char Separator, int TestIndex, boolean WriteExpention, boolean WriteInfo) throws SQLException {
        try {
            List<String> questionTexts = new ArrayList<>();
            List<String> questionNumbers = new ArrayList<>();

            FileReader fileReader = new FileReader(filePath);

            CSVParser parser = new CSVParserBuilder().
                    withSeparator(Separator).
                    build();

            CSVReader csvReader = new CSVReaderBuilder(fileReader).
                    withCSVParser(parser).
                    build();

            String[] nextRecord;

            csvReader.readNext();

            nextRecord = csvReader.readNext();

            for(int i=5; i< nextRecord.length; i++)
            {
                if (i!=9 && i!=10)
                    questionTexts.add(nextRecord[i]);
            }

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

            for (int i = 0; i < questionTexts.size(); i++) {
                String questionNumber = questionNumbers.get(i);
                String questionText = questionTexts.get(i);

                try {
                    String str = "'" + questionNumber + "', '" + questionText + "'";
                    super.executeSqlStatement("INSERT INTO " + tableName + " VALUES ( " + str + " );");

                    if (WriteInfo)
                        System.out.println("В " + tableName + " Добавлена запись " + str);
                } catch (Exception e) {
                    if (WriteExpention) System.out.println(e.toString());
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
