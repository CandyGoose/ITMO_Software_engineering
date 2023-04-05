package Client.CommandDispatcher;

/**
 * Класс, представляющий команду и её аргументы, которые будут отправлены на сервер.
 */
public class CommandToSend {

    /**
     * Имя команды.
     */
    private final String commandName;

    /**
     * Аргументы команды.
     */
    private final String[] commandArgs;

    /**
     * Создает объект команды с указанным именем и аргументами.
     * @param commandName имя команды
     * @param commandArgs аргументы команды
     */
    public CommandToSend(String commandName, String[] commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }

    /**
     * Получает имя команды.
     * @return имя команды
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Получает аргументы команды.
     * @return аргументы команды
     */
    public String[] getCommandArgs() {
        return commandArgs;
    }


}
