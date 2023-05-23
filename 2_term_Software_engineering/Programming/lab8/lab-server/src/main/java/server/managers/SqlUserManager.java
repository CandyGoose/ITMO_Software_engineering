package server.managers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.logging.Logger;

import common.util.AuthCredentials;
import common.util.UserManager;
import server.Server;

/**
 * Класс SqlUserManager представляет реализацию интерфейса UserManager с использованием SQL-базы данных.
 * Осуществляет аутентификацию, регистрацию и получение имени пользователя по идентификатору.
 */
public class SqlUserManager implements UserManager {
    /**
     * Логгер
     */
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    /**
     * Запрос для создания таблицы
     */
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS users ("
                                                   + "    id serial PRIMARY KEY,"
                                                   + "    login varchar(50) NOT NULL UNIQUE,"
                                                   + "    password varchar(30) NOT NULL,"
                                                   + "    salt varchar(8) NOT NULL)";
    /**
     * Соединение
     */
    private final Connection conn;

    /**
     * Создает экземпляр SqlUserManager с переданным подключением к базе данных.
     * При необходимости создает таблицу пользователей в базе данных.
     *
     * @param conn подключение к базе данных
     * @throws SQLException если возникает ошибка SQL при создании таблицы
     */
    public SqlUserManager(Connection conn) throws SQLException {
        this.conn = conn;

        try (Statement s = conn.createStatement()) {
            s.execute(CREATE_TABLE_QUERY);
        }
    }

    /**
     * Хэширует сообщение с использованием соли.
     *
     * @param message исходное сообщение
     * @param salt соль
     * @return хэш сообщения с солью
     * @throws NoSuchAlgorithmException если не найден алгоритм хэширования
     */
    private static String encodeHashWithSalt(String message, String salt) throws NoSuchAlgorithmException {
        Encoder encoder = Base64.getEncoder();
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hash = md.digest((message + salt).getBytes(StandardCharsets.UTF_8));
        return encoder.encodeToString(hash);
    }

    /**
     * Переопределение метода authenticate(AuthCredentials auth) из интерфейса UserManager.
     * Метод выполняет аутентификацию пользователя по переданным учетным данным.
     * Если аутентификация успешна, возвращает идентификатор пользователя, иначе возвращает null.
     *
     * @param auth объект AuthCredentials, содержащий логин и пароль пользователя.
     * @return идентификатор пользователя, если аутентификация успешна, иначе null.
     */
    @Override
    public Long authenticate(AuthCredentials auth) {
        final String findUserQuery = "SELECT id, password, salt FROM users WHERE login = ? LIMIT 1";

        if (auth == null) {
            return null;
        }

        try (PreparedStatement s = conn.prepareStatement(findUserQuery)) {
            s.setString(1, auth.getLogin());
            try (ResultSet res = s.executeQuery()) {
                if (res.next()) {
                    String realPasswordHashed = res.getString("password");
                    String passwordHashed = encodeHashWithSalt(auth.getPassword(), res.getString("salt"));

                    if (passwordHashed.equals(realPasswordHashed)) {
                        return res.getLong("id");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Не удалось аутентифицировать пользователя из-за исключения SQL.");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("Не удалось найти алгоритм хэширования при входе пользователя в систему.");
        }

        return null;
    }


    /**
     * Переопределение метода register(AuthCredentials auth) из интерфейса UserManager.
     * Метод выполняет регистрацию нового пользователя с переданными учетными данными.
     * Если регистрация прошла успешно, возвращает идентификатор нового пользователя, иначе возвращает null.
     *
     * @param auth объект AuthCredentials, содержащий логин и пароль нового пользователя.
     * @return идентификатор нового пользователя, если регистрация успешна, иначе null.
     */
    @Override
    public Long register(AuthCredentials auth) {
        final String registerQuery = "INSERT INTO users VALUES (default, ?, ?, ?) RETURNING id;";
        final int loginIndex = 1;
        final int passwordIndex = 2;
        final int saltIndex = 3;
        final int saltBytes = 6;

        try (PreparedStatement s = conn.prepareStatement(registerQuery)) {
            Encoder encoder = Base64.getEncoder();
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[saltBytes];
            random.nextBytes(salt);
            String saltStr = encoder.encodeToString(salt);

            String hashStr = encodeHashWithSalt(auth.getPassword(), saltStr);

            s.setString(loginIndex, auth.getLogin());
            s.setString(passwordIndex, hashStr);
            s.setString(saltIndex, saltStr);

            try (ResultSet res = s.executeQuery()) {
                res.next();
                return res.getLong("id");
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("Не удалось найти алгоритм хэширования при регистрации пользователя в системе.");
            return null;
        } catch (SQLException e) {
            LOGGER.severe("Не удалось зарегистрировать пользователя.");
            return null;
        }
    }
}
