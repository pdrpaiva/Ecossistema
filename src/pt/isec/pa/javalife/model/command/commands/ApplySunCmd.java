package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;

public class ApplySunCmd implements ICommand {
    private final EcossistemaManager manager;
    private final long duration;

    public ApplySunCmd(EcossistemaManager manager, long duration) {
        this.manager = manager;
        this.duration = duration;
    }

    @Override
    public void execute() {
        manager.getEcossistema().aplicarSol();
    }

    @Override
    public void undo() {
        manager.getEcossistema().removerSol();
    }
}
