package be.howest.sooa.o4.moviedb;

/**
 *
 * @author hayk
 */
public class DBException extends RuntimeException {
    
    public DBException(Throwable cause) {
        super("Database connection error", cause);
    }
    
}
