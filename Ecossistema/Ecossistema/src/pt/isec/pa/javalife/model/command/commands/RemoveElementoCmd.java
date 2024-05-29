package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.CommandAdapter;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.IElemento;

public class RemoveElementoCmd extends CommandAdapter {

    private final EcossistemaManager manager;
    private final IElemento elemento;

    public RemoveElementoCmd(EcossistemaManager manager, IElemento elemento) {
        this.manager = manager;
        this.elemento = elemento;
    }

    @Override
    public void execute() {
        manager.removerElemento(elemento.getId());
    }

    @Override
    public void undo() {
        manager.addElemento(elemento);
    }


}