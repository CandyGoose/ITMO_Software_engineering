package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Исполняет скрипт
 */
public class ExecuteScriptCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * Создает новый объект команды с указанием имени и описания.
     */
    public ExecuteScriptCommand() {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла (в скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме)");
    }

    /**
     * Метод execute запускает выполнение скрипта с указанным именем файла.
     * Если аргумент не указан, выбрасывает исключение WrongAmountOfElementsException.
     * Перед выполнением скрипта выводит сообщение о запуске скрипта.
     * @param argument аргумент команды (имя файла скрипта)
     * @return true, если выполнение успешно, иначе false
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn("Выполнение скрипта '" + argument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Использование '" + getName() + "'");
        }
        return false;
    }
}
