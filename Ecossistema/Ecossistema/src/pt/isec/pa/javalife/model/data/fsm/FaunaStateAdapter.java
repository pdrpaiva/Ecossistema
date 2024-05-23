package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;

public abstract class   FaunaStateAdapter implements IFaunaState {
    protected FaunaContext context;
    protected Fauna data;

    protected FaunaStateAdapter(FaunaContext context, Fauna data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(FaunaState newState){
        context.changeState(  //tem várias opções de uso
                //CofreStateFactory.getInstance(newState,context,data)
                //CofreState.getInstance(newState, context, data)
                newState.getInstance(context,data)
        );
    }

    @Override
    public boolean mover() {
        return false;
    }

    @Override
    public boolean procurarComida() {
        return false;
    }

    @Override
    public boolean alimentar() {
        return false;
    }

    @Override
    public boolean atacar() {
        return false;
    }

    @Override
    public boolean morrer() {
        return false;
    }

    @Override
    public FaunaState getState() {
        return null;
    }

}
