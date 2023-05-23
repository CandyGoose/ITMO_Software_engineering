package common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import common.commands.*;
import common.exceptions.CommandArgumentException;
import common.exceptions.CommandNotFoundException;
import common.exceptions.InvalidRequestException;
import common.exceptions.UnauthenticatedException;
import common.network.*;
import org.springframework.stereotype.Component;

/**
 * Класс, обрабатывающий команды.
 */
@Component
public class CommandHandler {
    /**
     * Команды
     */
    private HashMap<String, AbstractCommand> commands = new HashMap<>();

    /**
     * Создает экземпляр стандартного обработчика команд с заданными менеджерами коллекций и пользователей.
     *
     * @param cm менеджер коллекций.
     * @param users менеджер пользователей.
     * @return экземпляр стандартного обработчика команд.
     */
    public static CommandHandler standardCommandHandler(CollectionManager cm, UserManager users) {
        CommandHandler ch = new CommandHandler();
        ch.addCommand(new HelpCommand(ch));
        ch.addCommand(new InfoCommand(cm));
        ch.addCommand(new ShowCommand(cm));
        ch.addCommand(new AddCommand(cm));
        ch.addCommand(new RemoveByIdCommand(cm));
        ch.addCommand(new UpdateCommand(cm));
        ch.addCommand(new ExitCommand());
        ch.addCommand(new ClearCommand(cm));
        ch.addCommand(new PrintDescendingAnnualTurnoverCommand(cm));
        ch.addCommand(new RemoveFirstCommand(cm));
        ch.addCommand(new PrintUniqueEmployeesCount(cm));
        ch.addCommand(new PrintDescendingCommand(cm));
        ch.addCommand(new SortCommand(cm));
        ch.addCommand(new ExecuteScriptCommand());
        ch.addCommand(new RegisterCommand(users));
        ch.addCommand(new LoginCommand(users));

        return ch;
    }

    /**
     * Обрабатывает строку команды и возвращает запрос.
     *
     * @param commandString строка команды.
     * @param io интерфейс ввода-вывода для взаимодействия с пользователем.
     * @param auth информация об аутентификации пользователя.
     * @return запрос, сформированный на основе строки команды.
     * @throws CommandNotFoundException выбрасывается, если команда не найдена.
     * @throws CommandArgumentException выбрасывается, если команда содержит неверные аргументы.
     */
    public Request handleString(String commandString, BasicUserIO io, AuthCredentials auth) throws CommandNotFoundException, CommandArgumentException {
        String[] commandArgs = commandString.trim().split("\\s+");

        AbstractCommand command = commands.get(commandArgs[0]);

        if (command == null) {
            throw new CommandNotFoundException(commandArgs[0]);
        }

        RequestBody body = command.packageBody(Arrays.copyOfRange(commandArgs, 1, commandArgs.length), io);
        if (body == null) {
            return null;
        }
        Request request = new Request(command.getName(), body, auth);
        return request;
    }

    /**
     * Обрабатывает запрос и возвращает ответ.
     *
     * @param request запрос, который требуется обработать.
     * @param users   менеджер пользователей.
     * @return ответ на запрос.
     */
    public Response handleRequest(Request request, UserManager users) {
        if (commands.get(request.getCommandName()) != null) {
            Long userId = users.authenticate(request.getAuth());

            if (userId == null && commands.get(request.getCommandName()).requiresAuth()) {
                return new ResponseWithException(new UnauthenticatedException());
            }

            try {
                return commands.get(request.getCommandName()).execute(request);
            } catch (InvalidRequestException e) {
                return new ResponseWithException(e);
            }
        }

        return new ResponseWithException(new CommandNotFoundException(request.getCommandName()));
    }

    /**
     * Добавляет команду в обработчик.
     *
     * @param command команда для добавления.
     */
    public void addCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }

    /**
     * Возвращает список команд, доступных в обработчике.
     *
     * @return список команд.
     */
    public Map<String, AbstractCommand> getCommands() {
        return this.commands;
    }
}
