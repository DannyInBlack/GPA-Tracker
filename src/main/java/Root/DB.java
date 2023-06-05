package Root;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection to the MySQL Database using mysql-connector
 */
abstract class DB {
    protected final String DB_URL = "jdbc:mysql://localhost/test_db";
    protected final String USERNAME = "root";
    protected final String PASSWORD = "";

    protected Connection getDBConn() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
}
