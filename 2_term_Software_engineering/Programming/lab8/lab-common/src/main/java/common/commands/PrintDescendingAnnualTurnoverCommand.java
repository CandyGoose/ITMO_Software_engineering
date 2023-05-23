package common.commands;

import common.util.CollectionManager;
import common.exceptions.InvalidRequestException;
import common.network.Request;
import common.network.Response;
import common.util.TerminalColors;

/**
 * Команда вывода в обратном порядке значений годового оборота
 */
public class PrintDescendingAnnualTurnoverCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public PrintDescendingAnnualTurnoverCommand(CollectionManager col) {
        super("print_field_descending_annual_turnover ");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("print_field_descending_annual_turnover ", TerminalColors.GREEN)
             + " - вывести значения поля annualTurnover всех элементов в порядке убывания";
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

        for (Float annualTurnover : col.getDescendingAnnualTurnover()) {
            sb.append(annualTurnover.toString() + '\n');
        }

        return new Response(sb.toString());
    }
}
