package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.IElementoComForca;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class MortoState extends FaunaStateAdapter {

    public MortoState(FaunaContext context, Fauna data) {
        super(context, data);
    }

  /*  @Override
    public boolean executar() {
        data.setVivo(false);
        context.getEcossistema().removerElemento(data.getId());
        IElementoComForca adjacenteFauna = findAdjacentFauna();
        if(adjacenteFauna != null){
            adjacenteFauna.setForca(adjacenteFauna.getForca() + data.getForca());
        }
        return true;
    } */


    /*private IElementoComForca findAdjacentFauna() {
        // Implementação para encontrar uma fauna adjacente (ou próxima) a esta fauna
        for (IElementoComForca elemento : context.getEcossistema().getElementos().stream()
                .filter(e -> e instanceof IElementoComForca)
                .map(e -> (IElementoComForca) e)
                .toList()) {
            if (elemento instanceof Fauna && elemento != data && isAdjacent(data, (Fauna) elemento)) {
                return (IElementoComForca) elemento;
            }
        }
        return null;
    } */

    private boolean isAdjacent(Fauna f1, Fauna f2) {
        // Implementação para verificar se dois elementos estão adjacentes
        double distance = calculateDistance(f1.getArea(), f2.getArea());
        return distance <= 1.0; // Considera adjacente se a distância for 1 ou menos (ajuste conforme necessário)
    }

    private double calculateDistance(Area area1, Area area2) {
        // Implementação para calcular a distância entre duas áreas
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
}
