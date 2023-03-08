package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Завершает работу программы
 */
public class ExitCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * Создает новый объект команды с указанием имени и описания.
     */
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Метод execute представляет реализацию команды exit, которая завершает работу программы.
     *
     * @param argument строка аргументов команды.
     * @return Метод возвращает true, если выполнение команды прошло успешно, иначе - false.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn("До свидания!");
            System.exit(0);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Использование '" + getName() + "'");
        }
        return false;
    }
}
