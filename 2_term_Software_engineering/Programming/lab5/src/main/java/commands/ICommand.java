package commands;

/**
 * Интерфейс для всех команд
 */
public interface ICommand {
    String getDescription();
    String getName();
    boolean execute(String argument);
}
