package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.Flora;

public class ApplyHerbicideCmd implements ICommand {
    private final EcossistemaManager manager;
    private final Flora flora;

    public ApplyHerbicideCmd(EcossistemaManager manager, Flora flora) {
        this.manager = manager;
        this.flora = flora;
    }

    @Override
    public void execute() {
        manager.getEcossistema().removerElemento(flora.getId());
    }

    @Override
    public void undo() {
        manager.getEcossistema().adicionarElemento(flora);
    }
}
