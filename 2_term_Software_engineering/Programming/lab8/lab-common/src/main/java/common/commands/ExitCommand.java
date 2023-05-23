package common.commands;

import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.network.BasicUserIO;
import common.util.TerminalColors;

/**
 * Команда выхода
 */
public class ExitCommand extends AbstractCommand {
    /**
     * Конструктор класса.
     */
    public ExitCommand() {
        super("exit");
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("exit", TerminalColors.GREEN) + " - завершить программу";
    }

    /**
     * Упаковывает аргументы команды и данные организации в объект RequestBody.
     *
     * @param args массив аргументов команды
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return объект RequestBody с упакованными данными
     */
    @Override
    public RequestBody packageBody(String[] args, BasicUserIO io) {
        io.writeln("До свидания!");
        System.exit(0);
        return null;
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        throw new UnsupportedOperationException();
    }
}
