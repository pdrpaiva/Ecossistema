package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class AlimentacaoState extends FaunaStateAdapter {
    private Flora floraAtual;
    public AlimentacaoState(FaunaContext context, Fauna data) {
        super(context, data);
        this.floraAtual = null;
    }
//    @Override
//    public boolean executar() {
//        if (data.getForca() < 80) {
//            IElemento flora = findNearestFlora();
//            if (flora != null && flora instanceof Flora) {
//                Flora floraElement = (Flora) flora;
//                double forcaRecuperada = Math.min(80 - data.getForca(), floraElement.getForca());
//                data.setForca(data.getForca() + forcaRecuperada);
//                floraElement.setForca(floraElement.getForca() - forcaRecuperada);
//
//                if (floraElement.getForca() <= 0) {
//                    context.getEcossistema().removerElemento(floraElement.getId());
//                }
//
//                if (data.getForca() >= 80) {
//                    changeState(FaunaState.MOVIMENTO);
//                }
//
//                return true;
//            } else {
//                changeState(FaunaState.PROCURA_COMIDA);
//                return true;
//            }
//        } else {
//            changeState(FaunaState.MOVIMENTO);
//            return true;
//        }
//    }
@Override
public boolean executar() {
    if (data.getForca() < 80) {
        if (floraAtual == null) {
            IElemento flora = findNearestFlora();
            if (flora != null && flora instanceof Flora) {
                floraAtual = (Flora) flora;
            } else {
                changeState(FaunaState.PROCURA_COMIDA);
                return true;
            }
        }

        if (floraAtual != null) {
            if (data.getArea().intersecta(floraAtual.getArea())) {
                // Se intersecta, então continua alimentando.
                return true;
            } else {
                // Se não intersecta mais, procura outra flora
                floraAtual = null;
                changeState(FaunaState.PROCURA_COMIDA);
                return true;
            }
        }
    } else {
        changeState(FaunaState.MOVIMENTO);
    }
    return true;
}

    private IElemento findNearestFlora() {
        IElemento nearestFlora = null;
        double shortestDistance = Double.MAX_VALUE;

        for (IElemento elemento : context.getEcossistema().getElementos()) {
            if (elemento instanceof Flora) {
                double distance = calculateDistance(data.getArea(), elemento.getArea());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestFlora = elemento;
                }
            }
        }
        return nearestFlora;
    }

    private double calculateDistance(Area area1, Area area2) {
        double x1 = (area1.direita() + area1.esquerda()) / 2;
        double y1 = (area1.cima() + area1.baixo()) / 2;
        double x2 = (area2.direita() + area2.esquerda()) / 2;
        double y2 = (area2.cima() + area2.baixo()) / 2;

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public FaunaState getState() {
        return FaunaState.ALIMENTACAO;
    }
}
