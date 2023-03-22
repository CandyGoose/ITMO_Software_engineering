package server.utility;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import common.data.*;
import common.utility.Outputer;

import java.io.*;
import java.util.*;

/**
 * Класс FileManager, который отвечает за запись коллекции в файл и чтение из файла.
 */
public class CollectionFileManager {
    /**
     * Название файла
     */
    private String filename;

    /**
     * Объект XStream для преобразования коллекции в XML и обратно
     */
    private final XStream xstream;

    public CollectionFileManager(String filename) {
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
                FileOutputStream file = new FileOutputStream(CollectionFileManager.path);
                BufferedOutputStream collectionFileWriter = new BufferedOutputStream(file);
                String xml = xstream.toXML(new ArrayList<>(collection));
                byte[] buffer = xml.getBytes();
                collectionFileWriter.write(buffer, 0, buffer.length);
                collectionFileWriter.flush();
                Outputer.printLn("Файл сохранен.");
            } catch (IOException exception) {
                Outputer.printError("Файл не может быть открыт или является директорией.");
            }
        } else Outputer.printError("Файл поврежден или ошибка в названии.");
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
                LinkedList<Organization> organizationLinkedList = new LinkedList<>();
                organizationLinkedList.addAll(list);
                organizationLinkedList.sort(Comparator.comparing(Organization::getName, String.CASE_INSENSITIVE_ORDER));
            return organizationLinkedList;
            } catch (StreamException exception){
                Outputer.printError("EOF error.\nФайл обработан.\n");
            }
            catch (FileNotFoundException exception) {
                Outputer.printError("Файл не найден или доступ запрещен.\nПрограмма остановлена.");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                Outputer.printError("Файл пуст.\nПрограмма остановлена.");
                System.exit(0);
            } catch ( NullPointerException exception) {
                Outputer.printError("Неверный формат коллекции в файле.\nПрограмма остановлена.");
                System.exit(0); // ?
            } catch (IllegalStateException exception) {
                Outputer.printError("Непредвиденная ошибка.\nПрограмма остановлена.");
                System.exit(0);
            }
        } else { Outputer.printError("Файл поврежден или ошибка в названии.\nПрограмма остановлена.");
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
            path = System.getenv("lab5"); // lab5 - полный путь до файла, включая его название
            String[] checkPaths = path.split(";");
            if (checkPaths.length > 1) {
                Outputer.printError("В этой переменной содержится более одного пути к файлам.\nПрограмма остановлена.");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            Outputer.printError("Некорректная переменная окружения.\nПрограмма остановлена.");
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
