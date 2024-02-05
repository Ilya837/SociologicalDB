package SQL.repository;

import java.sql.SQLException;

// Операции с таблицами
public interface TableOperations {
    void createTable() throws SQLException; // создание таблицы
    void createForeignKeys() throws SQLException; // создание связей между таблицами

    void WriteInTable(String filePath,char Separator, boolean WriteExpention, boolean WriteInfo) throws SQLException;


}
