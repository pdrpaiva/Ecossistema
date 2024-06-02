package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.elements.IElementoComForca;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class MortoState extends FaunaStateAdapter {

    public MortoState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public boolean executar() {
        data.setVivo(false);
        context.getEcossistema().removerElemento(data.getId());

        IElementoComForca adjacenteFauna = findAdjacentFauna();
        if (adjacenteFauna != null) {
            adjacenteFauna.setForca(adjacenteFauna.getForca() + data.getForca());
        }

        return true;
    }

    private IElementoComForca findAdjacentFauna() {
        for (IElemento elemento : context.getEcossistema().getElementos()) {
            if (elemento instanceof Fauna && elemento != data && isAdjacent(data, (Fauna) elemento)) {
                return (IElementoComForca) elemento;
            }
        }
        return null;
    }

    private boolean isAdjacent(Fauna f1, Fauna f2) {
        double distance = calculateDistance(f1.getArea(), f2.getArea());
        double combinedHalfWidth = (f1.getArea().direita() - f1.getArea().esquerda()) / 2.0 + (f2.getArea().direita() - f2.getArea().esquerda()) / 2.0;
        double combinedHalfHeight = (f1.getArea().baixo() - f1.getArea().cima()) / 2.0 + (f2.getArea().baixo() - f2.getArea().cima()) / 2.0;
        return distance <= combinedHalfWidth + combinedHalfHeight; // Considera adjacente se a distância for menor ou igual à soma das metades das larguras e alturas
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
        return FaunaState.MORTO;
    }

    @Override
    public String toString() {
        return "Morto";
    }
}