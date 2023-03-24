package client.utility;

import client.App;
import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;
import common.exceptions.CommandUsageException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.ScriptRecursionException;
import common.interaction.OrganizationRaw;
import common.interaction.Request;
import common.interaction.ResponseResult;
import common.utility.Outputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Обработчик ввода пользователем команд. Обрабатывает ввод из консоли и скриптов.
 */
public class UserHandler {

    /**
     * Сканнер для чтения пользовательского ввода из консоли.
     */
    private Scanner userScanner;

    /**
     * Стек файлов скриптов для проверки рекурсии.
     */
    private final Stack<File> scriptStack = new Stack<>();

    /**
     * Стек сканеров для чтения из файлов скриптов.
     */
    private final Stack<Scanner> scannerStack = new Stack<>();

    /**
     * Конструктор обработчика пользовательского ввода.
     * @param userScanner сканнер для чтения пользовательского ввода из консоли.
     */
    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Обработка пользовательского ввода и генерация запроса к серверу.
     * @param serverResponseResult результат предыдущего запроса сервера.
     * @return запрос к серверу.
     */
    public Request handle(ResponseResult serverResponseResult) {
        String userInput;
        String[] userCommand;
        ProcessingResult processingResult;
        int rewriteAttempts = 0;
        try{
            do{
                try{
                    if(fileMode() && (serverResponseResult == ResponseResult.ERROR ||
                            serverResponseResult == ResponseResult.SERVER_EXIT))
                        throw new IncorrectInputInScriptException();
                    while(fileMode() && !userScanner.hasNextLine()){
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        Outputer.printLn("Возвращение к скрипту '" + scriptStack.pop().getName() + "'...");
                    }
                    if(fileMode()){
                        userInput = userScanner.nextLine();
                        if(!userInput.isEmpty()){
                            Outputer.print(App.PS1);
                            Outputer.printLn(userInput);
                        }
                    } else {
                        Outputer.print(App.PS1);
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Outputer.println();
                    Outputer.printError("Произошла ошибка при вводе команды.");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    int maxRewriteAttempts = 3;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputer.printError("Превышено количество попыток ввода.");
                        System.exit(0);
                    }
                }
                processingResult = processCommand(userCommand[0], userCommand[1]);
            } while (processingResult == ProcessingResult.ERROR && !fileMode() || userCommand[0].isEmpty());
            try{
                if(fileMode() && (serverResponseResult == ResponseResult.ERROR || processingResult == ProcessingResult.ERROR))
                    throw new IncorrectInputInScriptException();
                switch (processingResult){
                    case OBJECT:
                        OrganizationRaw organizationAddRaw = generateOrganizationAdd();
                        return new Request(userCommand[0], userCommand[1], organizationAddRaw);
                    case UPDATE_OBJECT:
                        OrganizationRaw organizationUpdateRaw = generateOrganizationUpdate();
                        return new Request(userCommand[0], userCommand[1], organizationUpdateRaw);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if(!scriptFile.exists()) throw new FileNotFoundException();
                        if(!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Outputer.printLn("Выполнение скрипта '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception){
                Outputer.printError("Файл со скриптом не найден.");
            } catch (ScriptRecursionException exception){
                Outputer.printError("Скрипты не могут вызываться рекурсивно.");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception){
            Outputer.printError("Выполнение скрипта прервано.");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    /**
     * Обрабатывает команду и ее аргументы
     * @param command команда
     * @param commandArgument аргумент команды
     * @return результат обработки команды
     */
    private ProcessingResult processCommand(String command, String commandArgument) {
        try{
            switch (command){
                case "":
                    return ProcessingResult.ERROR;
                case "add", "print_field_descending_annual_turnover", "clear", "exit", "help", "history", "info", "print_descending", "print_unique_employees_count", "remove_first", "server_exit", "show", "sort", "shuffle":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingResult.SCRIPT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingResult.UPDATE_OBJECT;
                default:
                    Outputer.printLn("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessingResult.ERROR;
            }
        } catch (CommandUsageException exception){
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputer.printLn("Применение: '" + command + "'");
            return ProcessingResult.ERROR;
        }
        return ProcessingResult.OK;
    }

    /**
     * Генерирует объект Организация на основе введенных пользователем данных в режиме добавления.
     * @throws IncorrectInputInScriptException если происходит ошибка ввода в скрипте
     * @return объект Организация
     */
    private OrganizationRaw generateOrganizationAdd() throws IncorrectInputInScriptException {
        OrganizationAsker organizationAsker = new OrganizationAsker(userScanner);
        if (fileMode()) organizationAsker.setScriptMode();
        return new OrganizationRaw(
                organizationAsker.askName(),
                organizationAsker.askCoordinates(),
                organizationAsker.askAnnualTurnover(),
                organizationAsker.askFullName(),
                organizationAsker.askEmployeesCount(),
                organizationAsker.askOrganizationType(),
                organizationAsker.askAddress()
        );
    }

    /**
     * Генерирует объект Организация на основе введенных пользователем данных в режиме изменения.
     * @throws IncorrectInputInScriptException если происходит ошибка ввода в скрипте
     * @return объект Организация
     */
    private OrganizationRaw generateOrganizationUpdate() throws IncorrectInputInScriptException {
        OrganizationAsker organizationAsker = new OrganizationAsker(userScanner);
        if (fileMode()) organizationAsker.setScriptMode();
        String name = organizationAsker.askQuestion("Вы хотите изменить имя организации?") ?
                organizationAsker.askName() : null;
        Coordinates coordinates = organizationAsker.askQuestion("Вы хотите изменить координаты организации?") ?
                organizationAsker.askCoordinates() : null;
        Float annualTurnover = organizationAsker.askQuestion("Вы хотите изменить годовой оборот организации?") ?
                organizationAsker.askAnnualTurnover() : -1;
        String fullName = organizationAsker.askQuestion("Вы хотите изменить полное имя организации?") ?
                organizationAsker.askFullName() : null;
        long employeesCount = organizationAsker.askQuestion("Вы хотите изменить количество сотрудников в организации?") ?
                organizationAsker.askEmployeesCount() : -1;
        OrganizationType organizationType = organizationAsker.askQuestion("Вы хотите изменить тип организации?") ?
                organizationAsker.askOrganizationType() : null;
        Address address = organizationAsker.askQuestion("Вы хотите изменить адрес организации?") ?
                organizationAsker.askAddress() : null;
        return new OrganizationRaw(
                name,
                coordinates,
                annualTurnover,
                fullName,
                employeesCount,
                organizationType,
                address
        );
    }

    /**
     * Проверяет, является ли режим работы программы файловым.
     * @return true, если режим работы программы файловый, иначе false
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}