package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElementoComForca;
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

        Flora nearestFlora = findNearestFlora();
        if (nearestFlora != null) {
            moveTowards(nearestFlora.getArea());

            if (isOverlapping(data, nearestFlora)) {
                double forcaRecuperada = Math.min(80 - data.getForca(), nearestFlora.getForca());
                data.setForca(data.getForca() + forcaRecuperada);
                nearestFlora.setForca(nearestFlora.getForca() - forcaRecuperada);

                if (nearestFlora.getForca() <= 0) {
                    context.getEcossistema().removerElemento(nearestFlora.getId());
                }

                if (data.getForca() >= 80) {
                    changeState(FaunaState.MOVIMENTO);
                } else {
                    changeState(FaunaState.ALIMENTACAO);
                }
                return true;
            }
        }

        Fauna weakerFauna = findWeakerFauna();
        if (weakerFauna != null) {
            moveTowards(weakerFauna.getArea());

            if (isAdjacent(data, weakerFauna)) {
                data.setForca(data.getForca() - 10);

                if (data.getForca() > 0) {
                    weakerFauna.setForca(weakerFauna.getForca() - data.getForca());
                    if (weakerFauna.getForca() <= 0) {
                        context.getEcossistema().removerElemento(weakerFauna.getId());
                        data.setForca(data.getForca() + weakerFauna.getForca());
                    }
                } else {
                    weakerFauna.setForca(weakerFauna.getForca() + data.getForca());
                    context.getEcossistema().removerElemento(data.getId());
                    changeState(FaunaState.MORTO);
                }
                return true;
            }
        }

        return false;
    }

    private Flora findNearestFlora() {
        Flora nearestFlora = null;
        double shortestDistance = Double.MAX_VALUE;

        for (IElementoComForca elemento : context.getEcossistema().getElementos().stream()
                .filter(e -> e instanceof IElementoComForca)
                .map(e -> (IElementoComForca) e)
                .toList()) {
            if (elemento instanceof Flora) {
                Flora flora = (Flora) elemento;
                double distance = calculateDistance(data.getArea(), flora.getArea());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestFlora = flora;
                }
            }
        }
        return nearestFlora;
    }

    private Fauna findWeakerFauna() {
        Fauna weakerFauna = null;
        double lowestForca = Double.MAX_VALUE;

        for (IElementoComForca elemento : context.getEcossistema().getElementos().stream()
                .filter(e -> e instanceof IElementoComForca)
                .map(e -> (IElementoComForca) e)
                .toList()) {
            if (elemento instanceof Fauna && elemento != data) {
                Fauna fauna = (Fauna) elemento;
                if (fauna.getForca() < lowestForca) {
                    lowestForca = fauna.getForca();
                    weakerFauna = fauna;
                }
            }
        }
        return weakerFauna;
    }

    private void moveTowards(Area targetArea) {
        // Lógica para mover a fauna em direção a uma área específica
        // Placeholder para a lógica de movimento real
    }

    private boolean isOverlapping(Fauna fauna, Flora flora) {
        // Implementação para verificar se a fauna está sobreposta à flora
        // Placeholder para a lógica real
        return false;
    }

    private boolean isAdjacent(Fauna f1, Fauna f2) {
        double distance = calculateDistance(f1.getArea(), f2.getArea());
        return distance <= 1.0;
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
        return FaunaState.PROCURA_COMIDA;
    }
}
