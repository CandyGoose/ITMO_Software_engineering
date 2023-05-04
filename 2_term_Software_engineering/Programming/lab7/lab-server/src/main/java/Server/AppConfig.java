package Server;

import Server.db.DBConnector;
import Server.db.DBManager;
import Server.db.UsersManager;
import Server.interfaces.DBConnectable;
import Server.util.ConsoleThread;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Конфигурационный класс, отвечающий за настройку бинов и зависимостей приложения.
 */
@Configuration
public class AppConfig {

    /**
     * Создает экземпляр класса DBConnector для подключения к базе данных.
     * @return экземпляр класса DBConnector
     */
    @Bean
    public DBConnectable dbConnector() {
        return new DBConnector();
    }

    /**
     * Создает экземпляр класса DBManager для управления базой данных.
     * @param dbConnector экземпляр класса DBConnector для подключения к базе данных.
     * @return экземпляр класса DBManager
     */
    @Bean
    public DBManager dbManager(DBConnectable dbConnector) {
        return new DBManager(dbConnector);
    }

    /**
     * Создает экземпляр класса UsersManager для управления пользователями.
     * @param dbManager экземпляр класса DBManager для управления базой данных.
     * @return экземпляр класса UsersManager
     */
    @Bean
    public UsersManager usersManager(DBManager dbManager) {
        return new UsersManager(dbManager);
    }

    /**
     * Создает ServerSocketChannel для прослушивания входящих соединений.
     * @return ServerSocketChannel для прослушивания входящих соединений.
     * @throws IOException если произошла ошибка при создании канала
     */
    @Bean
    public ServerSocketChannel serverSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        return serverSocketChannel;
    }

    /**
     * Создает экземпляр класса App для запуска приложения.
     * @param serverSocketChannel ServerSocketChannel для прослушивания входящих соединений.
     * @param executorService1 экземпляр ExecutorService для выполнения задач на сервере.
     * @param executorService2 экземпляр ExecutorService для выполнения задач на сервере.
     * @param executorService3 экземпляр ExecutorService для выполнения задач на сервере.
     * @return экземпляр класса App
     */
    @Bean
    public App app(ServerSocketChannel serverSocketChannel, ExecutorService executorService1, ExecutorService executorService2, ExecutorService executorService3) {
        return new App(serverSocketChannel, executorService1, executorService2, executorService3);
    }

    /**
     * Создает экземпляр ExecutorService для выполнения задач на сервере.
     * @return экземпляр ExecutorService
     */
    @Bean
    public ExecutorService executorService1() {
        return Executors.newFixedThreadPool(10);
    }

    /**
     * Создает экземпляр ExecutorService для выполнения задач на сервере.
     * @return экземпляр ExecutorService
     */
    @Bean
    public ExecutorService executorService2() {
        return Executors.newFixedThreadPool(10);
    }

    /**
     * Создает экземпляр ExecutorService для выполнения задач на сервере.
     * @return экземпляр ExecutorService
     */
    @Bean
    public ExecutorService executorService3() {
        return Executors.newFixedThreadPool(10);
    }

    /**
     * Создает экземпляр ConsoleThread.
     * @return экземпляр ConsoleThread
     */
    @Bean
    public ConsoleThread consoleThread() {
        return new ConsoleThread();
    }
}
