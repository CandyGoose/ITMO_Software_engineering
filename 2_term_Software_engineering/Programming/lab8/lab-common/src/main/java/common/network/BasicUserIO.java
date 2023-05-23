package common.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

/**
 * Класс для базового ввода-вывода пользователя.
 */
public class BasicUserIO {
    /**
     * Повтор ввода
     */
    private boolean repeatInput = false;
    /**
     * Входной поток
     */
    private LinkedList<BufferedReader> inStack;

    /**
     * Выходной поток
     */
    private BufferedWriter out;

    /**
     * Конструктор по умолчанию.
     * Создает объект BasicUserIO с использованием стандартных потоков ввода-вывода (System.in, System.out).
     */
    public BasicUserIO() {
        this(System.in, System.out);
    }

    /**
     * Конструктор с заданными входным и выходным потоками.
     *
     * @param in входной поток.
     * @param out выходной поток.
     */
    public BasicUserIO(InputStream in, OutputStream out) {
        this.inStack = new LinkedList<>();
        this.inStack.add(new BufferedReader(new InputStreamReader(in)));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    /**
     * Устанавливает повторение ввода пользователем.
     *
     * @param repeatInput флаг повторения ввода.
     */
    public void setRepeatInput(boolean repeatInput) {
        this.repeatInput = repeatInput;
    }

    /**
     * Записывает строку в выходной поток.
     *
     * @param s строка для записи.
     */
    public void write(Object s) {
        try {
            out.write(s.toString());
            out.flush();
        } catch (IOException | OutOfMemoryError e) {
            System.err.println("Исключение при записи в выходной поток.");
            System.exit(1);
        }
    }

    /**
     * Записывает строку с переводом строки в выходной поток.
     *
     * @param s строка для записи.
     */
    public void writeln(Object s) {
        try {
            out.write(s.toString());
            out.newLine();
            out.flush();
        } catch (IOException | OutOfMemoryError e) {
            System.err.println("Исключение при записи в выходной поток.");
            System.exit(1);
        }
    }

    /**
     * Считывает строку из входного потока.
     *
     * @return считанная строка или null, если входной поток пуст.
     */
    public String read() {
        try {
            String input;
            while (!inStack.isEmpty()) {
                BufferedReader reader = inStack.getLast();
                input = reader.readLine();
                if (input == null) {
                    detachInAndClose();
                    continue;
                }
                if (repeatInput) {
                    writeln(input);
                }
                return input;
            }
            return null;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении из входного потока.");
            return "";
        }
    }

    /**
     * Считывает строку из входного потока с предварительным выводом сообщения.
     *
     * @param msg сообщение для вывода перед чтением строки.
     * @return считанная строка или null, если входной поток пуст.
     */
    public String read(String msg) {
        write(msg);
        return read();
    }

    /**
     * Присоединяет новый входной поток.
     *
     * @param in входной поток для присоединения.
     */
    public void attachIn(BufferedReader in) {
        inStack.add(in);
    }

    /**
     * Отсоединяет текущий входной поток.
     *
     * @return отсоединенный входной поток.
     */
    public BufferedReader detachIn() {
        return inStack.removeLast();
    }

    /**
     * Отсоединяет текущий входной поток и закрывает его.
     *
     * @return отсоединенный входной поток.
     */
    public BufferedReader detachInAndClose() {
        BufferedReader t = this.detachIn();
        try {
            t.close();
        } catch (IOException e) {
            System.err.println("Не удалось закрыть входной поток.");
        }
        return t;
    }
}
