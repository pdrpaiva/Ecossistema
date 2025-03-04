package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.IElemento;

public class AddElementoCmd implements ICommand {

    private final EcossistemaManager manager;
    private final IElemento elemento;

    public AddElementoCmd(EcossistemaManager manager, IElemento elemento) {
        this.manager = manager;
        this.elemento = elemento;
    }

    @Override
    public void execute() {
        manager.getEcossistema().adicionarElemento(elemento);
    }

    @Override
    public void undo() {
        manager.getEcossistema().removerElemento(elemento.getId());
    }
}
