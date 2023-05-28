package common.commands;

import common.util.CommandHandler;
import common.exceptions.ClientSideCommandException;
import common.exceptions.CommandArgumentException;
import common.exceptions.InvalidRequestException;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.network.BasicUserIO;
import common.util.TerminalColors;
/**
 * Команда вывода помощи
 */
public class HelpCommand extends AbstractCommand {

    /**
     * Обработчик команд
     */
    private CommandHandler ch;

    /**
     * Конструктор класса.
     *
     * @param ch обработчик команд
     */
    public HelpCommand(CommandHandler ch) {
        super("help");
        this.ch = ch;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("help", TerminalColors.GREEN)
             + " - вывести справку по доступным командам";
    }

    /**
     * Упаковывает аргументы команды и данные организации в объект RequestBody.
     *
     * @param args массив аргументов команды
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return объект RequestBody с упакованными данными
     * @throws CommandArgumentException если аргументы команды некорректны
     */
    @Override
    public RequestBody packageBody(String[] args, BasicUserIO io) throws CommandArgumentException {
        if (args.length > 1) {
            throw new CommandArgumentException("команда help принимает 1 аргумент или не принимает никаких аргументов. Получено " + args.length);
        } else if (args.length == 1) {
            AbstractCommand command = ch.getCommands().get(args[0]);
            if (command == null) {
                io.writeln(TerminalColors.colorString("Нет команды с именем " + args[0] + ".", TerminalColors.RED));
            }
            io.writeln("- " + TerminalColors.colorString(command.getName(), TerminalColors.GREEN));
            io.writeln(command.getUsage());
        } else {
            ch.getCommands().values().forEach(c -> {
                io.writeln("- " + TerminalColors.colorString(c.getName(), TerminalColors.GREEN));
                io.writeln(c.getUsage());
                io.writeln("");
            });
        }
        return null;
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     * @throws InvalidRequestException если запрос некорректен
     */
    @Override
    public Response execute(Request request) throws InvalidRequestException {
        throw new ClientSideCommandException();
    }
}
