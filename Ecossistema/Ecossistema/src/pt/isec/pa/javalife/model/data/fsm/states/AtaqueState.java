package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.elements.IElementoComForca;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

public class AtaqueState extends FaunaStateAdapter {

    public AtaqueState(FaunaContext context, Fauna data) {
        super(context, data);
    }


    @Override
    public boolean executar() {
        if (data.getForca() <= 0) {
            changeState(FaunaState.MORTO);
            return true;
        } else {
            IElementoComForca weakerElemento = findWeakerFauna();
            if (weakerElemento != null && weakerElemento instanceof Fauna) {
                Fauna weakerFauna = (Fauna) weakerElemento;
                double originalForca = data.getForca();
                data.setForca(data.getForca() - 10);

                if (data.getForca() > 0) {
                    weakerFauna.setForca(weakerFauna.getForca() - originalForca);
                    if (weakerFauna.getForca() <= 0) {
                        context.getEcossistema().removerElemento(weakerFauna.getId());
                        data.setForca(data.getForca() + weakerFauna.getForca());
                    }
                } else {
                    weakerFauna.setForca(weakerFauna.getForca() + originalForca);
                    context.getEcossistema().removerElemento(data.getId());
                    changeState(FaunaState.MORTO);
                }
                return true;
            } else {
                changeState(FaunaState.MOVIMENTO);
                return true;
            }
        }
    }

    private IElementoComForca findWeakerFauna() {
        IElementoComForca weakerFauna = null;
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

    @Override
    public FaunaState getState() {
        return FaunaState.ATAQUE;
    }

}
