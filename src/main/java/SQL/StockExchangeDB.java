package SQL;

import SQL.model.Schwarrtz;
import SQL.repository.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StockExchangeDB {
    // Блок объявления констант

    public static final String DB_NAME = "sociological_db";
    public static final String DB_PORT = "5432";
    public static final String DB_URL = "jdbc:postgresql://localhost:"+ DB_PORT +"/" + DB_NAME;

    static String username = "postgres";
    static String password = "1234";

    // Таблицы СУБД

    RespondentsTable respondentsTable;
    WilliamsTable williamsTable;
    SchwartzTable schwartzTable;
    TriandisTable triandisTable;
    MednikTable mednikTable;
    MednikQuestionsTable mednikQuestionsTable;
    MednikOriginalityTable mednikOriginalityTable;
    SociologyTable sociologyTable;
    SociologyQuestionsTable sociologyQuestionsTable;



    // Получить новое соединение с БД
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, username, password );
    }

    // Инициализация

    private void  createDB() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:"+ DB_PORT +"/", username, password );

        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(
        "CREATE DATABASE "+ DB_NAME +
            " WITH" +
            " OWNER = " + username +
            " ENCODING = 'UTF8'" +
            " LC_COLLATE = 'Russian_Russia.1251'" +
            " LC_CTYPE = 'Russian_Russia.1251'" +
            " TABLESPACE = pg_default" +
            " CONNECTION LIMIT = -1;"
        ); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД

        connection.close();
        System.out.println("База данных " + DB_NAME + " Была создана");


    }
    public StockExchangeDB() throws SQLException, ClassNotFoundException {


        Connection connection = null;
        boolean haveBase = false;

        try{
            connection = getConnection();
            haveBase = true;
            connection.close();
        }
        catch (SQLException e){
            haveBase = false;

        }

        if(!haveBase){
            createDB();
        }

        // Инициализируем таблицы
        respondentsTable = new RespondentsTable();
        williamsTable = new WilliamsTable();
        schwartzTable = new SchwartzTable();
        triandisTable = new TriandisTable();
        mednikTable = new MednikTable();
        mednikQuestionsTable = new MednikQuestionsTable();
        mednikOriginalityTable = new MednikOriginalityTable();
        sociologyTable = new SociologyTable();
        sociologyQuestionsTable = new SociologyQuestionsTable();

    }

    // Создание всех таблиц и ключей между ними
    public void createTablesAndForeignKeys() throws SQLException {
        respondentsTable.createTable();
        williamsTable.createTable();
        schwartzTable.createTable();
        triandisTable.createTable();
        mednikTable.createTable();
        mednikQuestionsTable.createTable();
        mednikOriginalityTable.createTable();
        sociologyTable.createTable();
        sociologyQuestionsTable.createTable();

        // Создание внешних ключей (связt` между таблицами)
        williamsTable.createForeignKeys();
        schwartzTable.createForeignKeys();
        triandisTable.createForeignKeys();
        mednikTable.createForeignKeys();
        mednikOriginalityTable.createForeignKeys();
        sociologyTable.createForeignKeys();
    }

    public void closeTables(){
        respondentsTable.close();
        williamsTable.close();
        schwartzTable.close();
        triandisTable.close();
        mednikTable.close();
        mednikQuestionsTable.close();
        mednikOriginalityTable.close();
        sociologyTable.close();
        sociologyQuestionsTable.close();
    }

    public void WriteInTables() throws SQLException {
        respondentsTable.WriteInTable("./src/main/java/SQL/CSV/Respondents.csv", ';',false, true);
        respondentsTable.WriteInTable("./src/main/java/SQL/CSV/Williams.csv",';',false, true);
        respondentsTable.WriteInTable("./src/main/java/SQL/CSV/Schwarrtz.csv",';',false, true);
        respondentsTable.WriteInTable("./src/main/java/SQL/CSV/Sociology.csv",';',false, true,true,false);
        respondentsTable.WriteInTable("./src/main/java/SQL/CSV/Mednik.csv",'|',false, true,false,true);


        williamsTable.WriteInTable("./src/main/java/SQL/CSV/Williams.csv",';',1, false, true);
        schwartzTable.WriteInTable("./src/main/java/SQL/CSV/Schwarrtz.csv",';',1, false, true);
        mednikOriginalityTable.WriteInTable("./src/main/java/SQL/CSV/Mednik.csv",'|',1, false, true);
        mednikQuestionsTable.WriteInTable("./src/main/java/SQL/CSV/Mednik.csv",'|',1, false, true);
        mednikTable.WriteInTable("./src/main/java/SQL/CSV/Mednik.csv",'|',1, false, true);
        triandisTable.WriteInTable("./src/main/java/SQL/CSV/Triandis.csv",';',1, false, true);
        sociologyQuestionsTable.WriteInTable("./src/main/java/SQL/CSV/Sociology.csv",';',1, false, true);
        sociologyTable.WriteInTable("./src/main/java/SQL/CSV/Sociology.csv",';',1, false, true);
    }

    public static void main(String[] args) {
        try{
            StockExchangeDB stockExchangeDB = new StockExchangeDB();
            stockExchangeDB.createTablesAndForeignKeys();
            stockExchangeDB.closeTables();

            stockExchangeDB.WriteInTables();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC драйвер для СУБД не найден!");
        }
    }
}
