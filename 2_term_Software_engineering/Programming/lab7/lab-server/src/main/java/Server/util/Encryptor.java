package Server.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Утилитный класс, предназначенный для шифрования строк методом SHA-1.
 */
public class Encryptor {
    /**
     * Метод, использующий алгоритм SHA-1 для шифрования переданной строки.
     * @param input строка, которую необходимо зашифровать.
     * @return зашифрованная строка в виде 32-символьной шестнадцатеричной строки.
     * @throws RuntimeException если не найден алгоритм SHA-1.
     */
    public static String encryptString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
