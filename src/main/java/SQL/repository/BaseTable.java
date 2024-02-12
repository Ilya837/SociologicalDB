package SQL.repository;

import SQL.StockExchangeDB;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public abstract class BaseTable implements Closeable {
    Connection connection;  // JDBC-соединение для работы с таблицей
    String tableName;       // Имя таблицы

    BaseTable(String tableName) throws SQLException { // Для реальной таблицы передадим в конструктор её имя
        this.tableName = tableName;
        this.connection = StockExchangeDB.getConnection(); // Установим соединение с СУБД для дальнейшей работы
    }

    // Закрытие
    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия SQL соединения!");
        }
    }

    // Выполнить SQL команду без параметров в СУБД, по завершению выдать сообщение в консоль
    void executeSqlStatement(String sql, String description) throws SQLException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        if (description != null)
            System.out.println(description);
    };

    public void executeSqlStatement(String sql) throws SQLException {
        executeSqlStatement(sql, null);
    };

    public String getTableName() {
        return tableName;
    }

    public ArrayList<ArrayList<String>> executeSqlPreparedStatement(String sql, int collumCount, String description) throws SQLException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        PreparedStatement statement = connection.prepareStatement(sql);  // Создаем statement для выполнения sql-команд
        ResultSet rs = statement.executeQuery();

        ArrayList<ArrayList<String>> result = new ArrayList<>() ;

        while (rs.next()){
            result.add(new ArrayList<String>());
            for(int i = 1; i <= collumCount; i++){
                result.get(rs.getRow() - 1)
                        .add( rs.getString(i) );
            }
        }

        rs.close();
        statement.close();      // Закрываем statement для фиксации изменений в СУБД

        if (description != null)
            System.out.println(description);

        return result;

    };

    public ArrayList<ArrayList<String>>  executeSqlPreparedStatement(String sql, int collumCount) throws SQLException {
        return executeSqlPreparedStatement(sql,collumCount, null);
    };


    // Активизация соединения с СУБД, если оно не активно.
    void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = StockExchangeDB.getConnection();
        }
    }
}
