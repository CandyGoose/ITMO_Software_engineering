package server.commands;

/**
 * ICommand - интерфейс, который определяет методы, необходимые для реализации любой команды. Он содержит три метода:
 * getDescription, getName, getUsage и execute.
 */
public interface ICommand {
    String getDescription();
    String getName();
    String getUsage();
    boolean execute(String commandStringArgument, Object commandObjectArgument);
}
