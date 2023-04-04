package Client.CommandDispatcher;

import Client.util.ScannerManager;
import Common.util.TextWriter;

import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandListener {

    public static CommandToSend readCommand(Scanner sc, boolean scriptMode) {
        try {
            if (!scriptMode) System.out.print(ScannerManager.INPUT_COMMAND);
            String[] splitedInput = sc.nextLine().trim().split("\\s+");
            if (splitedInput.length == 0 || splitedInput[0].equals("")) {
                return readCommand(sc, scriptMode);
            }
            if (splitedInput[0].equals("EOF")) return null;
            String commandName = splitedInput[0].toLowerCase(Locale.ROOT);
            String[] commandsArgs = Arrays.copyOfRange(splitedInput, 1, splitedInput.length);
            if (scriptMode) {
                String arguments = String.join(" ", commandsArgs);
                TextWriter.printInfoMessage("$ " + commandName + " " + arguments);
            }
            return new CommandToSend(commandName, commandsArgs);

        } catch (NoSuchElementException e) {
            if (!scriptMode) {
                TextWriter.printErr("Принудительное завершение работы.");
                System.exit(1);
            }
            return null;
        }
    }
}
