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
        if (args.length > 0) {
            try {
                InetSocketAddress addr = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
                ConsoleClient console = new ConsoleClient(addr);
                console.run();
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println(
                    TerminalColors.colorString("Не удается получить адрес хоста и порт из аргументов. Вы должны передать их в качестве аргументов.", TerminalColors.RED)
                );
            } catch (IOException e) {
                System.out.println(
                    TerminalColors.colorString("Не удалось запустить приложение.", TerminalColors.RED)
                );
            }
        } else {
            TerminalColors.doColoring(false);
            Application.launch(GraphicClient.class);
        }
    }
}
