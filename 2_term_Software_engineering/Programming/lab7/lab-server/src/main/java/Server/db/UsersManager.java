package Server.db;

import Common.exception.DatabaseException;
import Common.util.Request;
import Common.util.Response;

import java.util.ArrayList;

public class UsersManager {
    private final DBManager dbManager;

    public UsersManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public Response registerUser(Request request) {
        try {
            if (!dbManager.checkUsersExistence(request.getLogin())) {
                dbManager.addUser(request.getLogin(), request.getPassword());
                ArrayList<String> userData = new ArrayList<>();
                userData.add(request.getLogin());
                userData.add(request.getPassword());
                return new Response("Регистрация успешно завершена.", userData);
            } else {
                ArrayList<String> userData = new ArrayList<>();
                userData.add(request.getLogin());
                return new Response("Такое имя пользователя уже существует.", userData);
            }

        } catch (DatabaseException e) {
            ArrayList<String> userData = new ArrayList<>();
            userData.add(request.getLogin());
            return new Response(e.getMessage(), userData);
        }
    }

    public Response logInUser(Request request) {
        try {
            if (dbManager.checkUsersExistence(request.getLogin())) {
                if (dbManager.validateUser(request.getLogin(), request.getPassword())) {
                    ArrayList<String> userData = new ArrayList<>();
                    userData.add(request.getLogin());
                    userData.add(request.getPassword());
                    return new Response("Вход успешно выполнен.", userData);

                } else {
                    ArrayList<String> userData = new ArrayList<>();
                    userData.add(request.getLogin());
                    return new Response("Неправильный пароль.", userData);
                }
            } else {
                ArrayList<String> userData = new ArrayList<>();
                userData.add(request.getLogin());
                return new Response("Этого имени пользователя не существует.", userData);
            }


        } catch (DatabaseException e) {
            ArrayList<String> userData = new ArrayList<>();
            userData.add(request.getLogin());
            return new Response(e.getMessage(), userData);
        }
    }
}
