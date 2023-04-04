package Client.util;

import Common.exception.*;
import Common.data.*;
import Common.util.TextWriter;

import java.time.ZonedDateTime;
import java.util.*;


public class ScannerManager {

    public static final String INPUT_INFO = "> ";
    public static final String INPUT_COMMAND = "$ ";
    private Scanner userScanner;
    private boolean scriptMode;
    private String numberPattern = "-?\\d+(\\.\\d+)?";

    public ScannerManager(Scanner scanner) {
        this.userScanner = scanner;
        scriptMode = false;
    }
    public void setScriptScanner(Scanner scriptScanner) throws IncorrectInputInScriptException {
        this.userScanner = scriptScanner;
        scriptMode = true;
    }

    public Organization askOrganization() throws IncorrectInputInScriptException {
        return new Organization(
                null,
                askName(),
                askCoordinates(),
                ZonedDateTime.now(),
                askAnnualTurnover(),
                askFullName(),
                askEmployeesCount(),
                askType(),
                askAddress()
        );
    }

    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                TextWriter.printInfoMessage("Введите имя организации:");
                System.out.print(INPUT_INFO);
                name = userScanner.nextLine().trim();
                if (scriptMode) TextWriter.printInfoMessage(name);
                if (name.equals("")) throw new NotNullException();
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                System.exit(1);
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NotNullException exception) {
                TextWriter.printErr("Значение поля не может быть пустым.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    TextWriter.printErr("Работа программы прекращена.");
                    System.exit(1);
                }
            } catch (IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            }
        }
        return name;
    }

    public float askX() throws IncorrectInputInScriptException {
        String strX;
        float x;
        while (true) {
            try {
                TextWriter.printInfoMessage("Введите координату X:");
                System.out.print(INPUT_INFO);
                strX = userScanner.nextLine().trim();
                strX = strX.replace(",", ".");
                if (scriptMode) TextWriter.printInfoMessage(strX);
                if (!strX.matches(numberPattern)) throw new NumberFormatException();
                x = Float.parseFloat(strX);
                if (x > 741) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    TextWriter.printErr("Работа программы прекращена.");
                    System.exit(1);
                }
            } catch (NotInDeclaredLimitsException exception) {
                TextWriter.printErr("Значение поля должно быть меньше 741.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                    TextWriter.printErr("Значение поля должно быть float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            }
        }
        return x;
    }


    public Float askY() throws IncorrectInputInScriptException {
        String strY;
        Float y;
        while (true) {
            try {
                TextWriter.printInfoMessage("Введите координату Y:");
                System.out.print(INPUT_INFO);
                strY = userScanner.nextLine().trim();
                strY = strY.replace(",", ".");
                if (scriptMode) TextWriter.printInfoMessage(strY);
                if (!strY.matches(numberPattern)) throw new NumberFormatException();
                y = Float.parseFloat(strY);
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                System.exit(1);
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                    TextWriter.printErr("Значение поля должно быть Float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            }
        }
        return y;
    }


    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        float x = askX();
        Float y = askY();
        return new Coordinates(x, y);
    }

    public Float askAnnualTurnover() throws IncorrectInputInScriptException {
        String strTurn;
        Float annualTurnover;
        while (true) {
            try {
                TextWriter.printInfoMessage("Введите годовой оборот:");
                System.out.print(INPUT_INFO);
                strTurn = userScanner.nextLine().trim();
                strTurn = strTurn.replace(",", ".");
                if (scriptMode) TextWriter.printInfoMessage(strTurn);
                if (!strTurn.matches(numberPattern)) throw new NumberFormatException();
                annualTurnover = Float.parseFloat(strTurn);
                if (annualTurnover <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                System.exit(1);
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                    TextWriter.printErr("Значение поля должно быть Float.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            } catch (NotInDeclaredLimitsException e) {
                TextWriter.printErr("Значение поля должно быть больше 0.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return annualTurnover;
    }

    public String askFullName() throws IncorrectInputInScriptException {
        String fullName;
        while (true) {
            try {
                TextWriter.printInfoMessage("Введите полное имя организации: ");
                System.out.print(INPUT_INFO);
                fullName = userScanner.nextLine().trim();
                if (scriptMode) TextWriter.printInfoMessage(fullName);
                if (fullName.equals("")) throw new NotNullException();
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                System.exit(1);
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NotNullException exception) {
                TextWriter.printErr("Значение поля не может быть пустым.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            }
        }
        return fullName;
    }

    public long askEmployeesCount() throws IncorrectInputInScriptException {
        String strEmp;
        long employeesCount;
        while (true) {
            try {
                TextWriter.printInfoMessage("Введите количество сотрудников:");
                System.out.print(INPUT_INFO);
                strEmp = userScanner.nextLine().trim();
                strEmp = strEmp.replace(",", ".");
                if (scriptMode) TextWriter.printInfoMessage(strEmp);
                if (!strEmp.matches("-?\\d+$")) throw new NumberFormatException();
                employeesCount = Long.parseLong(strEmp);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                System.exit(1);
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                    TextWriter.printErr("Значение поля должно быть long.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            } catch (NotInDeclaredLimitsException e) {
                TextWriter.printErr("Значение поля должно быть больше 0.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return employeesCount;
    }

    public OrganizationType askType() throws IncorrectInputInScriptException {
        String strType;
        OrganizationType type;
        while (true) {
            try {
                TextWriter.printInfoMessage("Список типов: " + OrganizationType.nameList());
                TextWriter.printInfoMessage("Введите тип организации из списка:");
                System.out.print(INPUT_INFO);
                strType = userScanner.nextLine().trim();
                if (scriptMode) TextWriter.printInfoMessage(strType);
                if (strType.equals("")) throw new NotNullException();
                type = OrganizationType.valueOf(strType.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                System.exit(1);
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                TextWriter.printErr("Такого типа нет в списке.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            } catch (NotNullException exception) {
                TextWriter.printErr("Значение поля не может быть пустым.");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return type;
    }

    public String askStreet() throws IncorrectInputInScriptException {
        String street;
        while (true) {
            try {
                TextWriter.printInfoMessage("Введите улицу организации:");
                System.out.print(INPUT_INFO);
                street = userScanner.nextLine().trim();
                if (scriptMode) TextWriter.printInfoMessage(street);
                break;
            } catch (NoSuchElementException exception) {
                TextWriter.printErr("Значение поля не может быть использовано.");
                System.exit(1);
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                TextWriter.printErr("Непредвиденная ошибка.");
                System.exit(1);
            }
        }
        return street;
    }

    public Address askAddress() throws IncorrectInputInScriptException {
        String street = askStreet();
        if(street.equals("")) return null;
        return new Address(street);
    }
}


