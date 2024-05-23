package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.CommandAdapter;
import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.IElemento;

public class AddElementoCmd extends CommandAdapter {

    private final EcossistemaManager manager;
    private final IElemento elemento;


    public AddElementoCmd(EcossistemaManager manager, IElemento elemento) {
        this.manager = manager;
        this.elemento = elemento;
    }

    @Override
    public void execute() {
        manager.addElemento(elemento);
    }

    @Override
    public void undo() {
        manager.removeElemento(elemento.getId());
    }

}
