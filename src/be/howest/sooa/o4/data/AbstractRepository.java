package be.howest.sooa.o4.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hayk
 */
public abstract class AbstractRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/sooa_o4";
    private static final String USER = "student";
    private static String password;

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, password);
    }
    
    public static void connect(String password) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, USER, password)) {
            setPassword(password);
        }
    }
    
    public static void setPassword(String enteredPassword) {
        password = enteredPassword;
    }
}
