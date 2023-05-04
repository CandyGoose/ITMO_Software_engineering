package Client.NetworkManager;

import Client.App;
import Common.util.Request;
import Common.util.RequestType;
import Common.util.Response;
import Common.util.TextWriter;


import java.io.Console;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Модуль авторизации пользователя в приложении.
 */
public class AuthorizationModule {
    /**
     * Сканер
     */
    private final Scanner scanner;

    /**
     * Считывание с консоли
     */
    Console console = System.console();

    /**
     * Режим авторизации
     */
    private boolean authorizationDone = false;

    /**
     * Создает новый объект модуля авторизации пользователя с помощью переданного объекта сканера.
     *
     * @param scanner объект сканера, используемый для ввода данных пользователя.
     */
    public AuthorizationModule(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * Запрашивает у пользователя информацию о необходимости регистрации в приложении и возвращает соответствующий запрос.
     *
     * @return запрос на регистрацию или вход в систему, созданный на основе ответа пользователя.
     */
    public Request askForRegistration() {
        TextWriter.printInfoMessage("У вас есть аккаунт? [y/n]");
        while (true) {
            try {
                String s = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                if ("y".equals(s)) {
                    return loginUser();
                } else if ("n".equals(s)) {
                    return registerUser();
                } else {
                    TextWriter.printErr("Вы ввели недопустимый символ, попробуйте еще раз.");
                }
            } catch (NoSuchElementException e) {
                TextWriter.printErr("Принудительное завершение работы.");
                System.exit(1);
            }
        }
    }

    /**
     * Проверяет результат запроса на регистрацию и выводит соответствующее сообщение.
     *
     * @param response объект ответа, содержащий информацию о результате запроса на регистрацию.
     */
    public void validateRegistration(Response response) {
        List<String> usersInfo = response.getInfo();
        if (usersInfo.size() == 2) {
            App.login = usersInfo.get(0);
            App.password = usersInfo.get(1);
            TextWriter.printInfoMessage(response.getMessageToResponse());
            setAuthorizationDone(true);
        } else {
            TextWriter.printErr(response.getMessageToResponse());
        }
    }

    /**
     * Запрашивает у пользователя данные для регистрации нового аккаунта и создает на их основе запрос на регистрацию.
     *
     * @return запрос на регистрацию нового аккаунта.
     * @throws NoSuchElementException если ввод пользователя был завершен преждевременно.
     */
    private Request registerUser() throws NoSuchElementException {
        TextWriter.printInfoMessage("Добро пожаловать в регистрации!");
        String login;
        String password;
        while (true) {
            TextWriter.printInfoMessage("Введите имя пользователя, которое вы будете использовать для работы с приложением (должно содержать не менее 5 символов).");
            while (true) {
                login = scanner.nextLine().trim();
                if (login.length() < 5) {
                    TextWriter.printErr("Имя пользователя слишком короткое, попробуйте еще раз.");
                    continue;
                }
                break;
            }
            TextWriter.printInfoMessage("Введите пароль, который вы будете использовать для работы с приложением (должно содержать не менее 5 символов).");
            while (true) {
                try {
                    // password = scanner.nextLine().trim();
                    char[] passwordChars = console.readPassword();
                    password = new String(passwordChars);
                    if (password.length() < 5) {
                        TextWriter.printErr("Пароль слишком короткий, попробуйте еще раз.");
                        continue;
                    }
                    return new Request(login, password, RequestType.REGISTER);
                } catch (NullPointerException e) {
                    TextWriter.printErr("Ошибка при работе с консолью.");
                    System.exit(1);
                }
            }
        }
    }

    /**
     * Метод для авторизации пользователя в приложении.
     * @return возвращает объект типа Request, содержащий данные пользователя, если авторизация прошла успешно
     * @throws NoSuchElementException если ввод данных пользователя был прерван
     */
    private Request loginUser() throws NoSuchElementException {
        TextWriter.printInfoMessage("Добро пожаловать в авторизацию!");
        String login;
        String password;
        while (true) {
            TextWriter.printInfoMessage("Введите имя пользователя, которое вы будете использовать для работы с приложением (должно содержать не менее 5 символов).");
            while (true) {
                login = scanner.nextLine().trim();
                if (login.length() < 5) {
                    TextWriter.printErr("Имя пользователя слишком короткое, попробуйте еще раз.");
                    continue;
                }
                break;
            }
            TextWriter.printInfoMessage("Введите пароль, который вы будете использовать для работы с приложением (должно содержать не менее 5 символов).");
            while (true) {
                try {
                    // password = scanner.nextLine().trim();
                    char[] passwordChars = console.readPassword();
                    password = new String(passwordChars);
                    if (password.length() < 5) {
                        TextWriter.printErr("Пароль слишком короткий, попробуйте еще раз.");
                        continue;
                    }
                    return new Request(login, password, RequestType.LOGIN);
                } catch (NullPointerException e) {
                    TextWriter.printErr("Ошибка при работе с консолью.");
                    System.exit(1);
                }
            }
        }
    }

    /**
     * Метод, возвращающий информацию о том, выполнена ли авторизация в приложении.
     * @return true, если авторизация была выполнена успешно, иначе false.
     */
    public boolean isAuthorizationDone() {
        return authorizationDone;
    }

    /**
     * Метод, устанавливающий флаг выполнения авторизации в приложении.
     * @param authorizationDone флаг выполнения авторизации.
     */
    public void setAuthorizationDone(boolean authorizationDone) {
        this.authorizationDone = authorizationDone;
    }

}
