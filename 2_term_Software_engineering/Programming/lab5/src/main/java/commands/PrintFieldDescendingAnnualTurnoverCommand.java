package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Выводит все значения годового оборота организаций в системе в порядке убывания
 */
public class PrintFieldDescendingAnnualTurnoverCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingAnnualTurnoverCommand(CollectionManager collectionManager) {
        super("print_field_descending_annual_turnover", "вывести значения поля annualTurnover всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит все типы организаций в системе в порядке убывания
     *
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            ArrayList<Float> annualTurnover = new ArrayList<Float>(collectionManager.getListAnnualTurnover()) {
            };
            annualTurnover.sort(Collections.reverseOrder());
            if (annualTurnover.isEmpty()) Console.printLn("Нет значений.");
            else Console.printLn("Все значения поля annualTurnover в порядке убывания: " + annualTurnover.toString().replaceAll("^\\[|\\]$", ""));
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование '" + argument + "' в " + getName());
        }
        return false;
    }
}
