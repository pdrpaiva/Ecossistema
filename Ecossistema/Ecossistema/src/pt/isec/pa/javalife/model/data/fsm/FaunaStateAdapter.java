package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;

public abstract class FaunaStateAdapter implements IFaunaState {
    protected FaunaContext context;
    protected Fauna data;

    protected FaunaStateAdapter(FaunaContext context, Fauna data) {
        this.context = context;
        this.data = data;
    }
}
