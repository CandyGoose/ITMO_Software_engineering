package Client.NetworkManager;

import Client.App;
import Common.util.Request;
import Common.util.RequestType;
import Common.util.Response;
import Common.util.TextWriter;


import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AuthorizationModule {
    private final Scanner scanner;
    private boolean authorizationDone = false;

    public AuthorizationModule(Scanner scanner) {
        this.scanner = scanner;
    }

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
                password = scanner.nextLine().trim();
                if (password.length() < 5) {
                    TextWriter.printErr("Пароль слишком короткий, попробуйте еще раз.");
                    continue;
                }
                return new Request(login, password, RequestType.REGISTER);
            }
        }
    }

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
                password = scanner.nextLine().trim();
                if (password.length() < 5) {
                    TextWriter.printErr("Пароль слишком короткий, попробуйте еще раз.");
                    continue;
                }
                return new Request(login, password, RequestType.LOGIN);
            }

        }

    }
    public boolean isAuthorizationDone() {
        return authorizationDone;
    }

    public void setAuthorizationDone(boolean authorizationDone) {
        this.authorizationDone = authorizationDone;
    }

}
