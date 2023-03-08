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
 * Класс FileManager, который отвечает за запись коллекции в файл и чтение из файла.
 */
public class FileManager {

    /**
     * Имя файла
     */
    private String filename;

    /**
     * Объект XStream для преобразования коллекции в XML и обратно
     */
    private final XStream xstream;

    /**
     * Устанавливает имя файла.
     * @param filename имя файла
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Создает новый экземпляр класса FileManager с указанным именем файла и настройками XStream.
     * Задает псевдонимы для классов, устанавливает настройки без ссылок и безопасности и
     * разрешает использование типа List и String.
     * @param filename имя файла
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
     * Записывает коллекцию в файл в формате XML.
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
     * Метод для чтения коллекции организаций из файла.
     *
     * @return возвращает коллекцию организаций, прочитанную из файла.
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
     * Переменная, в которой хранится путь к папке с файлами.
     */
    public static String path;

    /**
     * Метод для получения имени папки из переменной окружения.
     * @return имя папки
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
     * Возвращает строку с описанием класса FileManager
     * @return описание класса
     */
    @Override
    public String toString() {
        return "FileManager (класс для работы с файлами)";
    }
}