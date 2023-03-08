package managers;

import commands.ICommand;
import exceptions.HistoryIsEmptyException;

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
     */
    public CommandManager(ICommand addCommand, ICommand clearCommand, ICommand executeScriptCommand, ICommand exitCommand, ICommand helpCommand,ICommand historyCommand, ICommand infoCommand, ICommand printDescendingCommand, ICommand printFieldDescendingAnnualTurnoverCommand, ICommand printUniqueEmployeesCountCommand, ICommand removeByIdCommand, ICommand removeFirstCommand, ICommand saveCommand, ICommand showCommand, ICommand shuffleCommand, ICommand sortCommand,  ICommand updateCommand) {
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

        commands = new ArrayList<>(Arrays.asList(helpCommand, infoCommand, historyCommand, showCommand, addCommand, updateCommand, removeByIdCommand, clearCommand, saveCommand, executeScriptCommand, exitCommand, removeFirstCommand, shuffleCommand, sortCommand, printDescendingCommand, printUniqueEmployeesCountCommand, printFieldDescendingAnnualTurnoverCommand));
    }


    /**
     * Добавляет команду в историю использованных команд.
     * @param commandToStore команда для сохранения в истории
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
     * Выводит сообщение об ошибке, если команда не найдена.
     * @param command название не найденной команды
     * @return логическое значение false если не найдена
     */
    public boolean noSuchCommand(String command) {
        Console.printLn("Команда '" + command + "' не найдена. Напишите 'help' для просмотра всех доступных команд.");
        return false;
    }


    /**
     * Выводит список доступных команд или подробную информацию о конкретной команде.
     * @param argument аргумент для команды
     * @return логическое значение false, если команда help не была выполнена успешно
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
     * Выводит информацию о коллекции.
     * @param argument аргумент для команды
     * @return результат выполнения команды info
     */
    public boolean info(String argument) {
        return infoCommand.execute(argument);
    }


    /**
     * Выводит все элементы коллекции.
     * @param argument аргумент для команды
     * @return результат выполнения команды show
     */
    public boolean show(String argument) {
        return showCommand.execute(argument);
    }


    /**
     * Добавляет новый элемент в коллекцию.
     * @param argument аргумент для команды
     * @return результат выполнения команды add
     */
    public boolean add(String argument) {
        return addCommand.execute(argument);
    }


    /**
     * Обновляет значение элемента коллекции по его id.
     * @param argument аргумент для команды
     * @return результат выполнения команды update
     */
    public boolean update(String argument) {
        return updateCommand.execute(argument);
    }


    /**
     * Выполняет команду сортировки элементов коллекции
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean sort(String argument) {
        return sortCommand.execute(argument);
    }


    /**
     * Очищает коллекцию
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean clear(String argument) {
        return clearCommand.execute(argument);
    }


    /**
     * Сохраняет коллекцию в файл
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean save(String argument) {
        return saveCommand.execute(argument);
    }


    /**
     * Выполняет команду выхода из программы
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean exit(String argument) {
        return exitCommand.execute(argument);
    }


    /**
     * Выполняет скрипт из указанного файла
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean executeScript(String argument) {
        return executeScriptCommand.execute(argument);
    }


    /**
     * Выполняет перемешивание элементов коллекции
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean shuffle(String argument) {
        return shuffleCommand.execute(argument);
    }

    /**
     * Выводит значения поля annualTurnover всех элементов коллекции в порядке убывания
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean printFieldDescendingAnnualTurnover(String argument) {
        return printFieldDescendingAnnualTurnoverCommand.execute(argument);
    }

    /**
     * Выводит элементы коллекции в порядке убывания
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean printDescending(String argument) {
        return printDescendingCommand.execute(argument);
    }

    /**
     * Выводит уникальные значения поля employeesCount элементов коллекции
     * @param argument аргумент для команды
     * @return true, если команда успешно выполнена, иначе false
     */
    public boolean printUniqueEmployeesCountCommand(String argument) {
        return printUniqueEmployeesCountCommand.execute(argument);
    }

    /**
     * Удаляет первый элемент из коллекции
     *
     * @param argument аргумент для команды
     * @return ответная реакция
     */
    public boolean removeFirst(String argument){
        return removeFirstCommand.execute(argument);
    }

    /**
     * Удаляет элемент коллекции по заданному идентификатору.
     * @param argument строковое значение идентификатора элемента.
     * @return результат выполнения команды.
     */
    public boolean removeById(String argument) {
        return removeByIdCommand.execute(argument);
    }

    /**
     * Выводит историю выполненных команд.
     * @param argument аргумент команды (не используется).
     * @return результат выполнения команды.
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
     * Возвращает строковое представление класса.
     * @return строковое представление класса.
     */
    @Override
    public String toString() {
        return "CommandManager (вспомогательный класс для работы с командами)";
    }
}