package Client.CommandDispatcher;

public class CommandToSend {

    private final String commandName;
    private final String[] commandArgs;

    public CommandToSend(String commandName, String[] commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getCommandArgs() {
        return commandArgs;
    }


}
