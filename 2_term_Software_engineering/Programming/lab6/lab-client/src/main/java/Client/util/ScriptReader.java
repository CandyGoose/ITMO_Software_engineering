package Client.util;

import Common.exception.IllegalSizeOfScriptException;
import Client.CommandDispatcher.CommandToSend;
import Common.util.TextWriter;

import java.io.File;
import java.util.Stack;

/**
 * Класс для чтения скриптов.
 */
public class ScriptReader {
    /**
     * Имя скрипта.
     */
    private final String filename;
    /**
     * Путь до скрипта скрипта.
     */

    private final File path;
    /**
     * Стек для отслеживания рекурсии в скриптах.
     */
    private static Stack<String> callStack = new Stack<>();

    /**
     * Конструктор для создания объекта ScriptReader.
     * @param commandToSend объект типа CommandToSend, содержащий имя скрипта.
     * @throws IllegalSizeOfScriptException исключение, которое выбрасывается, если размер скрипта превышает 10Мб.
     * @throws IllegalArgumentException исключение, которое выбрасывается, если файл скрипта не найден или не может быть прочитан.
     */
    public ScriptReader(CommandToSend commandToSend) throws IllegalSizeOfScriptException, IllegalArgumentException {
        this.filename = commandToSend.getCommandArgs()[0];
        path = new File(new File(System.getProperty("user.dir")), filename);
        if (!path.exists() || !path.canRead())
            throw new IllegalArgumentException("Проблема при вызове скрипта. Проверьте данные на верность.");
        if (callStack.contains(path.getAbsolutePath())) {
            callStack.clear();
            TextWriter.printErr("Скрипты рекурсивно ссылаются друг на друга. Работа программы прекращена.");
            System.exit(1);
        }
        callStack.push(path.getAbsolutePath());
        if (path.equals(new File(System.getProperty("user.dir"), "/dev/random")) || path.length() / (1024 * 1024) > 10) {
            throw new IllegalSizeOfScriptException("Размер скриптов составляет более 10Мб");
        }
    }

    /**
     * Метод для остановки чтения скрипта.
     */
    public void stopScriptReading() {
        callStack.clear();
    }

    /**
     * Метод для получения пути к скрипту.
     * @return объект типа File, представляющий путь к скрипту.
     */
    public File getPath() {
        return path;
    }
}
