package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class MovimentoState extends FaunaStateAdapter {

    public MovimentoState(FaunaContext context, Fauna data) {
        super(context, data);
    }



    @Override
    public boolean procurarComida() {
        if(data.getForca() < 35){
            changeState(FaunaState.PROCURA_COMIDA);
            return true;
        }
        return false;
    }

    @Override
    public FaunaState getState(){
        return FaunaState.MOVIMENTO;
    }
}
