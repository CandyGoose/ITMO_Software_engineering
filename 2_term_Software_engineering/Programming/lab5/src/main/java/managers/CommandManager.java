package managers;

import commands.ICommand;
import exceptions.HistoryIsEmptyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Класс CommandManager управляет всеми командами в CLI
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

    /**
     * Задает объекты для работы с командами
     */
    public CommandManager(ICommand addCommand, ICommand clearCommand, ICommand executeScriptCommand, ICommand exitCommand, ICommand historyCommand, ICommand infoCommand, ICommand printFieldDescendingAnnualTurnoverCommand, ICommand printDescendingCommand, ICommand printUniqueEmployeesCountCommand, ICommand removeFirstCommand, ICommand removeByIdCommand, ICommand sortCommand, ICommand saveCommand, ICommand showCommand, ICommand shuffleCommand, ICommand updateCommand, ICommand helpCommand) {
        this.addCommand = addCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.historyCommand = historyCommand;
        this.infoCommand = infoCommand;
        this.printFieldDescendingAnnualTurnoverCommand = printFieldDescendingAnnualTurnoverCommand;
        this.printDescendingCommand = printDescendingCommand;
        this.printUniqueEmployeesCountCommand = printUniqueEmployeesCountCommand;
        this.removeFirstCommand = removeFirstCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.sortCommand = sortCommand;
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.shuffleCommand = shuffleCommand;
        this.updateCommand = updateCommand;
        this.helpCommand = helpCommand;

        commands = new ArrayList<>(Arrays.asList(helpCommand, infoCommand, historyCommand, showCommand, addCommand, updateCommand, removeByIdCommand, clearCommand, saveCommand, executeScriptCommand, exitCommand, removeFirstCommand, shuffleCommand, sortCommand, printDescendingCommand, printUniqueEmployeesCountCommand, printFieldDescendingAnnualTurnoverCommand));
    }


    /**
     * Добавляет команды в историю
     *
     * @param commandToStore введенная команда
     */
    public void addToHistory(String commandToStore) {

        for (ICommand command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE-1; i>0; i--) {
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }


    /**
     * Вывод сообщения, если команда не найдена
     *
     * @param command Команда, которая не найдена
     * @return boolean value
     */
    public boolean noSuchCommand(String command) {
        Console.printLn("Команда '" + command + "' не найдена. Напишите 'help' для просмотра всех доступных команд.");
        return false;
    }


    /**
     * Выводит список всех команд
     *
     * @param argument Аргумент, переданный команде help
     * @return boolean value
     */
    public boolean help(String argument) {
        if (!helpCommand.execute(argument)) {
            for (ICommand command : commands) {
                Console.printLn(command.getName() + ": " + command.getDescription());
            }
            return true;
        } else return false;
    }


    /**
     * Эта функция вызывается, когда пользователь вводит "info" в командной строке
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean info(String argument) {
        return infoCommand.execute(argument);
    }


    /**
     * Эта функция вызывается, когда пользователь вводит "show" в командной строке
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean show(String argument) {
        return showCommand.execute(argument);
    }


    /**
     * Эта функция вызывается, когда пользователь вводит "add" в командной строке
     *
     * @param argument аргумент для добавления в список
     * @return ответная реакция
     */
    public boolean add(String argument) {
        return addCommand.execute(argument);
    }


    /**
     * Метод update принимает строковый аргумент и возвращает логическое значение
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean update(String argument) {
        return updateCommand.execute(argument);
    }


    /**
     * Удаляет организацию из коллекции по ее идентификатору
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean sort(String argument) {
        return sortCommand.execute(argument);
    }


    /**
     * Очищает текущую коллекцию
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean clear(String argument) {
        return clearCommand.execute(argument);
    }


    /**
     * Функция save принимает строковый аргумент и возвращает логическое значение
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean save(String argument) {
        return saveCommand.execute(argument);
    }


    /**
     * Функция exit - это метод, который принимает строковый аргумент
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean exit(String argument) {
        return exitCommand.execute(argument);
    }


    /**
     * Исполняет скрипт
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean executeScript(String argument) {
        return executeScriptCommand.execute(argument);
    }


    /**
     * Функция shuffle принимает строковый аргумент и возвращает логическое значение
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean shuffle(String argument) {
        return shuffleCommand.execute(argument);
    }

    /**
     * Выводит числа в порядке возрастания
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean printFieldDescendingAnnualTurnover(String argument) {
        return printFieldDescendingAnnualTurnoverCommand.execute(argument);
    }

    /**
     * Выводит список элементов в порядке убывания
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean printDescending(String argument) {
        return printDescendingCommand.execute(argument);
    }

    /**
     * Выводит уникальные поля
     *
     * @return ответная реакция
     */
    public boolean printUniqueEmployeesCountCommand(String argument) {
        return printUniqueEmployeesCountCommand.execute(argument);
    }

    /**
     * Удаляет элемент с указанным индексом
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean removeFirst(String argument){
        return removeFirstCommand.execute(argument);
    }

    /**
     * Удаляет организацию из коллекции по ее идентификатору
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean removeById(String argument) {
        return removeByIdCommand.execute(argument);
    }

    /**
     * Выводит последние использованные команды
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean history(String argument) {
        if (!historyCommand.execute(argument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                Console.printLn("Последние использованные команды:");
                for (String s : commandHistory) {
                    if (s != null) Console.printLn(" " + s);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                Console.printLn("Вы еще не ввели ни одной команды.");
            }
        }
        return false;
    }


    /**
     * Эта функция возвращает строку, описывающую класс
     *
     * @return "CommandManager (вспомогательный класс для работы с командами)"
     */
    @Override
    public String toString() {
        return "CommandManager (вспомогательный класс для работы с командами)";
    }
}