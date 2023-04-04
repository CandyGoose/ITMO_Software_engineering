package Server.Commands;

import Common.util.Request;
import Common.util.Response;

public abstract class AbstractCommand {

    private final String name;
    private final String description;
    private final int amountOfArgs;

    public AbstractCommand(String name, String description, int amountOfArgs) {
        this.name = name;
        this.description = description;
        this.amountOfArgs = amountOfArgs;
    }

    public abstract Response execute(Request request);

    public String getName() {
        return name;
    }

    public int getAmountOfArgs() { return amountOfArgs; }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return name + ": " + description;
    }
}
