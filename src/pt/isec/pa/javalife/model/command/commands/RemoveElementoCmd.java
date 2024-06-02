package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.IElemento;

public class RemoveElementoCmd implements ICommand {

    private final EcossistemaManager manager;
    private final IElemento elemento;

    public RemoveElementoCmd(EcossistemaManager manager, IElemento elemento) {
        this.manager = manager;
        this.elemento = elemento;
    }

    @Override
    public void execute() {
        manager.getEcossistema().removerElemento(elemento.getId());
    }

    @Override
    public void undo() {
        manager.getEcossistema().adicionarElemento(elemento);
    }
}
