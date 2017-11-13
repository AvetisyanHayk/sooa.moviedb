package be.howest.sooa.o4.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hayk
 */
abstract class AbstractRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/sooa_o4";
    private static final String USER = "student";
    private static final String PASSWORD = "student";
    
    private static Connection connection;
    
    protected static Connection getConnection() throws SQLException {
        if (connection == null) {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
