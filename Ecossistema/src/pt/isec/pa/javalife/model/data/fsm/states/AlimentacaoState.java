package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class AlimentacaoState extends FaunaStateAdapter {

    public AlimentacaoState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public boolean executar() {
        if (data.getForca() < 80) {
            IElemento flora = findNearestFlora();
            if (flora != null && flora instanceof Flora) {
                Flora floraElement = (Flora) flora;
                double forcaRecuperada = Math.min(80 - data.getForca(), floraElement.getForca());
                data.setForca(data.getForca() + forcaRecuperada);
                floraElement.setForca(floraElement.getForca() - forcaRecuperada);

                if (floraElement.getForca() <= 0) {
                    context.getEcossistema().removeElemento(floraElement.getId());
                }

                if (data.getForca() >= 80) {
                    changeState(FaunaState.MOVIMENTO);
                }

                return true;
            } else {
                IElemento weakerFauna = findWeakerFauna();
                if (weakerFauna != null && weakerFauna instanceof Fauna) {
                    Fauna faunaElement = (Fauna) weakerFauna;
                    data.setForca(data.getForca() - 10);
                    if (data.getForca() > 0) {
                        faunaElement.setForca(faunaElement.getForca() - data.getForca());
                        if (faunaElement.getForca() <= 0) {
                            context.getEcossistema().removeElemento(faunaElement.getId());
                            data.setForca(data.getForca() + faunaElement.getForca());
                        }
                    } else {
                        faunaElement.setForca(faunaElement.getForca() + data.getForca());
                        context.getEcossistema().removeElemento(data.getId());
                        changeState(FaunaState.MORTO);
                    }
                    return true;
                } else {
                    changeState(FaunaState.MOVIMENTO);
                    return true;
                }
            }
        } else {
            changeState(FaunaState.MOVIMENTO);
            return true;
        }
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
        // Implementação para calcular a distância entre duas áreas
        double x1 = (area1.direita() + area1.esquerda()) / 2;
        double y1 = (area1.cima() + area1.baixo()) / 2;
        double x2 = (area2.direita() + area2.esquerda()) / 2;
        double y2 = (area2.cima() + area2.baixo()) / 2;

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private IElemento findWeakerFauna() {
        IElemento weakerFauna = null;
        double lowestForca = Double.MAX_VALUE;

        for (IElemento elemento : context.getEcossistema().getElementos()) {
            if (elemento instanceof Fauna && elemento != data) {
                Fauna fauna = (Fauna) elemento;
                if (fauna.getForca() < lowestForca) {
                    lowestForca = fauna.getForca();
                    weakerFauna = elemento;
                }
            }
        }
        return weakerFauna;
    }

    @Override
    public FaunaState getState() {
        return FaunaState.ALIMENTACAO;
    }
}
