package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class ProcuraComidaState extends FaunaStateAdapter {

    public ProcuraComidaState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public boolean executar() {
        IElemento closestFlora = context.getEcossistema().encontrarElementoMaisProximo(data.getArea(), Elemento.FLORA);
        Fauna weakestFauna = null;

        if (closestFlora != null) {
            if (moveTo(closestFlora)) {
                changeState(FaunaState.ALIMENTACAO);
            } else {

            }
        } else {
            weakestFauna = context.getEcossistema().encontrarFaunaMaisFraca(data.getId());
            System.out.println("Fauna mais fraca encontrada: " + data.getId());

            if (weakestFauna != null) {
                if (moveToFauna(weakestFauna)) {
                    System.out.println("ATAQUE");
                    changeState(FaunaState.ATAQUE);
                } else {
                    changeState(FaunaState.MOVIMENTO);
                }
            } else {

                changeState(FaunaState.MOVIMENTO);
            }
        }
        return true;
    }
   private boolean moveTo(IElemento target) {
       data.moveParaAlvo(target);
       return isOverlapping(data.getArea(), target.getArea());
   }
    private boolean moveToFauna(IElemento target) {
        data.moveParaAlvo(target);
        return adjacente(data.getArea(),target.getArea());
    }

    private boolean isOverlapping(Area area1, Area area2) {
        return area1.intersecta(area2);
    }
    private boolean adjacente(Area area1, Area area2){
        return area1.isAdjacent(area2);
    }

    @Override
    public FaunaState getState() {
        return FaunaState.PROCURA_COMIDA;
    }
}
