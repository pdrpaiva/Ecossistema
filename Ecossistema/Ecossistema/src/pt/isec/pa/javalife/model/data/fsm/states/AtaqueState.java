package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class AtaqueState extends FaunaStateAdapter {

    public AtaqueState(FaunaContext context, Fauna data) {
        super(context, data);
    }


    @Override
    public boolean mover() {
        if (data.getForca() > 0) {
            changeState(FaunaState.MOVIMENTO);
            return true;
        }
        return false;
    }

    @Override
    public boolean morrer() {
        if (data.getForca() <= 0) {
            changeState(FaunaState.MORTO);
            return true;
        }
        return false;
    }

    @Override
    public FaunaState getState() {
        return FaunaState.ATAQUE;
    }

}
