package common.commands;

import common.util.CollectionManager;
import common.exceptions.InvalidRequestException;
import common.network.Request;
import common.network.Response;
import common.util.TerminalColors;

/**
 * Команда вывода уникальных значений количества сотрудников
 */
public class PrintUniqueEmployeesCount extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public PrintUniqueEmployeesCount(CollectionManager col) {
        super("print_unique_employees_count");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("print_unique_employees_count", TerminalColors.GREEN)
                + " - вывести уникальные значения поля employeesCount всех элементов в коллекции";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     * @throws InvalidRequestException если запрос некорректен
     */
    @Override
    public Response execute(Request request) throws InvalidRequestException {
        StringBuilder sb = new StringBuilder();

        for (Long employeesCount : col.getUniqueEmployeesCount()) {
            sb.append(employeesCount.toString() + '\n');
        }

        return new Response(sb.toString());
    }
}
