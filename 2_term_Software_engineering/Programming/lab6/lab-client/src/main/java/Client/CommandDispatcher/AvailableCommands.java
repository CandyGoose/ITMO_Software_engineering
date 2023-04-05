package Client.CommandDispatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

/**
 * Класс, хранящий доступные команды для диспетчера команд.
 */
public class AvailableCommands {
    /**
     * Множество команд без аргументов.
     */
    public static final Set<String> COMMANDS_WITHOUT_ARGS = new HashSet<>();
    /**
     * Множество команд с аргументом ID.
     */
    public static final Set<String> COMMANDS_WITH_ID_ARG = new HashSet<>();
    /**
     * Множество команд с аргументом организации.
     */
    public static final Set<String> COMMANDS_WITH_ORGANIZATION_ARG = new HashSet<>();
    /**
     * Множество команд с аргументами ID и организации.
     */
    public static final Set<String> COMMANDS_WITH_ORGANIZATION_ID_ARGS = new HashSet<>();
    /**
     * Множество команд, принимающих аргументом скрипт.
     */
    public static final Set<String> SCRIPT_ARGUMENT_COMMAND = new HashSet<>();

    static {
        Collections.addAll(COMMANDS_WITHOUT_ARGS,
                "help",
                "show",
                "info",
                "exit",
                "remove_first",
                "clear",
                "print_descending",
                "print_unique_employees_count",
                "print_field_descending_annual_turnover",
                "sort",
                "shuffle"
        );
        Collections.addAll(COMMANDS_WITH_ID_ARG,
                "remove_by_id"
        );
        Collections.addAll(COMMANDS_WITH_ORGANIZATION_ARG,
                "add"
        );
        Collections.addAll(COMMANDS_WITH_ORGANIZATION_ID_ARGS,
                "update");
        SCRIPT_ARGUMENT_COMMAND.add("execute_script");
    }
}
