package SQL.repository;

import java.sql.SQLException;

public class RespondentsTable extends BaseTable implements TableOperations {

    public RespondentsTable() throws SQLException {
        super("respondents");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id INTEGER PRIMARY KEY);", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
    }
}
