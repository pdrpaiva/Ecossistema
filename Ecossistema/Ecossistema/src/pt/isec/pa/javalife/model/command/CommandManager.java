package pt.isec.pa.javalife.model.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<ICommand> commandHistory = new ArrayList<>();
    private int currentCommandIndex = -1;

    public void executeCommand(ICommand command) {
        if (currentCommandIndex != commandHistory.size() - 1) {
            commandHistory.subList(currentCommandIndex + 1, commandHistory.size()).clear();
        }
        command.execute();
        commandHistory.add(command);
        currentCommandIndex++;
    }


    public void undo() {
        if (currentCommandIndex >= 0) {
            ICommand command = commandHistory.get(currentCommandIndex);
            command.undo();
            currentCommandIndex--;
        }
    }

    public void redo() {
        if (currentCommandIndex < commandHistory.size() - 1) {
            ICommand command = commandHistory.get(currentCommandIndex + 1);
            command.execute();
            currentCommandIndex++;
        }
    }

}
