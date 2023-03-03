package managers;

import data.Address;
import data.Coordinates;
import data.OrganizationType;
import exceptions.IncorrectInputInScriptException;
import exceptions.MustBeNotEmptyException;
import exceptions.NotInDeclaredLimitsException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Класс, запрашивающий у пользователя ввод
 */
public class OrganizationAsker {
    CollectionManager collectionManager;
    Scanner userScanner;
    private boolean scriptMode;

    /**
     * Эта функция возвращает объект сканера, использующийся для считывания пользовательского ввода
     *
     * @return объект сканера, созданный в этом методе
     */
    public Scanner getUserScanner() { return userScanner; }

    /**
     * Эта функция задает объект сканера, использующийся для считывания пользовательского ввода
     *
     * @param userScanner объект сканера, использующийся для считывания пользовательского ввода
     */
    public void setUserScanner(Scanner userScanner) { this.userScanner = userScanner; }

    /**
     * Эта функция задает считывание пользовательского ввода
     */
    public OrganizationAsker(CollectionManager collectionManager, Scanner userScanner) {
        this.userScanner = userScanner;
        this.collectionManager = collectionManager;
        scriptMode = false;
    }

    /**
     * Функция, отвечающая за переход в режим чтения со скрипта
     */
    public void setScriptMode(){
        scriptMode = true;
    }

    /**
     * Функция, отвечающая за переход в режим чтения с командной строки
     */
    public void setUserMode(){
        scriptMode = false;
    }



    /**
     * Функция, генерирующая новый id
     *
     * @return id
     */
    public Long setId() {
        return collectionManager.generateNewIdForCollection();
    }


    /**
     * Функция, спрашивающая у пользователя имя организации
     *
     * @return name
     * @throws IncorrectInputInScriptException
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            Console.print("Введите имя организации: ");
            try {
                name = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (MustBeNotEmptyException e) {
                Console.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NoSuchElementException e) {
                Console.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (IllegalStateException e) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return name;
    }


    /**
     * Функция, спрашивающая у пользователя координату X
     *
     * @return X
     * @throws IncorrectInputInScriptException
     */
    private float askX() throws IncorrectInputInScriptException {
        float x;
        while (true) {
            try {
                Console.print("Введите координату X: ");
                String s = userScanner.nextLine().trim();
                s = s.replace(",", ".");
                if(scriptMode) Console.printLn(s);
                x = Float.parseFloat(s);
                if(x > 741) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Console.printError("Значение поля должно быть float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Console.printError("Значение поля должно быть меньше 741.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return x;
    }

    /**
     * Функция, спрашивающая у пользователя координату Y
     *
     * @return Y
     * @throws IncorrectInputInScriptException
     */
    private Float askY() throws IncorrectInputInScriptException {
        float y;
        while (true) {
            try {
                Console.print("Введите координату Y: ");
                String s = userScanner.nextLine().trim();
                s = s.replace(",", ".");
                if(scriptMode) Console.printLn(s);
                if (s.equals("")) throw new MustBeNotEmptyException();
                y = Float.parseFloat(s);
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Console.printError("Значение поля должно быть Float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Console.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return y;
    }

    /**
     * AskCoordinates()
     *
     * @return Объект с координатами
     * @throws IncorrectInputInScriptException
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        float x;
        Float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }


    /**
     * Функция, устанавливающая дату и время
     *
     * @return ZonedDateTime
     */
    public ZonedDateTime askCreationDate() {
        while (true) {
            try {
                return ZonedDateTime.now();
            } catch (DateTimeException e) {
                Console.printError("Проблема с датой.");
            }
        }
    }


    /**
     * Функция, спрашивающая у пользователя годовой оборот
     *
     * @return turnOver
     * @throws IncorrectInputInScriptException
     */
    public Float askAnnualTurnover() throws IncorrectInputInScriptException {
        Float turnOver;
        while (true) {
            try {
                Console.print("Введите годовой оборот: ");
                String s = userScanner.nextLine().trim();
                s = s.replace(",", ".");
                if(scriptMode) Console.printLn(s);
                turnOver = Float.parseFloat(s);
                if(s.equals("")) throw new MustBeNotEmptyException();
                if(turnOver <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Значение поля не может быть использовано.");
                if(scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Console.printError("Значение поля должно быть Float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Console.printError("Значение поля должно быть больше 0.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException e) {
                Console.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }

        }
        return turnOver;
    }


    /**
     * Функция, спрашивающая у пользователя имя организации
     *
     * @return fullName
     * @throws IncorrectInputInScriptException
     */
    public String askFullName() throws IncorrectInputInScriptException {
        String fullName;
        while (true) {
            Console.print("Введите полное название организации: ");
            try {
                fullName = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(fullName);
                if (fullName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (MustBeNotEmptyException e) {
                Console.printError("Поле не может быть null.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NoSuchElementException e) {
                Console.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (IllegalStateException e) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return fullName;
    }

    /**
     * Функция, спрашивающая у пользователя количество сотрудников
     *
     * @return employeesCount
     * @throws IncorrectInputInScriptException
     */
    public long askEmployeesCount() throws IncorrectInputInScriptException {
        long employeesCount;
        while (true) {
            try {
                Console.print("Введите количество сотрудников: ");
                String s = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(s);
                employeesCount = Long.parseLong(s);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (NotInDeclaredLimitsException exception) {
                Console.printError("Значение поля должно быть больше 0.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printError("Значение поля должно быть long.");
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return employeesCount;
    }


    /**
     * Функция, спрашивающая у пользователя тип организации
     *
     * @return OrganizationType
     * @throws IncorrectInputInScriptException
     */
    public OrganizationType askOrganizationType() throws IncorrectInputInScriptException {
        OrganizationType organizationType;
        while (true) {
            try {
                Console.printLn("Категории: " + OrganizationType.nameList());
                Console.print("Введите тип организации: ");
                String s = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(s);
                if(s.equals("")) throw new MustBeNotEmptyException();
                organizationType = OrganizationType.valueOf(s.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (IllegalArgumentException exception) {
                Console.printError("Такой категории не существует.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Console.printError("Поле не может быть null.");
            }
        }
        return organizationType;
    }


    /**
     * Функция, спрашивающая у пользователя улицу
     *
     * @return street
     * @throws IncorrectInputInScriptException
     */
    private String askStreet() throws IncorrectInputInScriptException {
        String street;
        while (true) {
            try {
                Console.print("Введите улицу: ");
                street = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(street);
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Console.printError("Входной поток закрыт, остановка приложения.");
                    System.exit(0);
                }
            } catch (IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка.");
                System.exit(0);
            }
        }
        return street;
    }


    /**
     * Объект с адресом
     *
     * @return Address
     * @throws IncorrectInputInScriptException
     */
    public Address askAddress() throws IncorrectInputInScriptException {
        String street = askStreet();
        if(street.equals("")) return null;
        return new Address(street);
    }
}