package SQL.repository;

import java.sql.SQLException;

public class SchwartzTable extends BaseTable implements TableOperations {

    public SchwartzTable() throws SQLException {
        super("schwartz");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id INTEGER," +
                "index_of_test INTEGER," +
                "safety DOUBLE PRECISION," +
                "comfort DOUBLE PRECISION," +
                "tradition DOUBLE PRECISION," +
                "independence DOUBLE PRECISION," +
                "risk_novelty DOUBLE PRECISION," +
                "hedonism DOUBLE PRECISION," +
                "achievement DOUBLE PRECISION," +
                "power DOUBLE PRECISION," +
                "benevolence DOUBLE PRECISION," +
                "universalism DOUBLE PRECISION," +
                "level_name CHARACTER VARYING(30)," +
                "PRIMARY KEY(respondent_id, index_of_test) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");
    }
}
