package SQL.repository;

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

    }
}
