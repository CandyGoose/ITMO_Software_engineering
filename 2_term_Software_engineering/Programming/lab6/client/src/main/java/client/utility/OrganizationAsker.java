package client.utility;

import client.App;
import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.utility.Outputer;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс, запрашивающий у пользователя ввод
 */
public class OrganizationAsker {
    /**
     Сканер для чтения пользовательского ввода.
     */
    Scanner userScanner;
    /**
     * Это приватное логическое поле scriptMode, которое указывает, работает ли приложение в режиме скрипта или нет.
     * Если значение этого поля установлено в true, то это означает, что приложение выполняет команды, содержащиеся в
     * скрипте, иначе оно ожидает пользовательского ввода в консоли.
     */
    private boolean scriptMode;

    /**
     * Функция, возвращающая объект сканера, использующийся для считывания пользовательского ввода
     * @return объект сканера, созданный в этом методе
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Функция, задающая объект сканера, использующийся для считывания пользовательского ввода
     * @param userScanner объект сканера, использующийся для считывания пользовательского ввода
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public OrganizationAsker(Scanner userScanner) {
        this.userScanner = userScanner;
        scriptMode = false;
    }

    /**
     * Функция, задающая режим чтения со скрипта
     */
    public void setScriptMode(){
        scriptMode = true;
    }
    /**
     * Функция, задающая режим чтения с командной строки
     */
    public void setUserMode(){
        scriptMode = false;
    }

    /**
     * Функция, спрашивающая у пользователя имя организации
     * @return name - имя организации, введенное пользователем
     * @throws IncorrectInputInScriptException если при чтении скрипта возникла ошибка
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            Outputer.print("Введите имя организации:");
            Outputer.print(App.PS2);
            try {
                name = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (MustBeNotEmptyException e) {
                Outputer.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NoSuchElementException e) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (IllegalStateException e) {
                Outputer.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return name;
    }


    /**
     * Метод для ввода значения координаты X
     * @return значение координаты X
     * @throws IncorrectInputInScriptException если была ошибка ввода значения в скрипте
     */
    private float askX() throws IncorrectInputInScriptException {
        float x;
        while (true) {
            try {
                Outputer.print("Введите координату X:");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                x = Float.parseFloat(s);
                if(x > 741) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Outputer.printError("Значение поля должно быть float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Непредвиденная ошибка.");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Outputer.printError("Значение поля должно быть меньше 741.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return x;
    }


    /**
     * Метод для ввода значения координаты Y
     * @return значение координаты Y
     * @throws IncorrectInputInScriptException если была ошибка ввода значения в скрипте
     */
    private Float askY() throws IncorrectInputInScriptException {
        float y;
        while (true) {
            try {
                Outputer.print("Введите координату Y:");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                if (s.equals("")) throw new MustBeNotEmptyException();
                y = Float.parseFloat(s);
                break;
            } catch (NoSuchElementException e) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Outputer.printError("Значение поля должно быть Float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Непредвиденная ошибка.");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Outputer.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return y;
    }


    /**
     * Метод для ввода координат
     * @return объект класса Coordinates с заданными координатами
     * @throws IncorrectInputInScriptException если была ошибка ввода значения в скрипте
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        float x;
        Float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }


    /**
     * Запрашивает годовой оборот организации.
     * @return годовой оборот в виде Float
     * @throws IncorrectInputInScriptException если скрипт содержит неверные данные ввода
     */
    public Float askAnnualTurnover() throws IncorrectInputInScriptException {
        Float turnOver;
        while (true) {
            try {
                Outputer.print("Введите годовой оборот:");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                turnOver = Float.parseFloat(s);
                if(s.equals("")) throw new MustBeNotEmptyException();
                if(turnOver <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Outputer.printError("Значение поля должно быть Float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Непредвиденная ошибка");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Outputer.printError("Значение поля должно быть больше 0.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException e) {
                Outputer.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return turnOver;
    }

    /**
     * Запрашивает полное название организации.
     * @return полное название организации в виде строки
     * @throws IncorrectInputInScriptException если скрипт содержит неверные данные ввода
     */
    public String askFullName() throws IncorrectInputInScriptException {
        String fullName;
        while (true) {
            Outputer.print("Введите полное название организации: ");
            try {
                fullName = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(fullName);
                if (fullName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (MustBeNotEmptyException e) {
                Outputer.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NoSuchElementException e) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (IllegalStateException e) {
                Outputer.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return fullName;
    }


    /**
     * Метод запрашивает у пользователя количество сотрудников организации.
     * @return количество сотрудников.
     * @throws IncorrectInputInScriptException если введенные данные некорректны при работе скрипта.
     */
    public long askEmployeesCount() throws IncorrectInputInScriptException {
        long employeesCount;
        while (true) {
            try {
                Outputer.print("Введите количество сотрудников: ");
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                employeesCount = Long.parseLong(s);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printError("Значение поля должно быть больше 0.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printError("Значение поля должно быть long.");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return employeesCount;
    }


    /**
     * Метод запрашивает у пользователя тип организации.
     * @return тип организации.
     * @throws IncorrectInputInScriptException если введенные данные некорректны при работе скрипта.
     */
    public OrganizationType askOrganizationType() throws IncorrectInputInScriptException {
        OrganizationType organizationType;
        while (true) {
            try {
                Outputer.printLn("Категории: " + OrganizationType.nameList());
                Outputer.print("Введите тип организации: ");
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                if(s.equals("")) throw new MustBeNotEmptyException();
                organizationType = OrganizationType.valueOf(s.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (IllegalArgumentException exception) {
                Outputer.printError("Такой категории не существует.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printError("Непредвиденная ошибка.");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Outputer.printError("Поле не может быть null.");
            }
        }
        return organizationType;
    }


    /**
     * Запрашивает у пользователя название улицы и возвращает его в виде строки.
     * @return Название улицы, введенное пользователем
     * @throws IncorrectInputInScriptException если введенные данные некорректны при работе скрипта.
     */
    private String askStreet() throws IncorrectInputInScriptException {
        String street;
        while (true) {
            try {
                Outputer.print("Введите улицу: ");
                street = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(street);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Работа программы прекращена.");
                    System.exit(0);
                }
            } catch (IllegalStateException exception) {
                Outputer.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return street;
    }



    /**
     * Запрашивает у пользователя название улицы и создает новый объект Address с этим значением.
     * @return Новый объект Address с названием улицы, введенным пользователем, или null, если название пусто
     * @throws IncorrectInputInScriptException если введенные данные некорректны при работе скрипта.
     */
    public Address askAddress() throws IncorrectInputInScriptException {
        String street = askStreet();
        if(street.equals("")) return null;
        return new Address(street);
    }


    /**
     * Задает вопросы
     *
     * @param question вопрос
     * @return ответ (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException{
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try{
                Outputer.printLn(finalQuestion);
                Outputer.print(App.PS2);
                answer = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("The response was not recognized!");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printError("The answer must be represented by the signs '+' or '-'!");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }

}