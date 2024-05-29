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
    public boolean executar() {
        if (data.getForca() <= 0) {
            changeState(FaunaState.MORTO);
            return true;
        } else {
            // Lógica de ataque
            Fauna weakerFauna = findWeakerFauna();
            if (weakerFauna != null) {
                data.setForca(data.getForca() + weakerFauna.getForca());
                context.removeElemento(weakerFauna.getId());
                return true;
            } else {
                changeState(FaunaState.MOVIMENTO);
                return true;
            }
        }
    }

    private Fauna findWeakerFauna() {
        // Implementação para encontrar a fauna mais fraca
        return null; // Placeholder
    }

    @Override
    public FaunaState getState() {
        return FaunaState.ATAQUE;
    }

}
