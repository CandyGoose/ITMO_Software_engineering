package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Server.db.DBManager;
import Server.util.CollectionManager;

public abstract class AbstractCommand {

    private final String name;
    private final String description;
    private final int amountOfArgs;
    protected CollectionManager collectionManager;
    protected DBManager dbManager;

    public AbstractCommand(String name, String description, int amountOfArgs) {
        this.name = name;
        this.description = description;
        this.amountOfArgs = amountOfArgs;
    }

    public AbstractCommand(String name, String description, int amountOfArgs, CollectionManager collectionManager, DBManager dbManager) {
        this.name = name;
        this.description = description;
        this.amountOfArgs = amountOfArgs;
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    public abstract Response execute(Request request);

    public String getName() {
        return name;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
