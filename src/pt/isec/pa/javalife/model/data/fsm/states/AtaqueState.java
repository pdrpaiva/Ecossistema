package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Elemento;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class AtaqueState extends FaunaStateAdapter {

    public AtaqueState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public FaunaState getState() {
        return FaunaState.ATAQUE;
    }

    @Override
    public boolean executar() {
        Fauna faunaAlvo = encontrarFaunaMaisFraca(data.getId());
        if (faunaAlvo == null) {
            changeState(FaunaState.MOVIMENTO);
            return false;
        }

        data.setForca(data.getForca() - 10);

        FaunaState estadoAlvo = faunaAlvo.getFaunaContext().getCurrentState().getState();
        if (estadoAlvo == FaunaState.PROCURA_COMIDA || estadoAlvo == FaunaState.ALIMENTACAO || estadoAlvo == FaunaState.ATAQUE) {
            if (data.getForca() < faunaAlvo.getForca()) {
                faunaAlvo.setForca(faunaAlvo.getForca() + data.getForca());
                data.setForca(0);
            } else {
                data.setForca(data.getForca() + faunaAlvo.getForca());
                faunaAlvo.setForca(0);
            }
        }
        if (data.getForca() > 0) {
            data.setForca(data.getForca() + faunaAlvo.getForca());
            faunaAlvo.setForca(0);
        } else {
            faunaAlvo.setForca(faunaAlvo.getForca() + data.getForca() + 10);
            data.setForca(0);
            System.out.println("esfaleceu");
        }

        context.getEcossistema().removerElemento(faunaAlvo.getId());

        changeState(FaunaState.MOVIMENTO);
        return true;
    }

    private Fauna encontrarFaunaMaisFraca(int ignorarID) {
        double menorForca = Double.MAX_VALUE;
        Fauna maisFraca = null;
        for (IElemento elemento : context.getEcossistema().getElementos()) {
            if (elemento.getTipo() == Elemento.FAUNA && elemento.getId() != ignorarID) {
                Fauna fauna = (Fauna) elemento;
                if (fauna.getForca() < menorForca) {
                    menorForca = fauna.getForca();
                    maisFraca = fauna;
                }
            }
        }
        return maisFraca;
    }

    @Override
    public String toString() {
        return "Ataque";
    }
}
