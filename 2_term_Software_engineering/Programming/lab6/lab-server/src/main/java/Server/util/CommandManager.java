package Server.util;

import Common.util.Request;
import Server.Commands.AbstractCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Менеджер команд.
 */
public class CommandManager {

    /**
     * Список доступных команд.
     * Ключ - название команды, значение - объект команды.
     */
    public static final Map<String, AbstractCommand> AVAILABLE_COMMANDS = new HashMap<>();

    /**
     * Создает новый объект менеджера команд.
     * @param addCommand - команда добавления элемента в коллекцию
     * @param clearCommand - команда очистки коллекции
     * @param executeScriptCommand - команда исполнения скрипта
     * @param exitCommand - команда выхода из приложения
     * @param helpCommand - команда вывода справки по доступным командам
     * @param infoCommand - команда вывода информации о коллекции
     * @param printDescendingCommand - команда вывода элементов коллекции в порядке убывания
     * @param printFieldDescendingAnnualTurnoverCommand - команда вывода значения поля annualTurnover элементов коллекции в порядке убывания
     * @param printUniqueEmployeesCountCommand - команда вывода количества уникальных значений поля employeesCount элементов коллекции
     * @param removeByIdCommand - команда удаления элемента коллекции по заданному id
     * @param removeFirstCommand - команда удаления первого элемента коллекции
     * @param showCommand - команда вывода всех элементов коллекции
     * @param shuffleCommand - команда перемешивания элементов коллекции
     * @param sortCommand - команда сортировки элементов коллекции в естественном порядке
     * @param updateByIdCommand - команда обновления значения элемента коллекции по заданному id
     */
    public CommandManager(AbstractCommand addCommand, AbstractCommand clearCommand, AbstractCommand executeScriptCommand, AbstractCommand exitCommand, AbstractCommand helpCommand, AbstractCommand infoCommand, AbstractCommand printDescendingCommand, AbstractCommand printFieldDescendingAnnualTurnoverCommand, AbstractCommand printUniqueEmployeesCountCommand, AbstractCommand removeByIdCommand, AbstractCommand removeFirstCommand, AbstractCommand showCommand, AbstractCommand shuffleCommand, AbstractCommand sortCommand, AbstractCommand updateByIdCommand) {
        AVAILABLE_COMMANDS.put(addCommand.getName(), addCommand);
        AVAILABLE_COMMANDS.put(clearCommand.getName(), clearCommand);
        AVAILABLE_COMMANDS.put(executeScriptCommand.getName(), executeScriptCommand);
        AVAILABLE_COMMANDS.put(exitCommand.getName(), exitCommand);
        AVAILABLE_COMMANDS.put(helpCommand.getName(), helpCommand);
        AVAILABLE_COMMANDS.put(infoCommand.getName(), infoCommand);
        AVAILABLE_COMMANDS.put(printDescendingCommand.getName(), printDescendingCommand);
        AVAILABLE_COMMANDS.put(printFieldDescendingAnnualTurnoverCommand.getName(), printFieldDescendingAnnualTurnoverCommand);
        AVAILABLE_COMMANDS.put(printUniqueEmployeesCountCommand.getName(), printUniqueEmployeesCountCommand);AVAILABLE_COMMANDS.put(showCommand.getName(), showCommand);
        AVAILABLE_COMMANDS.put(removeByIdCommand.getName(), removeByIdCommand);
        AVAILABLE_COMMANDS.put(removeFirstCommand.getName(), removeFirstCommand);
        AVAILABLE_COMMANDS.put(shuffleCommand.getName(), shuffleCommand);
        AVAILABLE_COMMANDS.put(sortCommand.getName(), sortCommand);
        AVAILABLE_COMMANDS.put(updateByIdCommand.getName(), updateByIdCommand);
    }

    /**
     * Метод для получения объекта команды по имени команды из запроса.
     * @param request запрос от клиента
     * @return объект команды, соответствующий имени команды из запроса
     */
    public AbstractCommand initCommand(Request request) {
        String commandName = request.getCommandName();
        return AVAILABLE_COMMANDS.get(commandName);
    }
}

