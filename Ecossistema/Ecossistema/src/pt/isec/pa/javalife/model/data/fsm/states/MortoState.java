package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class MortoState extends FaunaStateAdapter {

    public MortoState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public boolean executar() {
        data.setVivo(false);
        context.removeElemento(data.getId());
        return true;
    }

    @Override
    public FaunaState getState() {
        return FaunaState.MORTO;
    }
}
