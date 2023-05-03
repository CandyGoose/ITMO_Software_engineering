package Server;

import Common.util.TextWriter;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Главный класс, запускающий приложение сервера.
 * Содержит метод main, отвечающий за запуск приложения.
 *
 * @author Касьяненко Вера (P3120)
 */
public final class Server {

    private Server() {
        throw new UnsupportedOperationException("Это утилитный класс, экземпляр которого не может быть создан.");
    }

    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(AppConfig.class);
            context.refresh();

            ServerSocketChannel serverSocketChannel = context.getBean(ServerSocketChannel.class);
            askForPort(serverSocketChannel);

            App app = context.getBean(App.class);
            app.start();
        } catch (BeansException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод inputPort запрашивает данные для создания соединения.
     */
    public static void askForPort(ServerSocketChannel server) {
        Scanner sc = ServerConfig.scanner;
        TextWriter.printInfoMessage("Вы хотите использовать порт по умолчанию? [y/n]");
        try {
            String s = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if ("n".equals(s)) {
                TextWriter.printInfoMessage("Введите порт (1-65535):");
                String port = sc.nextLine();
                try {
                    int portInt = Integer.parseInt(port);
                    if (portInt > 0 && portInt <= 65535) {
                        ServerConfig.PORT = portInt;
                        server.socket().bind(new InetSocketAddress(ServerConfig.PORT));
                    } else {
                        TextWriter.printErr("Число не входит в установленные пределы, повторите ввод.");
                        askForPort(server);
                    }
                } catch (IllegalArgumentException e) {
                    TextWriter.printErr("Ошибка при обработке номера, повторите ввод.");
                    askForPort(server);
                }
            } else if ("y".equals(s)) {
                TextWriter.printInfoMessage("Будет использован порт по умолчанию.");
                server.socket().bind(new InetSocketAddress(ServerConfig.PORT));
            } else {
                TextWriter.printErr("Вы ввели недопустимый символ, попробуйте еще раз.");
                askForPort(server);
            }
        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            ServerConfig.logger.info("Работа сервера завершена.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            TextWriter.printErr("Ошибка при обработке номера, повторите ввод.");
            askForPort(server);
        } catch (BindException e) {
            TextWriter.printErr("Этот порт недоступен. Введите другой.");
            askForPort(server);
        } catch (IOException e) {
            TextWriter.printErr("Возникли проблемы с IO.");
            askForPort(server);
        }
    }
}
