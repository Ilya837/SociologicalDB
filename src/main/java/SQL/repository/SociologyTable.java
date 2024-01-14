package SQL.repository;

import java.sql.SQLException;

public class SociologyTable extends BaseTable implements TableOperations {

    public SociologyTable() throws SQLException {
        super("sociology ");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id INTEGER," +
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
}
