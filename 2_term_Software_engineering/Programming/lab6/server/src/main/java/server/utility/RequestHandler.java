package server.utility;

import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;

/**
 * Обработчик запросов.
 */
public class RequestHandler {

    /**
     * Объект CommandManager, управляющий выполнением команд
     */
    private final CommandManager commandManager;

    /**
     * Конструктор класса RequestHandler.
     *
     * @param commandManager менеджер команд.
     */
    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Обрабатывает запрос и возвращает ответ.
     * @param request запрос от клиента.
     * @return ответ сервера на запрос.
     */
    public Response handle(Request request){
        commandManager.addToHistory(request.getCommandName());
        ResponseResult responseResult = executeCommand(
                request.getCommandName(),
                request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseResult, ResponseOutputer.getAndClear());
    }

    /**
     * Выполняет команду с заданными аргументами.
     * @param command имя команды.
     * @param commandStringArgument строковый аргумент команды.
     * @param commandObjectArgument объектный аргумент команды.
     * @return результат выполнения команды.
     */
    private ResponseResult executeCommand(String command, String commandStringArgument, Object commandObjectArgument) {
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "info":
                if (!commandManager.info(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "update":
                if (!commandManager.update(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "remove_first":
                if (!commandManager.removeFirst(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                return ResponseResult.ERROR;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "sort":
                if (!commandManager.sort(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "shuffle":
                if (!commandManager.shuffle(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "history":
                if (!commandManager.history(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "print_field_descending_annual_turnover":
                if (!commandManager.printFieldDescendingAnnualTurnover(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "print_descending":
                if (!commandManager.printDescending(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "print_unique_employees_count":
                if (!commandManager.printUniqueEmployeesCount(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "server_exit":
                if (!commandManager.serverExit(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                return ResponseResult.SERVER_EXIT;
            default:
                ResponseOutputer.appendLn("Команда '" + command + "' не найдена. Напишите 'help' для просмотра всех доступных команд.");
                return ResponseResult.ERROR;
        }
        return ResponseResult.OK;
    }
}
