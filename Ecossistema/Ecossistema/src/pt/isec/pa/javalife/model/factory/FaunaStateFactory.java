package pt.isec.pa.javalife.model.factory;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.states.*;

public class FaunaStateFactory {
    private FaunaStateFactory(){}


    public static IFaunaState getInstance(FaunaState type, FaunaContext context, Fauna data) {
        return switch (type) {
            case MOVIMENTO -> new MovimentoState(context,data);
            case PROCURA_COMIDA -> new ProcuraComidaState(context,data);
            case ALIMENTACAO -> new AlimentacaoState(context,data);
            case ATAQUE -> new AtaqueState(context,data);
            case MORTO -> new MortoState(context,data);
        };
    }
}
