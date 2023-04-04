package Client.util;

import Common.exception.IllegalSizeOfScriptException;
import Client.CommandDispatcher.CommandToSend;
import Common.util.TextWriter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ScriptReader {
    private final String filename;
    private final File path;
    private static Stack<String> callStack = new Stack<>();

    public ScriptReader(CommandToSend commandToSend) throws IllegalSizeOfScriptException, IllegalArgumentException {
        this.filename = commandToSend.getCommandArgs()[0];
        path = new File(new File(System.getProperty("user.dir")), filename);
        if (!path.exists() || !path.canRead())
            throw new IllegalArgumentException("Проблема при вызове скрипта. Проверьте данные на верность.");
        if (callStack.contains(path.getAbsolutePath())) {
            callStack.clear();
            TextWriter.printErr("Скрипты рекурсивно ссылаются друг на друга.\nРабота программы прекращена.");
            System.exit(1);
        }
        callStack.push(path.getAbsolutePath());
        if (path.equals(new File(System.getProperty("user.dir"), "/dev/random")) || path.length() / (1024 * 1024) > 10) {
            throw new IllegalSizeOfScriptException("Размер скриптов составляет более 10Мб");
        }
    }

    public void stopScriptReading() {
        callStack.clear();
    }

    public File getPath() {
        return path;
    }
}
