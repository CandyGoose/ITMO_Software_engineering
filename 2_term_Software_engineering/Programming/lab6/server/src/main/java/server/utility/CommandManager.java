package server.utility;

import common.exceptions.HistoryIsEmptyException;
import server.commands.ICommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс менеджера команд
 */
public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 10;
    private final String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private final List<ICommand> commands;
    private final ICommand addCommand;
    private final ICommand clearCommand;
    private final ICommand executeScriptCommand;
    private final ICommand exitCommand;
    private final ICommand historyCommand;
    private final ICommand infoCommand;
    private final ICommand printFieldDescendingAnnualTurnoverCommand;
    private final ICommand printDescendingCommand;
    private final ICommand printUniqueEmployeesCountCommand;
    private final ICommand removeFirstCommand;
    private final ICommand removeByIdCommand;
    private final ICommand sortCommand;
    private final ICommand saveCommand;
    private final ICommand showCommand;
    private final ICommand shuffleCommand;
    private final ICommand updateCommand;
    private final ICommand helpCommand;
    private final ICommand serverExitCommand;

    /**
     * Конструктор класса менеджера команд
     * @param addCommand команда добавления новой организации в коллекцию
     * @param clearCommand команда очистки коллекции
     * @param executeScriptCommand команда исполнения скрипта
     * @param exitCommand команда выхода из программы
     * @param helpCommand команда вывода справки
     * @param historyCommand команда вывода последних выполненных команд
     * @param infoCommand команда вывода информации о коллекции
     * @param printDescendingCommand команда вывода элементов коллекции в порядке убывания
     * @param printFieldDescendingAnnualTurnoverCommand команда вывода значений поля annualTurnover элементов коллекции в порядке убывания
     * @param printUniqueEmployeesCountCommand команда вывода уникальных значений поля employeesCount элементов коллекции
     * @param removeByIdCommand команда удаления элемента коллекции по заданному id
     * @param removeFirstCommand команда удаления первого элемента коллекции
     * @param saveCommand команда сохранения коллекции в файл
     * @param showCommand команда вывода всех элементов коллекции
     * @param shuffleCommand команда перемешивания элементов коллекции
     * @param sortCommand команда сортировки элементов коллекции
     * @param updateCommand команда обновления значений элемента коллекции по заданному id
     * @param serverExitCommand команда завершения работы сервера
     */
    public CommandManager(ICommand addCommand, ICommand clearCommand, ICommand executeScriptCommand, ICommand exitCommand, ICommand helpCommand,ICommand historyCommand, ICommand infoCommand, ICommand printDescendingCommand, ICommand printFieldDescendingAnnualTurnoverCommand, ICommand printUniqueEmployeesCountCommand, ICommand removeByIdCommand, ICommand removeFirstCommand, ICommand saveCommand, ICommand showCommand, ICommand shuffleCommand, ICommand sortCommand,  ICommand updateCommand, ICommand serverExitCommand) {
        this.addCommand = addCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.helpCommand = helpCommand;
        this.historyCommand = historyCommand;
        this.infoCommand = infoCommand;
        this.printDescendingCommand = printDescendingCommand;
        this.printFieldDescendingAnnualTurnoverCommand = printFieldDescendingAnnualTurnoverCommand;
        this.printUniqueEmployeesCountCommand = printUniqueEmployeesCountCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.removeFirstCommand = removeFirstCommand;
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.shuffleCommand = shuffleCommand;
        this.sortCommand = sortCommand;
        this.updateCommand = updateCommand;
        this.serverExitCommand = serverExitCommand;

        commands = new ArrayList<>(Arrays.asList(helpCommand, infoCommand, historyCommand, showCommand, addCommand, updateCommand, removeByIdCommand, clearCommand, saveCommand, executeScriptCommand, exitCommand, removeFirstCommand, shuffleCommand, sortCommand, printDescendingCommand, printUniqueEmployeesCountCommand, printFieldDescendingAnnualTurnoverCommand, serverExitCommand));
    }

    /**
     * Возвращает список команд менеджера
     * @return Список команд менеджера
     */
    public List<ICommand> getCommands() {
        return commands;
    }

    /**
     * Добавляет команду в историю использованных команд, обновляет историю последних команд,
     * добавляя в нее новую команду в начале массива и удаляя самую старую команду, которая располагалась в конце массива.
     * @param commandToStore команда для сохранения в истории
     */
    public void addToHistory(String commandToStore) {

        for (ICommand command : commands) {
            if (command.getName().equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                    commandHistory[i] = commandHistory[i - 1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Выход из приложения.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean exit(String stringArgument, Object objectArgument) {
        return exitCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Вывод справки по доступным командам.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean help(String stringArgument, Object objectArgument) {
        if (helpCommand.execute(stringArgument, objectArgument)) {
            for (ICommand command : commands) {
                ResponseOutputer.appendTable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Вывод информации о коллекции.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean info(String stringArgument, Object objectArgument) {
        return infoCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Вывод всех элементов коллекции.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean show(String stringArgument, Object objectArgument) {
        return showCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Добавление нового элемента в коллекцию.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean add(String stringArgument, Object objectArgument) {
        return addCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Обновление значения элемента коллекции по его id.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean update(String stringArgument, Object objectArgument) {
        return updateCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Удаление элемента коллекции по его id.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean removeById(String stringArgument, Object objectArgument) {
        return removeByIdCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Выход из приложения.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean clear(String stringArgument, Object objectArgument) {
        return clearCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Очистка коллекции.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean save(String stringArgument, Object objectArgument) {
        return saveCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Выполняет скрипт.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean executeScript(String stringArgument, Object objectArgument) {
        return executeScriptCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Выводит историю введенных команд.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean history(String stringArgument, Object objectArgument) {
        if (historyCommand.execute(stringArgument, objectArgument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.appendLn("Последние использованные команды:");
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendLn(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendLn("Вы еще не ввели ни одной команды.");
            }
        }
        return false;
    }

    /**
     * Выводит элементы коллекции в порядке убывания.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean printDescending(String stringArgument, Object objectArgument) {
        return printDescendingCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Выводит значения поля annualTurnover всех элементов в порядке убывания.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean printFieldDescendingAnnualTurnover(String stringArgument, Object objectArgument) {
        return printFieldDescendingAnnualTurnoverCommand.execute(stringArgument, objectArgument);
    }
    /**
     * Выводит количество уникальных значений поля employeesCount всех элементов коллекции.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean printUniqueEmployeesCount(String stringArgument, Object objectArgument) {
        return printUniqueEmployeesCountCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Удаляет первый элемент из коллекции.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean removeFirst(String stringArgument, Object objectArgument) {
        return removeFirstCommand.execute(stringArgument, objectArgument);
    }
    /**
     * Перемешивает элементы коллекции в случайном порядке.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean shuffle(String stringArgument, Object objectArgument) {
        return shuffleCommand.execute(stringArgument, objectArgument);
    }
    /**
     * Сортирует элементы коллекции.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean sort(String stringArgument, Object objectArgument) {
        return sortCommand.execute(stringArgument, objectArgument);
    }
    /**
     * Завершает работу сервера.
     * @param stringArgument строковый аргумент команды
     * @param objectArgument объектный аргумент команды
     * @return результат выполнения команды
     */
    public boolean serverExit(String stringArgument, Object objectArgument) {
        save(stringArgument, objectArgument);
        return serverExitCommand.execute(stringArgument, objectArgument);
    }
}
