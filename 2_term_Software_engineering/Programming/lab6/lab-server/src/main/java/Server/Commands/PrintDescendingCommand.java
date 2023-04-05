package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Команда, выводящая элементы коллекции в порядке убывания.
 */
public class PrintDescendingCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", " вывести элементы коллекции в порядке убывания", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение команды вывода элементов в порядке убывания.
     * @param request объект запроса
     * @return объект типа Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else
            return new Response(collectionManager.printDescending());
    }
}

