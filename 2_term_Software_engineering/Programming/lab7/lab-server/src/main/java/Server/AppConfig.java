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

@Configuration
public class AppConfig {

    @Bean
    public DBConnectable dbConnector() {
        return new DBConnector();
    }

    @Bean
    public DBManager dbManager(DBConnectable dbConnector) {
        return new DBManager(dbConnector);
    }

    @Bean
    public UsersManager usersManager(DBManager dbManager) {
        return new UsersManager(dbManager);
    }

    @Bean
    public ServerSocketChannel serverSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        return serverSocketChannel;
    }

    @Bean
    public App app(ServerSocketChannel serverSocketChannel, ExecutorService executorService1, ExecutorService executorService2, ExecutorService executorService3) {
        return new App(serverSocketChannel, executorService1, executorService2, executorService3);
    }

    @Bean
    public ExecutorService executorService1() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public ExecutorService executorService2() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public ExecutorService executorService3() {
        return Executors.newFixedThreadPool(10);
    }
    @Bean
    public ConsoleThread consoleThread() {
        return new ConsoleThread();
    }
}
