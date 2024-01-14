package SQL.repository;

import java.sql.SQLException;

public class TriandisTable extends BaseTable implements TableOperations {

    public TriandisTable() throws SQLException {
        super("triandis");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30)," +
                "index_of_test INTEGER," +
                "figure INTEGER," +
                "level_name CHARACTER VARYING(30)," +
                "PRIMARY KEY(respondent_id, index_of_test) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");
    }

    @Override
    public void WriteInTable(String filePath) throws SQLException {

    }
}
