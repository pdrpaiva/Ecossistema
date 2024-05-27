package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class ProcuraComidaState extends FaunaStateAdapter {

    public ProcuraComidaState(FaunaContext context, Fauna data) {
        super(context, data);
    }


    @Override
    public boolean executar() {
        Flora nearestFlora = findNearestFlora();
        if(nearestFlora != null){
            data.setForca(data.getForca() + nearestFlora.getForca());
            context.removeElemento(nearestFlora.getId());
            changeState(FaunaState.ALIMENTACAO);
            return true;
        }else if(data.getForca() >= 80){
            changeState(FaunaState.MOVIMENTO);
            return true;
        }


        return false;
    }

    private Flora findNearestFlora() {
        // Implementação para encontrar a flora mais próxima
        return null; // Placeholder
    }

    private Fauna findWeakerFauna() {
        // Implementação para encontrar a fauna mais fraca
        return null; // Placeholder
    }



    @Override
    public FaunaState getState() {
        return FaunaState.PROCURA_COMIDA;
    }
}
