package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;

public enum FaunaState {
    MOVIMENTO, PROCURA_COMIDA, ALIMENTACAO,ATAQUE,MORTO;

    public static IFaunaState getInstance(FaunaState type, FaunaContext context, Fauna data){
        return switch (type){
            case MOVIMENTO -> new MovimentoState(context,data);
            case PROCURA_COMIDA -> new ProcuraComidaState(context,data);
            case ALIMENTACAO -> new AlimentacaoState(context,data);
            case ATAQUE -> new AtaqueState(context,data);
            case MORTO -> new MortoState(context,data);
        };
    }

    public IFaunaState getInstance(FaunaContext context, Fauna data){
        return switch (this){
            case MOVIMENTO -> new MovimentoState(context,data);
            case PROCURA_COMIDA -> new ProcuraComidaState(context,data);
            case ALIMENTACAO -> new AlimentacaoState(context,data);
            case ATAQUE -> new AtaqueState(context,data);
            case MORTO -> new MortoState(context,data);
        };
    }
}
