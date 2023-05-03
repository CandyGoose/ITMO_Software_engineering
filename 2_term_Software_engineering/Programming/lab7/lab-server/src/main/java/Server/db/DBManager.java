package Server.db;

import Common.exception.DatabaseException;
import Common.data.*;
import Server.interfaces.DBConnectable;
import Server.util.Encryptor;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DBManager {
    private final DBConnectable dbConnector;


    public DBManager(DBConnectable dbConnector) {
        this.dbConnector = dbConnector;
    }

    public ConcurrentLinkedDeque<Organization> loadCollection() throws DatabaseException {
        return dbConnector.handleQuery((Connection connection) -> {
            String selectCollectionQuery = "SELECT * FROM organizations";
            Statement statement = connection.createStatement();
            ResultSet collectionSet = statement.executeQuery(selectCollectionQuery);
            ConcurrentLinkedDeque<Organization> resultDeque = new ConcurrentLinkedDeque<>();
            while (collectionSet.next()) {
                Coordinates coordinates = new Coordinates(
                        collectionSet.getFloat("x"),
                        collectionSet.getFloat("y")
                );
                OrganizationType type = OrganizationType.valueOf(collectionSet.getString("type"));
                Address postalAddress = new Address (collectionSet.getString("street"));
                Organization organization = new Organization(
                        collectionSet.getLong("id"),
                        collectionSet.getString("name"),
                        coordinates,
                        collectionSet.getDate("creation_date").toLocalDate().atStartOfDay(),
                        collectionSet.getFloat("annual_turnover"),
                        collectionSet.getString("full_name"),
                        collectionSet.getLong("employees_count"),
                        type,
                        postalAddress
                );
                resultDeque.add(organization);
            }
            return resultDeque;
        });
    }

    public Long addElement(Organization organization, String username) throws DatabaseException {
            return dbConnector.handleQuery((Connection connection) -> {
                String addElementQuery = "INSERT INTO organizations "
                        + "(creation_date, name, x, y, annual_turnover, full_name, employees_count, type, street, owner_id) "
                        + "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, id "
                        + "FROM users "
                        + "WHERE users.login = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(addElementQuery,
                        Statement.RETURN_GENERATED_KEYS);

                Coordinates coordinates = organization.getCoordinates();
                Address postalAddress = organization.getPostalAddress();
                preparedStatement.setDate(1, Date.valueOf(organization.getCreationDate().toLocalDate()));
                preparedStatement.setString(2, organization.getName());
                preparedStatement.setFloat(3, coordinates.getX());
                preparedStatement.setFloat(4, coordinates.getY());
                preparedStatement.setFloat(5, organization.getAnnualTurnover());
                preparedStatement.setString(6, organization.getFullName());
                preparedStatement.setLong(7, organization.getEmployeesCount());
                preparedStatement.setString(8, organization.getType().toString());
                preparedStatement.setString(9, postalAddress != null ? postalAddress.getStreet() : null);
                preparedStatement.setString(10, username);

                preparedStatement.executeUpdate();
                ResultSet result = preparedStatement.getGeneratedKeys();
                result.next();

                return result.getLong(1);
            });
    }
    public boolean checkOrganizationExistence(Long id) throws DatabaseException {
        return dbConnector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT COUNT (*) "
                    + "FROM organizations "
                    + "WHERE organizations.id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return resultSet.getInt("count") > 0;
        });
    }

    public boolean removeById(Long id, String username) throws DatabaseException {
            return dbConnector.handleQuery((Connection connection) -> {
                String removeQuery = "DELETE FROM organizations "
                        + "USING users "
                        + "WHERE organizations.id = ? "
                        + "AND organizations.owner_id = users.id AND users.login = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
                preparedStatement.setLong(1, id);
                preparedStatement.setString(2, username);

                int deletedBands = preparedStatement.executeUpdate();
                return deletedBands > 0;
            });
    }

    public boolean updateById(Organization organization, Long id, String username) throws DatabaseException {
        return dbConnector.handleQuery((Connection connection) -> {
            connection.createStatement().execute("BEGIN TRANSACTION;");
            String updateQuery = "UPDATE organizations "
                    + "SET creation_date = ?, "
                    + "name = ?, "
                    + "x = ?, "
                    + "y = ?, "
                    + "annual_turnover = ?, "
                    + "full_name = ?, "
                    + "employees_count = ?, "
                    + "type = ?, "
                    + "street = ? "
                    + "FROM users "
                    + "WHERE organizations.id = ? "
                    + "AND organizations.owner_id = users.id AND users.login = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            Coordinates coordinates = organization.getCoordinates();
            Address postalAddress = organization.getPostalAddress();
            preparedStatement.setDate(1, Date.valueOf(organization.getCreationDate().toLocalDate()));
            preparedStatement.setString(2, organization.getName());
            preparedStatement.setFloat(3, coordinates.getX());
            preparedStatement.setFloat(4, coordinates.getY());
            preparedStatement.setFloat(5, organization.getAnnualTurnover());
            preparedStatement.setString(6, organization.getFullName());
            preparedStatement.setLong(7, organization.getEmployeesCount());
            preparedStatement.setString(8, organization.getType().toString());
            preparedStatement.setString(9, postalAddress != null ? postalAddress.getStreet() : null);
            preparedStatement.setLong(10, id);
            preparedStatement.setString(11, username);

            int updatedRows = preparedStatement.executeUpdate();
            connection.createStatement().execute("COMMIT;");

            return updatedRows > 0;
        });
    }


    public List<Long> clear(String username) throws DatabaseException {
            return dbConnector.handleQuery((Connection connection) -> {
                String clearQuery = "DELETE FROM organizations "
                        + "USING users "
                        + "WHERE organizations.owner_id = users.id AND users.login = ? "
                        + "RETURNING organizations.id;";
                PreparedStatement preparedStatement = connection.prepareStatement(clearQuery);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Long> resultingList = new ArrayList<>();
                while (resultSet.next()) {
                    resultingList.add(resultSet.getLong("id"));
                }
                return resultingList;
            });
    }

    public void addUser(String username, String password) throws DatabaseException {
            dbConnector.handleQuery((Connection connection) -> {
                String addUserQuery = "INSERT INTO users (login, password) "
                        + "VALUES (?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, Encryptor.encryptString(password));

                preparedStatement.executeUpdate();
            });
    }

    public String getPassword(String username) throws DatabaseException {
            return dbConnector.handleQuery((Connection connection) -> {
                String getPasswordQuery = "SELECT (password) "
                        + "FROM users "
                        + "WHERE users.login = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
                return null;
            });
    }

    public boolean checkUsersExistence(String username) throws DatabaseException {
            return dbConnector.handleQuery((Connection connection) -> {
                String existenceQuery = "SELECT COUNT (*) "
                        + "FROM users "
                        + "WHERE users.login = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();

                return resultSet.getInt("count") > 0;
            });
    }

    public List<Long> getIdsOfUsersElements(String username) throws DatabaseException {
            return dbConnector.handleQuery((Connection connection) -> {
                String getIdsQuery = "SELECT organizations.id FROM organizations, users "
                        + "WHERE organizations.owner_id = users.id AND users.login = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(getIdsQuery);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Long> resultingList = new ArrayList<>();
                while (resultSet.next()) {
                    resultingList.add(resultSet.getLong("id"));
                }

                return resultingList;
            });
    }

    public boolean validateUser(String username, String password) throws DatabaseException {
        return dbConnector.handleQuery((Connection connection) ->
                Encryptor.encryptString(password).equals(getPassword(username)));
    }
}
