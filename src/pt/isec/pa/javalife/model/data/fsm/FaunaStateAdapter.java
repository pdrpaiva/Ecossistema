package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;

import java.io.Serializable;

public abstract class FaunaStateAdapter implements IFaunaState, Serializable {
    protected FaunaContext context;
    protected Fauna data;

    protected FaunaStateAdapter(FaunaContext context, Fauna data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(FaunaState newState) {
        context.changeState(newState.getInstance(context, data));
    }

    @Override
    public boolean executar() {
        return false; // padrão
    }

    @Override
    public FaunaState getState() {
        return null; // padrão
    }
}