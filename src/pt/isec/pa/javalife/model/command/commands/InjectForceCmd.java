package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.Fauna;

public class InjectForceCmd implements ICommand {
    private final EcossistemaManager manager;
    private final Fauna fauna;
    private final double originalForce;
    private final double additionalForce;

    public InjectForceCmd(EcossistemaManager manager, Fauna fauna, double additionalForce) {
        this.manager = manager;
        this.fauna = fauna;
        this.originalForce = fauna.getForca();
        this.additionalForce = additionalForce;
    }

    @Override
    public void execute() {
        fauna.setForca(originalForce + additionalForce);
    }

    @Override
    public void undo() {
        fauna.setForca(originalForce);
    }
}
