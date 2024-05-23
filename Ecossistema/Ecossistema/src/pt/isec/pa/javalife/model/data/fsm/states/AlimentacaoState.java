package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class AlimentacaoState extends FaunaStateAdapter {

    public AlimentacaoState(FaunaContext context, Fauna data) {
        super(context, data);
    }


    @Override
    public boolean procurarComida(){
        if(data.getForca() < 80){
            changeState(FaunaState.PROCURA_COMIDA);
            return true;
        }
        return false;
    }


    @Override
    public boolean mover() {
        if (data.getForca() >= 80) {
            changeState(FaunaState.MOVIMENTO);
            return true;
        }
        return false;
    }

    @Override
    public FaunaState getState(){
        return FaunaState.ALIMENTACAO;
    }

}
