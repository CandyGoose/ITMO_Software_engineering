package Server.util;

import Common.data.Address;
import Common.data.Coordinates;
import Common.data.Organization;
import Common.data.OrganizationType;
import Server.ServerApp;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.*;
import java.util.*;


public class FileManager {
    /**
     * Название файла
     */
    private final String fileName;

    /**
     * Объект XStream для преобразования коллекции в XML и обратно
     */
    private final XStream xstream;

    public FileManager(String fileName) {
        this.fileName = fileName;
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



    public void writeCollection(LinkedList<Organization> collection) {
        if (!fileName.equals("")) {
            try {
                FileOutputStream file = new FileOutputStream(FileManager.path);
                BufferedOutputStream collectionFileWriter = new BufferedOutputStream(file);
                String xml = xstream.toXML(new ArrayList<>(collection));
                byte[] buffer = xml.getBytes();
                collectionFileWriter.write(buffer, 0, buffer.length);
                collectionFileWriter.flush();
                ServerApp.logger.info("Коллекция сохранена.");
            } catch (IOException exception) {
                ServerApp.logger.warning("Файл не может быть открыт или является директорией.");
            }
        } else ServerApp.logger.warning("Файл поврежден или ошибка в названии.");
    }


    public void readCollection() {
        if (!fileName.equals("")) {
            try (Scanner collectionFileScanner = new Scanner(new File(path))) {

                xstream.setMode(XStream.NO_REFERENCES);
                xstream.addPermission(NoTypePermission.NONE);
                xstream.addPermission(NullPermission.NULL);
                xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
                xstream.allowTypeHierarchy(List.class);
                xstream.allowTypeHierarchy(String.class);
                xstream.ignoreUnknownElements();
                xstream.allowTypes(new Class[] {ArrayList.class, Organization.class});
                StringBuilder xml = new StringBuilder();
                while(collectionFileScanner.hasNext()){
                    xml.append(collectionFileScanner.nextLine());
                }
                List<Organization> list = (List<Organization>) xstream.fromXML(xml.toString());
                Validator validator = new Validator(list);
                LinkedList<Organization> organizationLinkedList = new LinkedList<>();
                organizationLinkedList.addAll(validator.validate());
                organizationLinkedList.sort(Comparator.comparing(Organization::getName, String.CASE_INSENSITIVE_ORDER));
                ServerApp.collectionManager.setOrganizationCollection(organizationLinkedList);
            } catch (StreamException exception){
                ServerApp.logger.warning("EOF error.\nФайл обработан.\n");
            }
            catch (FileNotFoundException exception) {
                ServerApp.logger.warning("Файл не найден или доступ запрещен.\nРабота сервера завершена.");
                System.exit(1);
            } catch (NoSuchElementException exception) {
                ServerApp.logger.warning("Файл пуст.\nРабота сервера завершена.");
                System.exit(1);
            } catch ( NullPointerException exception) {
                ServerApp.logger.warning("Неверный формат коллекции в файле.\nРабота сервера завершена.");
                System.exit(1);
            } catch (IllegalStateException exception) {
                ServerApp.logger.severe("Непредвиденная ошибка.\nРабота сервера завершена.");
                System.exit(1);
            }
        } else { ServerApp.logger.warning("Файл поврежден или ошибка в названии.\nРабота сервера завершена.");
            System.exit(1);}
    }

    public static String path;

    public static String getFileName(){
        try {
            path = System.getenv("lab5"); // lab5 - полный путь до файла, включая его название
            String[] checkPaths = path.split(";");
            if (checkPaths.length > 1) {
                ServerApp.logger.warning("В этой переменной содержится более одного пути к файлам.\nРабота сервера завершена.");
                System.exit(1);
            }
        } catch (NullPointerException e) {
            ServerApp.logger.warning("Некорректная переменная окружения.\nРабота сервера завершена.");
            System.exit(1);
        }
        File name = new File(path);
        return name.getName();
    }

}
