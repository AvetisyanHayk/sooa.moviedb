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
            System.out.println(ex.getMessage());
        }
        return lastInsertedId;
    }

    public void update(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(SQL_READ);
                PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
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
            System.out.println(ex.getMessage());
        }
    }

    public void delete(Genre genre) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, genre.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Genre build(ResultSet resultSet) throws SQLException {
        return new Genre(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }

}
