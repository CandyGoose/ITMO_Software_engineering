package server;

import common.util.CollectionManager;
import common.util.UserManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.managers.SqlCollectionManager;
import server.managers.SqlUserManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Настройки сервера
 */
@Configuration
public class ServerConfig {
    /**
     * Создает подключение к базе данных.
     *
     * @return объект Connection для подключения к базе данных
     * @throws SQLException если произошла ошибка при подключении к базе данных
     */
    @Bean
    public Connection databaseConnection() throws SQLException {
        String dbName = System.getenv("SV_DB");
        String dbUser = System.getenv("SV_LOGIN");
        String dbPassword = System.getenv("SV_PASS");

        return DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/" + dbName,
                dbUser,
                dbPassword
        );
    }

    /**
     * Создает экземпляр SqlUserManager.
     *
     * @param connection подключение к базе данных
     * @return экземпляр SqlUserManager
     * @throws SQLException если произошла ошибка при работе с базой данных
     */
    @Bean
    public SqlUserManager sqlUserManager(Connection connection) throws SQLException {
        return new SqlUserManager(connection);
    }

    /**
     * Создает экземпляр SqlCollectionManager.
     *
     * @param connection подключение к базе данных
     * @return экземпляр SqlCollectionManager
     */
    @Bean
    public SqlCollectionManager sqlCollectionManager(Connection connection) {
        return new SqlCollectionManager(connection);
    }

    /**
     * Создает экземпляр ServerInstance.
     *
     * @param collectionManager менеджер коллекции
     * @param userManager менеджер пользователей
     * @return экземпляр ServerInstance
     * @throws IOException если произошла ошибка ввода-вывода
     */
    @Bean
    public ServerInstance serverInstance(CollectionManager collectionManager, UserManager userManager) throws IOException {
        return new ServerInstance(collectionManager, userManager);
    }
}
