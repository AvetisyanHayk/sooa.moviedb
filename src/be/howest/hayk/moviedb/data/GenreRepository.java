package be.howest.hayk.moviedb.data;

import be.howest.hayk.domain.Genre;
import be.howest.hayk.moviedb.DBException;

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

    private Genre build(ResultSet resultSet) throws SQLException {
        return new Genre(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
    
}
