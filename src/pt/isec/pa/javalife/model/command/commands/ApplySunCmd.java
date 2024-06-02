package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;

public class ApplySunCmd implements ICommand {
    private final EcossistemaManager manager;

    public ApplySunCmd(EcossistemaManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.getEcossistema().applySun();
    }

    @Override
    public void undo() {
        manager.getEcossistema().removerSol();
    }
}
