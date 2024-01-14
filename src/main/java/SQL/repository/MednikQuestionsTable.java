package SQL.repository;

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
    public void WriteInTable(String filePath, boolean WriteExpention, boolean WriteInfo) throws SQLException {

    }
}
