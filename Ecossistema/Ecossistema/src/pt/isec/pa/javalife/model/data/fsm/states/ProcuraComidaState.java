package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class ProcuraComidaState extends FaunaStateAdapter {

    public ProcuraComidaState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public boolean alimentar(){
        // Lógica para encontrar Flora e alimentar
        changeState(FaunaState.ALIMENTACAO);
        return true;
    }


    @Override
    public boolean atacar(){
        if (data.getForca() < 80) {
            // Lógica para encontrar Fauna mais fraca e atacar
            changeState(FaunaState.ATAQUE);
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
    public FaunaState getState() {
        return FaunaState.PROCURA_COMIDA;
    }
}
