package pt.isec.pa.javalife.model.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<ICommand> commandHistory;
    private int currentCommandIndex;


    public CommandManager(){
        commandHistory = new ArrayList<>();
        currentCommandIndex = -1;
    }

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
            commandHistory.get(currentCommandIndex--).undo();
        }
    }

    public void redo() {
        if (currentCommandIndex < commandHistory.size() - 1) {
            commandHistory.get(++currentCommandIndex).execute();
        }
    }

    public void clearHistory() {
        commandHistory.clear();
        currentCommandIndex = -1;
    }

}
