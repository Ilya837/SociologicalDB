package SQL.repository;

import java.sql.SQLException;

public class WilliamsTable extends SQL.repository.BaseTable implements TableOperations {

    public WilliamsTable() throws SQLException {
        super("williams");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id CHARACTER VARYING(30)," +
                "index_of_test INTEGER," +
                "inquisitiveness INTEGER," +
                "imagination INTEGER," +
                "complexity INTEGER," +
                "risk_appetite INTEGER," +
                "points_sum INTEGER," +
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
