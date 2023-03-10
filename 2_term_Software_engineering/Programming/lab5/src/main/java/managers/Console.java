package managers;

import exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Класс для работы с консолью.
 */
public class Console {

    /**
     * Приглашение к вводу команды.
     */
    public static final String PS = "$ ";

    /**
     Менеджер команд.
     */
    private final CommandManager commandManager;

    /**
     Сканер для чтения пользовательского ввода.
     */
    private final Scanner userScanner;

    /**
     Объект, задающий вопросы пользователю для ввода информации об организации.
     */
    private final OrganizationAsker organizationAsker;

    /**
     Список строк, содержащих команды, которые были запущены из текущего скрипта.
     */
    private final List<String> scriptStack = new ArrayList<>();

    /**
     * Конструктор для создания объекта для работы с консолью.
     * @param commandManager объект для выполнения команд.
     * @param userScanner сканер для ввода данных пользователя.
     * @param organizationAsker объект для получения ввода данных организации.
     */
    public Console(CommandManager commandManager, Scanner userScanner, OrganizationAsker organizationAsker) {
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.organizationAsker = organizationAsker;
    }

    /**
     * Функция для выполнения скрипта.
     * @param argument имя файла скрипта.
     * @return статус выполнения команды.
     */
    public int scriptMode(String argument) {
        String[] userCommand;
        int commandStatus;
        scriptStack.add(argument); // добавление скрипта в стек скриптов, чтобы отслеживать рекурсию скриптов
        try (Scanner scriptScanner = new Scanner(new File(argument))) { // открытие файла скрипта для чтения
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = organizationAsker.getUserScanner(); // установка сканера ввода скрипта, чтобы все последующие команды в скрипте читались из этого сканера ввода
            organizationAsker.setUserScanner(scriptScanner);
            organizationAsker.setScriptMode();
            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2); // чтение очередной строки скрипта и разбиение ее на команду и аргументы
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                Console.printLn(Console.PS + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) { // проверка на наличие рекурсии в скрипте, если текущая команда
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == 0 && scriptScanner.hasNextLine());
            organizationAsker.setUserScanner(tmpScanner);
            organizationAsker.setUserMode();
            if (commandStatus == 1 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty()))
                Console.printLn("Проверьте скрипт на верность данных.");
            return commandStatus;
        } catch (FileNotFoundException exception) {
            Console.printError("Файл не найден или доступ запрещен.");
        } catch (NoSuchElementException exception) {
            Console.printError("Файл пуст.");
        } catch (ScriptRecursionException exception) {
            Console.printError("Скрипты не могут рекурсивно ссылаться друг на друга.");
        } catch (IllegalStateException exception) {
            Console.printError("Непредвиденная ошибка.");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size()-1); // удаление текущего скрипта из стека скриптов, чтобы позволить выполнение других скриптов
        }
        return 1;
    }

    /**
     * Класс, реализующий интерактивный режим работы с консолью. Он будет продолжать запрашивать у пользователя ввод
     * до тех пор, пока пользователь не введет "exit"
     */
    public void interactiveMode() {
        String[] userCommand;
        int commandStatus;
        try {
            do {
                Console.print(PS);
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandManager.addToHistory(userCommand[0]);
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != 2);
        } catch (NoSuchElementException exception) {
            Console.printError("Работа программы прекращена.");
        } catch (IllegalStateException exception) {
            Console.printError("Непредвиденная ошибка.");
        }
    }


    /**
     * Функция для вывода объекта в стандартный поток
     * @param toOut объект для вывода в консоль
     */
    public static void print(Object toOut){
        System.out.print(toOut);
    }


    /**
     * Функция для вывода объекта в консоль с переносом строки
     * @param toOut объект для вывода в консоль
     */
    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }


    /**
     * Функция для вывода ошибки в консоль
     * @param toOut объект для вывода в консоль
     */
    public static void printError(Object toOut) {
        System.out.println("Ошибка: " + toOut);
    }


    /**
     * Запускает команду, введенную пользователем
     * @param userCommand массив строк, содержащий имя команды и ее аргументы
     * @return 0, если команда была успешно выполнена, 1, если выполнение команды не удалось, и 2, если была
     * выполнена команда выхода
     */
    private int launchCommand(String[] userCommand) {
        switch (userCommand[0]) {
            case "":
                break;
            case "help":
                if (!commandManager.help(userCommand[1])) return 1;
                break;
            case "info":
                if (!commandManager.info(userCommand[1])) return 1;
                break;
            case "show":
                if (!commandManager.show(userCommand[1])) return 1;
                break;
            case "add":
                if (!commandManager.add(userCommand[1])) return 1;
                break;
            case "update":
                if (!commandManager.update(userCommand[1])) return 1;
                break;
            case "sort":
                if (!commandManager.sort(userCommand[1])) return 1;
                break;
            case "remove_first":
                if (!commandManager.removeFirst(userCommand[1])) return 1;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(userCommand[1])) return 1;
                break;
            case "clear":
                if (!commandManager.clear(userCommand[1])) return 1;
                break;
            case "save":
                if (!commandManager.save(userCommand[1])) return 1;
                break;
            case "execute_script":
                if (!commandManager.executeScript(userCommand[1])) return 1;
                else return scriptMode(userCommand[1]);
            case "shuffle":
                if (!commandManager.shuffle(userCommand[1])) return 1;
                break;
            case "history":
                if (!commandManager.history(userCommand[1])) return 1;
                break;
            case "print_field_descending_annual_turnover":
                if (!commandManager.printFieldDescendingAnnualTurnover(userCommand[1])) return 1;
                break;
            case "print_descending":
                if (!commandManager.printDescending(userCommand[1])) return 1;
                break;
            case "print_unique_employees_count":
                if (!commandManager.printUniqueEmployeesCountCommand(userCommand[1])) return 1;
                break;
            case "exit":
                if (!commandManager.exit(userCommand[1])) return 1;
                else return 2;
            default:
                if (!commandManager.noSuchCommand(userCommand[0])) return 1;
        }
        return 0;
    }
}