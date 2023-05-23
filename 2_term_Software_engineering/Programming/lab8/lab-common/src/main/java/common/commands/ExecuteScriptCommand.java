package common.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import common.exceptions.CommandArgumentException;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.network.BasicUserIO;
import common.util.TerminalColors;

/**
 * Команда исполнения скрипта
 */
public class ExecuteScriptCommand extends AbstractCommand {
    /**
     * Файлы
     */
    private Set<File> openedFiles = new HashSet<>();

    /**
     * Конструктор класса.
     */
    public ExecuteScriptCommand() {
        super("execute_script");
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("execute_script [fileName]", TerminalColors.GREEN)
             + " - считать и исполнить скрипт из указанного файла";
    }

    /**
     * Упаковывает аргументы команды и данные организации в объект RequestBody.
     *
     * @param args массив аргументов команды
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return объект RequestBody с упакованными данными
     * @throws CommandArgumentException если аргументы команды некорректны
     */
    public RequestBody packageBody(String[] args, BasicUserIO io) throws CommandArgumentException {
        if (args.length != 1) {
            throw new CommandArgumentException(this.getName(), 1, args.length);
        }

        File file = new File(args[0]);

        if (openedFiles.contains(file)) {
            throw new CommandArgumentException("Рекурсия в файле \"" + args[0] + '"');
        }

        openedFiles.add(file);

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file)) {
                @Override
                public String readLine() throws IOException {
                    String line = super.readLine();
                    while (line != null && line.trim().isEmpty()) {
                        line = super.readLine();
                    }
                    return line;
                }

                @Override
                public void close() throws IOException {
                    super.close();
                    ExecuteScriptCommand.this.openedFiles.remove(file);
                }
            };

            io.attachIn(fileReader);
        } catch (FileNotFoundException e) {
            throw new CommandArgumentException("Не удается найти файл с таким именем " + args[0]);
        }
        return null;
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     * @throws UnsupportedOperationException если запрос некорректен
     */
    public Response execute(Request request) {
        throw new UnsupportedOperationException();
    }
}
