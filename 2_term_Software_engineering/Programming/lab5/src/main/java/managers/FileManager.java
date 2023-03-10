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

        xstream.alias("address", Address.class); // используется для установки псевдонимов для классов, которые будут использоваться при преобразовании объекта в поток байтов для сохранения в файл XML
        xstream.alias("coordinates", Coordinates.class);
        xstream.alias("organization", Organization.class);
        xstream.alias("organizationType", OrganizationType.class);
        xstream.alias("organizations", CollectionManager.class);
        xstream.addImplicitCollection(CollectionManager.class, "organizationCollection"); // используется для определения, какая коллекция будет использоваться для хранения списка организаций в классе CollectionManager

        xstream.setMode(XStream.NO_REFERENCES); // устанавливает режим NO_REFERENCES, который гарантирует, что при сериализации не будут использоваться ссылки на другие объекты, т.е. каждый объект будет сериализован в своем собственном контексте
        xstream.addPermission(NoTypePermission.NONE); // запрещает сериализацию или десериализацию объектов, не имеющих типа
        xstream.addPermission(NullPermission.NULL); // позволяет сериализовать и десериализовать null значения
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES); // разрешены только примитивные типы данных и списки
        xstream.allowTypeHierarchy(List.class); // разрешает использование иерархии типов для объектов, которые реализуют интерфейс List
        xstream.allowTypeHierarchy(String.class); // разрешает использование иерархии типов для объектов типа String
        xstream.ignoreUnknownElements(); // используется для игнорирования неизвестных элементов при десериализации
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

                String xml = xstream.toXML(new ArrayList<>(collection)); // сериализуется список элементов коллекции в формат XML и сохраняется в строковую переменную xml
                byte[] buffer = xml.getBytes(); // создается массив байтов buffer из строки xml
                collectionFileWriter.write(buffer, 0, buffer.length);
                collectionFileWriter.flush(); // все накопленные данные записываются в файл

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

                xstream.setMode(XStream.NO_REFERENCES); // не будут использоваться ссылки на другие объекты при сериализации
                xstream.addPermission(NoTypePermission.NONE); // запрещает сериализацию объектов, не имеющих указанных типов
                xstream.addPermission(NullPermission.NULL); // разрешает сериализацию null-значений
                xstream.addPermission(PrimitiveTypePermission.PRIMITIVES); // разрешает сериализацию примитивных типов данных и строк
                xstream.allowTypeHierarchy(List.class); // разрешает сериализацию всех типов, производных от List
                xstream.allowTypeHierarchy(String.class); // разрешает сериализацию всех типов, производных от String
                xstream.ignoreUnknownElements(); // игнорирует элементы, которые не удалось десериализовать
                xstream.allowTypes(new Class[] {java.util.ArrayList.class, Organization.class}); // разрешает сериализацию объектов типа ArrayList и Organization
                StringBuilder xml = new StringBuilder();
                while(collectionFileScanner.hasNext()){
                    xml.append(collectionFileScanner.nextLine());
                }
                List<Organization> list = (List<Organization>) xstream.fromXML(xml.toString()); // десериализирует объекты Organization из строки XML, созданной из содержимого файла.
                Validator validator = new Validator(list);
                LinkedList<Organization> organizationLinkedList = new LinkedList<>();
                organizationLinkedList.addAll(validator.validate()); // добавляет в список только те объекты Organization, которые прошли проверку валидатором
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