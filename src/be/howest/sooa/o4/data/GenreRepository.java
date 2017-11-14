package be.howest.sooa.o4.data;

import be.howest.sooa.o4.domain.Genre;
import be.howest.sooa.o4.domain.Movie;
import be.howest.sooa.o4.moviedb.DBException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hayk
 */
public class GenreRepository extends AbstractRepository {
    
    private static final String SQL = "SELECT * FROM genre";
    private static final String SQL_FIND_ALL = SQL + " ORDER BY name";
    
    public List<Genre> findAll() {
        List<Genre> entities = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL)) {
            while (resultSet.next()) {
                entities.add(build(resultSet));
            }
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
        return entities;
    }
    
    public void save(Genre genre) {
        // TODO
        System.out.println("Saving genre " + genre);
    }
    
    public void update(Genre genre) {
        // TODO
        System.out.println("Updating genre " + genre);
    }
    
    public void delete(Genre genre) {
        // TODO
        System.out.println("Deleting genre " + genre);
    }

    private Genre build(ResultSet resultSet) throws SQLException {
        return new Genre(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
    
}
