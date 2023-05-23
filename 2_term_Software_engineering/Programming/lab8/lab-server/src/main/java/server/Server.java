package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import server.managers.SqlCollectionManager;
import server.managers.SqlUserManager;

/**
 * Главный класс для запуска сервера.
 * @author Касьяненко Вера (P3120)
 */
public final class Server {
    /**
     * Логгер
     */
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private Server() {
        throw new UnsupportedOperationException("Это служебный класс, и его экземпляр не может быть создан.");
    }

    /**
     * Главный метод, запускающий сервер.
     * @param args аргументы командной строки
     * @throws IOException если возникает ошибка ввода-вывода
     */
    public static void main(String[] args) throws IOException {
        String dbName = System.getenv("SV_DB");
        String dbUser = System.getenv("SV_LOGIN");
        String dbPassword = System.getenv("SV_PASS");
        int port = 65435;
        startSQLCollectionServer(port, dbName, dbUser, dbPassword);
    }

    /**
     * Метод для запуска SQL-сервера коллекции.
     * @param port порт, на котором будет запущен сервер
     * @param dbName имя базы данных
     * @param dbUser имя пользователя базы данных
     * @param dbPassword пароль пользователя базы данных
     * @throws IOException если возникает ошибка ввода-вывода
     */
    private static void startSQLCollectionServer(int port, String dbName, String dbUser, String dbPassword) throws IOException {
        try (Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://127.0.0.1:5432/" + dbName,
            dbUser,
            dbPassword
        )) {
            SqlUserManager users = new SqlUserManager(conn);
            SqlCollectionManager col = new SqlCollectionManager(conn);
            col.initTable();
            ServerInstance server = new ServerInstance(col, users);
            server.run(port);
        } catch (SQLException e) {
            LOGGER.severe("Не удалось установить соединение с postgresql.");
            System.exit(1);
        }
    }
}
