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
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса PrintFieldDescendingAnnualTurnoverCommand.
     * @param collectionManager менеджер коллекции.
     */
    public PrintFieldDescendingAnnualTurnoverCommand(CollectionManager collectionManager) {
        super("print_field_descending_annual_turnover", "вывести значения поля annualTurnover всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит все поля annualTurnover организаций в системе в порядке убывания.
     * Сортирует список объектов annualTurnover в обратном порядке с помощью вызова метода sort() класса Collections,
     * который принимает в качестве параметра объект, реализующий интерфейс Comparator.
     * Используется статический метод reverseOrder() класса Collections, который возвращает компаратор, сравнивающий
     * элементы в обратном порядке.
     *
     * @param argument строка аргументов команды
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
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
            Console.printError("Использование аргумента '" + argument + "' в команде '" + getName() + "'");
        }
        return false;
    }
}
