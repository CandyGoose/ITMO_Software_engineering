package server.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import common.util.CollectionManager;
import common.exceptions.InvalidFieldException;
import common.data.Address;
import common.data.Coordinates;
import common.data.Organization;
import common.data.OrganizationType;
import server.Server;

/**
 * Класс SqlCollectionManager реализует интерфейс CollectionManager и предоставляет
 * методы для работы с коллекцией организаций в базе данных с использованием SQL.
 */
public class SqlCollectionManager implements CollectionManager {
    /**
     * Запрос для создания таблицы
     */
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS organizations ("
            + "   id serial PRIMARY KEY,"
            + "   name varchar(100) NOT NULL,"
            + "   creation_date TIMESTAMP NOT NULL,"
            + "   x float NOT NULL,"
            + "   y float NOT NULL,"
            + "   annual_turnover float NOT NULL,"
            + "   full_name varchar(100) NOT NULL,"
            + "   employees_count bigint NOT NULL,"
            + "   type varchar(24) NOT NULL CHECK(type = 'COMMERCIAL' "
            + "OR type = 'PUBLIC' "
            + "OR type = 'GOVERNMENT' "
            + "OR type = 'TRUST' "
            + "OR type = 'OPEN_JOINT_STOCK_COMPANY'),"
            + "   street varchar(100),"
            + "   owner_id integer NOT NULL,"
            + "   CONSTRAINT fk_owner"
            + "      FOREIGN KEY(owner_id) REFERENCES users(id) ON DELETE CASCADE)";

    /**
     * Запрос для получения данных
     */
    private static final String SELECT_QUERY = "SELECT o.id, o.name, o.creation_date, "
            + "o.x, o.y, "
            + "o.annual_turnover, o.full_name, o.employees_count, o.type, o.street, "
            + "u.login AS owner FROM organizations AS o LEFT JOIN users AS u "
            + "ON o.owner_id = u.id";

    /**
     * Логгер
     */
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    /**
     * Соединение
     */
    private final Connection conn;

    /**
     * Коллекция
     */
    private final List<Organization> collection = new LinkedList<>();

    /**
     * Блокировка
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Конструктор класса SqlCollectionManager.
     *
     * @param conn объект Connection для установления соединения с базой данных.
     */
    public SqlCollectionManager(Connection conn) {
        this.conn = conn;
    }

    /**
     * Метод initTable выполняет создание таблицы organizations в базе данных и
     * инициализацию коллекции организаций данными из таблицы.
     *
     * @throws SQLException если произошла ошибка при работе с базой данных.
     */
    public void initTable() throws SQLException {
        try (Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            s.execute(CREATE_TABLE_QUERY);

            try (ResultSet res = s.executeQuery(SELECT_QUERY)) {
                int invalidOrganizations = 0;

                while (res.next()) {
                    Organization newOrganization = mapRowToOrganization(res);
                    if (newOrganization != null) {
                        collection.add(newOrganization);
                    } else {
                        invalidOrganizations++;
                    }
                }

                Collections.sort(collection);

                LOGGER.info("Загружено " + collection.size() + " организаций из БД, удалено " + invalidOrganizations + " организаций с ошибками.");
            }
        }
    }

    /**
     * Преобразует строку из ResultSet в объект Organization.
     *
     * @param res объект ResultSet, содержащий данные строки.
     * @return объект Organization, соответствующий строке из ResultSet, или null, если произошла ошибка при создании объекта.
     * @throws SQLException если произошла ошибка при работе с базой данных.
     */
    private Organization mapRowToOrganization(ResultSet res) throws SQLException {
        try {
            Organization organization = new Organization(
                    res.getLong("id"),
                    res.getString("name"),
                    res.getTimestamp("creation_date").toLocalDateTime(),
                    new Coordinates(
                            res.getObject("x") == null ? null : res.getFloat("x"),
                            res.getFloat("y")
                    ),
                    res.getFloat("annual_turnover"),
                    res.getString("full_name"),
                    res.getLong("employees_count"),
                    resolveOrganizationType(res.getObject("type")),
                    new Address(
                            res.getString("street")
                    )
            );

            organization.setOwner(res.getString("owner"));

            return organization;
        } catch (InvalidFieldException e) {
            return null;
        }
    }

    /**
     * Разрешает тип организации на основе полученного значения.
     *
     * @param type объект, представляющий тип организации.
     * @return объект OrganizationType, соответствующий типу организации, или null, если тип не удалось найти.
     */
    private OrganizationType resolveOrganizationType(Object type) {
        if (type instanceof OrganizationType) {
            return (OrganizationType) type;
        } else if (type instanceof String) {
            String typeString = (String) type;
            try {
                return OrganizationType.valueOf(typeString);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Подготавливает PreparedStatement для выполнения операций добавления и обновления
     * организации в базе данных.
     *
     * @param s объект PreparedStatement для подготовки.
     * @param organization объект Organization, содержащий данные организации.
     * @param paramOffset смещение параметров в PreparedStatement.
     * @throws SQLException если произошла ошибка при работе с базой данных.
     */
    private void prepareOrganizationStatement(PreparedStatement s, Organization organization, int paramOffset) throws SQLException {
        int i = 0;
        s.setString(paramOffset + ++i, organization.getName());
        s.setTimestamp(paramOffset + ++i, Timestamp.valueOf(organization.getCreationDate()));
        if (organization.getCoordinates().getX() != null) {
            s.setFloat(paramOffset + ++i, organization.getCoordinates().getX());
        } else {
            s.setNull(paramOffset + ++i, Types.BIGINT);
        }
        s.setFloat(paramOffset + ++i, organization.getCoordinates().getY());

        s.setFloat(paramOffset + ++i, organization.getAnnualTurnover());

        s.setString(paramOffset + ++i, organization.getFullName());

        s.setLong(paramOffset + ++i, organization.getEmployeesCount());

        s.setString(paramOffset + ++i, String.valueOf(organization.getType()));


        if (organization.getAddress().getStreet() != null) {
            s.setString(paramOffset + ++i, organization.getAddress().getStreet());
        } else {
            s.setNull(paramOffset + ++i, Types.VARCHAR);
        }
        s.setString(paramOffset + ++i, organization.getOwner());
    }

    /**
     * Возвращает список всех организаций в коллекции.
     *
     * @return список организаций в коллекции.
     */
    @Override
    public List<Organization> getCollection() {
        try {
            lock.lock();
            return new LinkedList<>(collection);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Возвращает список всех организаций в коллекции в обратном порядке.
     *
     * @return список организаций в обратном порядке.
     */
    @Override
    public List<Organization> getDescendingCollection() {
        try {
            lock.lock();
            List<Organization> copiedCollection = new LinkedList<>(collection);
            Collections.reverse(copiedCollection);
            return copiedCollection;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Возвращает отсортированный список всех организаций в коллекции по их идентификаторам.
     *
     * @return отсортированный список организаций по идентификаторам.
     */
    public List<Organization> getSortedCollection() {
        try {
            lock.lock();
            List<Organization> sortedCollection = new LinkedList<>(collection);
            Comparator<Organization> idComparator = Comparator.comparing(Organization::getId);
            sortedCollection.sort(idComparator);
            return sortedCollection;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Возвращает организацию по ее идентификатору.
     *
     * @param id идентификатор организации.
     * @return объект Organization, соответствующий идентификатору, или null, если организация не найдена.
     */
    @Override
    public Organization getItemById(long id) {
        try {
            lock.lock();
            return collection.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Добавляет организацию в коллекцию и базу данных.
     *
     * @param organization объект Organization, представляющий добавляемую организацию.
     * @return идентификатор добавленной организации.
     */
    @Override
    public long add(Organization organization) {
        String query = "INSERT INTO organizations VALUES ("
                + "    default,?,?,?,?,?,?,?,?,?,(SELECT id FROM users WHERE login = ?)) "
                + "    RETURNING id";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            lock.lock();
            prepareOrganizationStatement(s, organization, 0);
            try (ResultSet res = s.executeQuery()) {
                res.next();
                Long id = res.getLong("id");
                organization.setId(id);
                collection.add(organization);
                Collections.sort(collection);
                return id;
            }
        } catch (SQLException e) {
            LOGGER.severe("Failed to insert element into DB");
            return 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Обновляет данные организации в коллекции и базе данных.
     *
     * @param organization объект Organization, содержащий обновленные данные.
     * @return true, если обновление прошло успешно, false в противном случае.
     */
    @Override
    public boolean update(Organization organization) {
        final int idOffset = 10;
        String query = "UPDATE organizations SET "
                + "name=?, "
                + "creation_date=?, "
                + "x=?, "
                + "y=?, "
                + "annual_turnover=?, "
                + "full_name=?, "
                + "employees_count=?, "
                + "type=?, "
                + "street=? "
                + "WHERE id=?";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            lock.lock();
            prepareOrganizationStatement(s, organization, 0);
            s.setLong(idOffset, organization.getId());
            int count = s.executeUpdate();
            if (count > 0) {
                collection.removeIf(x -> x.getId() == organization.getId());
                collection.add(organization);
                Collections.sort(collection);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.severe("Не удалось обновить организацию.");
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Возвращает список уникальных значений поля employeesCount для всех организаций в коллекции.
     *
     * @return список уникальных значений поля employeesCount.
     */
    @Override
    public List<Long> getUniqueEmployeesCount() {
        try {
            lock.lock();
            return collection.stream()
                    .filter(x -> x.getEmployeesCount() != null)
                    .map(x -> x.getEmployeesCount())
                    .distinct()
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }


    /**
     * Возвращает список значений поля annualTurnover для всех организаций в коллекции в убывающем порядке.
     *
     * @return список значений поля annualTurnover в убывающем порядке.
     */
    @Override
    public List<Float> getDescendingAnnualTurnover() {
        try {
            lock.lock();
            return collection.stream()
                    .filter(x -> x.getAnnualTurnover() != null)
                    .map(x -> x.getAnnualTurnover())
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Удаляет организацию из коллекции и базы данных по ее идентификатору.
     *
     * @param id идентификатор удаляемой организации.
     */
    @Override
    public void remove(long id) {
        String query = "DELETE FROM organizations WHERE id=?";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            lock.lock();
            s.setLong(1, id);
            s.executeUpdate();
            collection.removeIf(x -> x.getId() == id);
        } catch (SQLException e) {
            LOGGER.severe("Failed to delete row");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Удаляет из коллекции и базы данных первую коллекцию.
     */
    @Override
    public int removeFirst(Predicate<? super Organization> predicate) {
        try {
            lock.lock();
            Optional<Organization> organizationToRemove = collection.stream().filter(predicate).findFirst();
            organizationToRemove.ifPresent(organization -> {
                collection.remove(organization);
                remove(organization.getId());
            });
            return organizationToRemove.isPresent() ? 1 : 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Удаляет из коллекции и базы данных все организации, удовлетворяющие заданному условию.
     *
     * @param predicate условие для удаления организаций.
     * @return количество удаленных организаций.
     */
    @Override
    public int removeIf(Predicate<? super Organization> predicate) {
        try {
            lock.lock();
            List<Long> ids = collection.stream().filter(predicate).map(x -> x.getId()).collect(Collectors.toList());
            ids.forEach(this::remove);
            return ids.size();
        } finally {
            lock.unlock();
        }
    }
}
