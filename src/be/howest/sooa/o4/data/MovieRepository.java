package be.howest.sooa.o4.data;

import be.howest.sooa.o4.domain.Genre;
import be.howest.sooa.o4.domain.Movie;
import be.howest.sooa.o4.moviedb.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hayk
 */
public class MovieRepository extends AbstractRepository {
    
    private static final String SQL = "SELECT * FROM movie";
    private static final String SQL_READ = SQL + " WHERE id = ?";
    private static final String SQL_FIND_BY_GENRE = SQL + " WHERE genre_id = ?"
            + " ORDER BY year, title, stars";
    
    public Movie read(long id) {
        try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_READ)) {
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return build(resultSet);
                }
            }
            return null;
        } catch(SQLException ex) {
            throw new DBException(ex);
        }
    }
    
    public List<Movie> findByGenre(Genre genre) {
        List<Movie> entities = new ArrayList<>();
        try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_GENRE)) {
            statement.setLong(1, genre.getId());
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(build(resultSet));
                }
            }
        } catch(SQLException ex) {
            throw new DBException(ex);
        }
        return entities;
    }
    
    public void save(Movie movie) {
        // TODO
        System.out.println("Saving movie " + movie);
    }
    
    public void update(Movie movie) {
        // TODO
        System.out.println("Updating movie " + movie);
    }
    
    public void delete(Movie movie) {
        // TODO
        System.out.println("Deleting movie " + movie);
    }
    
    private Movie build(ResultSet resultSet) throws SQLException {
        return new Movie(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getInt("year"),
                resultSet.getInt("stars")
        );
    }
}
