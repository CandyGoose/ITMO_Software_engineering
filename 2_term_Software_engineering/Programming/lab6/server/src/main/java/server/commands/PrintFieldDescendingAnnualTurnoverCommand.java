package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Выводит все значения годового оборота организаций в системе в порядке убывания
 */
public class PrintFieldDescendingAnnualTurnoverCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса PrintFieldDescendingAnnualTurnoverCommand.
     * @param collectionManager менеджер коллекции.
     */
    public PrintFieldDescendingAnnualTurnoverCommand(CollectionManager collectionManager) {
        super("print_field_descending_annual_turnover","", "вывести значения поля annualTurnover всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }
    /**
     * Команда вывода всех полей годового оборота в обратном порядке.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ArrayList<Float> annualTurnover = new ArrayList<>(collectionManager.getListAnnualTurnover()) {
            };
            annualTurnover.sort(Collections.reverseOrder());
            StringBuilder output = new StringBuilder();
            if (annualTurnover.isEmpty()) output.append("Нет значений.");
            else output.append("Все значения поля annualTurnover в порядке убывания: " +  annualTurnover.toString().replaceAll("^\\[|\\]$", ""));

            ResponseOutputer.appendLn(output);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
