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
        if (data.getForca() >= 80) {
            changeState(FaunaState.MOVIMENTO);
            return true;
        }

        IElemento closestFlora = context.getEcossistema().encontrarElementoMaisProximo(data.getArea(), Elemento.FLORA);
        if (closestFlora != null && moveTo(closestFlora)) {
            changeState(FaunaState.ALIMENTACAO);
            return true;
        }

        Fauna weakestFauna = context.getEcossistema().encontrarFaunaMaisFraca(data.getId());
        if (weakestFauna != null && moveTo(weakestFauna)) {
            changeState(FaunaState.ATAQUE);
            return true;
        }

        changeState(FaunaState.MOVIMENTO);
        return true;
    }

//    private boolean moveTo(IElemento target) {
//        data.moveParaAlvo(target);
//        return isOverlapping(data.getArea(), target.getArea());
//    }

    private boolean moveTo(IElemento target) {
        data.moveParaAlvo(target);
        if (isOverlapping(data.getArea(), target.getArea())) {
            return true;
        } else {
            // Se encontra um obstáculo no caminho, mudar para direção aleatória e tentar novamente.
            if (data.temObstaculoAFrente(context.getEcossistema().getElementos())) {
                data.mudarParaDirecaoAleatoria();
                data.mover();
            }
            return false;
        }
    }

    private boolean isOverlapping(Area area1, Area area2) {
        return area1.intersecta(area2);
    }

    @Override
    public FaunaState getState() {
        return FaunaState.PROCURA_COMIDA;
    }
}
