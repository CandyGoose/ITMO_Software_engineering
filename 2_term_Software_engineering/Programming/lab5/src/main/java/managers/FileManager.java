package managers;

import data.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.*;
import java.util.*;


/**
 * Класс FileManager для управления файлами
 */
public class FileManager {
    private String filename;
    private final XStream xstream;

    /**
     * Эта функция задает объект с именем файла
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Задает псевдонимы для классов
     */
    public FileManager(String filename) {
        this.filename = filename;
        xstream = new XStream();

        xstream.alias("address", Address.class);
        xstream.alias("coordinates", Coordinates.class);
        xstream.alias("organization", Organization.class);
        xstream.alias("organizationType", OrganizationType.class);
        xstream.alias("organizations", CollectionManager.class);
        xstream.addImplicitCollection(CollectionManager.class, "organizationCollection");

        xstream.setMode(XStream.NO_REFERENCES);
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypeHierarchy(List.class);
        xstream.allowTypeHierarchy(String.class);
        xstream.ignoreUnknownElements();
    }


    /**
     * Функция, записывающая коллекцию в файл
     *
     * @param collection коллекция для записи в файл
     */
    public void writeCollection(LinkedList<Organization> collection) {
        if (!filename.equals("")) {
            try {
                FileOutputStream file = new FileOutputStream(FileManager.path);
                BufferedOutputStream collectionFileWriter = new BufferedOutputStream(file);

                String xml = xstream.toXML(new ArrayList<>(collection));
                byte[] buffer = xml.getBytes();
                collectionFileWriter.write(buffer, 0, buffer.length);
                collectionFileWriter.flush();

                Console.printLn("Файл сохранен.");
            } catch (IOException exception) {
                Console.printError("Файл не может быть открыт или является директорией.");
            }
        } else Console.printError("Файл поврежден или ошибка в названии.");
    }


    /**
     * Считывает коллекцию из файла и возвращает ее в виде LinkedList
     *
     * @return LinkedList<>()
     */
    public LinkedList<Organization> readCollection() {
        if (!filename.equals("")) {
            try (Scanner collectionFileScanner = new Scanner(new File(filename))) {

                xstream.setMode(XStream.NO_REFERENCES);
                xstream.addPermission(NoTypePermission.NONE);
                xstream.addPermission(NullPermission.NULL);
                xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
                xstream.allowTypeHierarchy(List.class);
                xstream.allowTypeHierarchy(String.class);
                xstream.ignoreUnknownElements();
                xstream.allowTypes(new Class[] {java.util.ArrayList.class, Organization.class});
                StringBuilder xml = new StringBuilder();
                while(collectionFileScanner.hasNext()){
                    xml.append(collectionFileScanner.nextLine());
                }
                List<Organization> list = (List<Organization>) xstream.fromXML(xml.toString());
                Validator validator = new Validator(list);
                LinkedList<Organization> organizationLinkedList = new LinkedList<>();
                organizationLinkedList.addAll(validator.validate());
                Collections.sort(organizationLinkedList);
                return organizationLinkedList;
            } catch (StreamException exception){
                Console.printError("EOF error.\nФайл обработан.\n");
            } catch (FileNotFoundException exception) {
                Console.printError("Файл не найден или доступ запрещен.\nПрограмма остановлена.");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                Console.printError("Файл пуст.\nПрограмма остановлена.");
                System.exit(0);
            } catch (NullPointerException exception) {
                Console.printError("Неверный формат коллекции в файле.\nПрограмма остановлена.");
            } catch (IllegalStateException exception) {
                Console.printError("Непредвиденная ошибка.\nПрограмма остановлена.");
                System.exit(0);
            } catch (Exception e) {
                Console.printError("Неверный формат данных в файле.\nФайл очищен.\n");
            }
        } else { Console.printError("Файл поврежден или ошибка в названии.\nПрограмма остановлена.");
        System.exit(0);}
        return new LinkedList<>();
    }

    /**
     * Задает объект с переменной окружения
     */
    public static String path;

    /**
     * Эта функция обрабатывает переменную окружения
     *
     * @return имя файла
     */

    public static String getName(){
        try {
            path = System.getenv("lab5");
            String[] checkPaths = path.split(";");
            if (checkPaths.length > 1) {
                System.out.print("В этой переменной содержится более одного пути к файлам.\nПрограмма остановлена.");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            Console.printError("Некорректная переменная окружения.\nПрограмма остановлена.");
            System.exit(0);
        }
        File name = new File(path);
        return name.getName();
    }

    /**
     * Эта функция возвращает строку, описывающую класс
     *
     * @return "FileManager (класс для работы с файлами)"
     */
    @Override
    public String toString() {
        return "FileManager (класс для работы с файлами)";
    }
}