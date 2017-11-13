package be.howest.hayk.moviedb;

/**
 *
 * @author hayk
 */
public class DBException extends RuntimeException {
    
    public DBException(Throwable cause) {
        super("Database connection error", cause);
    }
    
}
