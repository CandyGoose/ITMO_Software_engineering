import java.util.Scanner;

final class Main {
    private final static Scanner inp = new Scanner(System.in);

    public static void main(String[] args) {
        boolean messageCreated = false;
        Message msg = null;
        do {
            System.out.print("Введите сообщение: ");
            String msgStr = inp.next();
            try {
                msg = new Message(msgStr);
                messageCreated = true;
            } catch (Exception e) {
                System.out.println("Невозможно обработать сообщение");
            }
        } while (!messageCreated);
        printErrorAndFix(msg);
    }

    private static void printErrorAndFix(Message m) {
        String errorBit = m.getErrorBit();
        if (errorBit != null) {
            System.out.printf("Ошибка в бите: %s\n", errorBit);
            Message fixed;
            try {
                fixed = m.getFixedMessage();
            } catch (Exception e) {
                System.out.println("Возникла проблема при исправлении ошибки");
                return;
            }
            System.out.printf("Исправленное сообщение: %s\n", fixed.getCurrentMessageString());
            try {
                assert (fixed.getFixedMessage() == null);
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }
        else
            System.out.printf("Ошибок нет");
    }
}

class Message {
    final static int MAX_CHECKING_BITS = 20;
    protected String msg;
    protected String S;

    public Message(String message) throws Exception {
        checkMessage(message);
        this.msg = message;
        calculateS();
    }

    public String getErrorBit() {
        int bitIndex = binToDec(reverse(this.S));
        int log2BitIndex = flooredLog2(bitIndex);
        if (bitIndex == 0)  // Нет ошибок
            return null;
        else if ((1 << log2BitIndex) == bitIndex)  // Контрольные биты стоят в индексах, это степени 2
            return "r" + log2BitIndex;
        else  // В противном случае это информационный бит
            return "i" + (bitIndex - log2BitIndex - 1);
    }

    public Message getFixedMessage() throws Exception {
        int bitIndex = binToDec(reverse(this.S)) - 1;
        String newMsg = this.msg.substring(0, bitIndex) + (this.msg.charAt(bitIndex) == '1' ? '0' : '1') + this.msg.substring(bitIndex + 1);
        return new Message(newMsg);
    }

    public String getCurrentMessageString() {
        return this.msg;
    }

    private void checkMessage(String msg) throws Exception {
        if (countChars(msg, "1") + countChars(msg, "0") != msg.length())
            throw new Exception();
    }

    private void calculateS() {
        int n = msg.length();
        int r = getNumberOfCheckingBits(n);
        String S = "";
        for (int i = 1; i <= r; i++)
            S += calculateSyndrome(i);
        this.S = S;
    }

    private int calculateSyndrome(int idx) {
        int s = 0;
        for (int i = 1 << (idx - 1); i <= msg.length(); i += 2*idx) {
            for (int j = 0; j < 1 << (idx - 1); j++) {
                if (i + j - 1 >= msg.length())
                    break;
                s = (s + Character.getNumericValue(this.msg.charAt(i + j - 1))) % 2;
            }
        }
        return s;
    }

    private static int getNumberOfCheckingBits(int totalBits) {
        // 2^r >= r + i + 1, searching for the first r which fits this inequality
        int twoPowR = 1;
        for (int r = 1; r <= MAX_CHECKING_BITS; r++) {
            twoPowR *= 2;
            if (twoPowR >= totalBits + 1)
                return r;
        }
        throw new ArithmeticException();
    }

    private static int countChars(String s, String chars) {
        return s.length() - s.replace(chars, "").length();
    }

    private static int flooredLog2(int num) {
        int val = 1;
        for (int power = 0; val < Integer.MAX_VALUE; power++) {
            if (val == num || val * 2 > num)
                return power;
            val *= 2;
        }
        return -1;
    }

    private static int binToDec(String bin) {
        int dec = 0;
        for (int i = 0; i < bin.length(); i++)
            if (bin.charAt(i) == '1')
                dec += 1 << (bin.length() - 1 - i);
        return dec;
    }

    private static String reverse(String s) {
        String res = "";
        for (int i = s.length() - 1; i >= 0; i--)
            res += s.charAt(i);
        return res;
    }
}