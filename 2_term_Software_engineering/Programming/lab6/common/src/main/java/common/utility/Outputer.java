package common.utility;

public class Outputer {

    public static void print(Object toOut) {
        System.out.print(toOut);
    }


    public static void println() {
        System.out.println();
    }

    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }

    public static void printError(Object toOut) {
        System.out.println("error: " + toOut);
    }
}