package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import common.util.TerminalColors;

import javafx.application.Application;


/**
 * Главный класс для запуска клиента.
 * @author Касьяненко Вера (P3120)
 */
public final class Client {
    private Client() {
        throw new UnsupportedOperationException("Это служебный класс, и его экземпляр не может быть создан");
    }

    /**
     * Главный метод, запускающий клиент.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Application.launch(GraphicClient.class);
    }
}
