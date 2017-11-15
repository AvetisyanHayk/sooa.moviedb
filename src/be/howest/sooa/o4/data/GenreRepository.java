package be.howest.sooa.o4.data;

import be.howest.sooa.o4.domain.Genre;
import be.howest.sooa.o4.moviedb.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private static final String SQL_READ = SQL + " WHERE id = ?";
    private static final String SQL_FIND_ALL = SQL + " ORDER BY name";
    private static final String SQL_INSERT = "INSERT INTO genre(name) values(?)";
    private static final String SQL_UPDATE = "UPDATE genre SET name = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM genre WHERE id = ?";
    private static final String SQL_COUNT = "SELECT count(*) count FROM genre WHERE name = ?";
    private static final String SQL_COUNT_AS_OTHER = SQL_COUNT +
            " AND id <> ?";
    private static final String SQL_COUNT_BY_GENRE = "SELECT count(*) count FROM movie"
            + " WHERE genre_id = ?";
    private static final String SQL_DELETE_MOVIES_BY_GENRE = "DELETE FROM movie WHERE genre_id = ?";

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

    public boolean exists(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_COUNT)) {
            statement.setString(1, genre.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
        return false;
    }
    
    public boolean existsAsOther(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_COUNT_AS_OTHER)) {
            statement.setString(1, genre.getName());
            statement.setLong(2, genre.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
        return false;
    }
    
    public int movieCountByGenre(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_COUNT_BY_GENRE)) {
            statement.setLong(1, genre.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
        return 0;
    }

    public long save(Genre genre) {
        long lastInsertedId = 0L;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                        Statement.RETURN_GENERATED_KEYS)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.setString(1, genre.getName());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    lastInsertedId = resultSet.getLong(1);
                    connection.commit();
                }
            }
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
        return lastInsertedId;
    }

    public void update(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(SQL_READ);
                PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            selectStatement.setLong(1, genre.getId());
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    updateStatement.setString(1, genre.getName());
                    updateStatement.setLong(2, genre.getId());
                    updateStatement.executeUpdate();
                    connection.commit();
                }
            }
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

    public void delete(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, genre.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DBException(ex);
        }
    }
    
    public void deleteWithMovies(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement deleteMoviesStatement = connection.prepareStatement(SQL_DELETE_MOVIES_BY_GENRE);
                PreparedStatement deleteGenreStatement = connection.prepareStatement(SQL_DELETE)) {
            deleteMoviesStatement.setLong(1, genre.getId());
            deleteMoviesStatement.executeUpdate();
            deleteGenreStatement.setLong(1, genre.getId());
            deleteGenreStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

    private Genre build(ResultSet resultSet) throws SQLException {
        return new Genre(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }

}
