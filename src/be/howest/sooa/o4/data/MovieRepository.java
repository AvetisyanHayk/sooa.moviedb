package be.howest.sooa.o4.data;

import static be.howest.sooa.o4.data.AbstractRepository.getConnection;
import be.howest.sooa.o4.domain.Genre;
import be.howest.sooa.o4.domain.Movie;
import be.howest.sooa.o4.moviedb.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
    private static final String SQL_INSERT = "INSERT INTO movie(title, genre_id, year, stars) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE movie SET title = ?, genre_id = ?, year = ?, stars = ?"
            + " WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM movie WHERE id = ?";
    private static final String SQL_COUNT = "SELECT count(*) count FROM movie"
            + " WHERE title = ? AND year = ?";
    private static final String SQL_COUNT_AS_OTHER = SQL_COUNT
            + " AND id <> ?";

    public Movie read(long id) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_READ)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return build(resultSet);
                }
            }
            return null;
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

    public List<Movie> findByGenre(Genre genre) {
        List<Movie> entities = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_GENRE)) {
            statement.setLong(1, genre.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(build(resultSet));
                }
            }
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
        return entities;
    }

    public boolean exists(Movie movie) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_COUNT)) {
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
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

    public boolean existsAsOther(Movie movie) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_COUNT_AS_OTHER)) {
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            statement.setLong(3, movie.getId());
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

    public long save(Movie movie) {
        long lastInsertedId = 0L;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                        Statement.RETURN_GENERATED_KEYS)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.setString(1, movie.getTitle());
            statement.setLong(2, movie.getGenre().getId());
            statement.setInt(3, movie.getYear());
            Integer stars = movie.getStars();
            if (stars == null) {
                statement.setNull(4, Types.INTEGER);
            } else {
                statement.setInt(4, movie.getStars());
            }
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

    public void update(Movie movie) {
        try (Connection connection = getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(SQL_READ);
                PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            selectStatement.setLong(1, movie.getId());
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    updateStatement.setString(1, movie.getTitle());
                    updateStatement.setLong(2, movie.getGenre().getId());
                    updateStatement.setInt(3, movie.getYear());
                    Integer stars = movie.getStars();
                    if (stars == null) {
                        updateStatement.setNull(4, Types.INTEGER);
                    } else {
                        updateStatement.setInt(4, movie.getStars());
                    }
                    updateStatement.setLong(5, movie.getId());
                    updateStatement.executeUpdate();
                    connection.commit();
                }
            }

        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

    public void delete(Movie movie) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, movie.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
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
