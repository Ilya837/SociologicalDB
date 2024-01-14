package SQL.repository;

import java.sql.SQLException;

public class MednikOriginalityTable extends BaseTable implements TableOperations {

    public MednikOriginalityTable() throws SQLException {
        super("mednik_originality");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "respondent_id INTEGER," +
                "index_of_test INTEGER," +
                "originality DOUBLE PRECISION," +
                "level_name CHARACTER VARYING(30)," +
                "PRIMARY KEY(respondent_id, index_of_test) );", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE "+ tableName +" ADD FOREIGN KEY (respondent_id) REFERENCES respondents(respondent_id)",
                "Cоздан внешний ключ " + tableName + ".respondent_id -> respondents.respondent_id");
    }
}
