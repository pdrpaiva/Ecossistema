package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElementoComForca;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.FaunaStateAdapter;

import java.util.Random;

public class MovimentoState extends FaunaStateAdapter {
    private static final double PERDA_FORCA_MOVIMENTO = 0.5;

    public MovimentoState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public boolean executar() {
        // Verifica se a força está abaixo do limiar para procurar comida
        if (data.getForca() < 35) {
            changeState(FaunaState.PROCURA_COMIDA);
            return true;
        }

        // Movimento da fauna e perda de força associada
        moverFauna();

        // Atualiza a força após o movimento
        data.setForca(data.getForca() - PERDA_FORCA_MOVIMENTO);

        // Verifica se a força caiu abaixo de 35 após o movimento
        if (data.getForca() < 35) {
            changeState(FaunaState.PROCURA_COMIDA);
            return true;
        }

        // Outras lógicas de movimento
        // Se a força estiver acima de 50, procurar fauna mais forte para se dirigir
        if (data.getForca() > 50) {
            Fauna strongerFauna = findStrongerFauna();
            if (strongerFauna != null) {
                moverPara(strongerFauna.getArea());
                return true;
            }
        }

        return false;
    }

    private void moverFauna() {
        // Lógica para movimentar a fauna de acordo com sua velocidade e direção
        // Placeholder para a lógica de movimento real
        // Por exemplo, atualiza a posição da fauna no ecossistema
    }

    private void moverPara(Area area) {
        // Lógica para mover a fauna em direção a uma área específica
        // Placeholder para a lógica de movimento real
    }

    private Fauna findStrongerFauna() {
        Fauna strongerFauna = null;
        double highestForca = -1;

        for (IElementoComForca elemento : context.getEcossistema().getElementos().stream()
                .filter(e -> e instanceof IElementoComForca)
                .map(e -> (IElementoComForca) e)
                .toList()) {
            if (elemento instanceof Fauna && elemento != data) {
                Fauna fauna = (Fauna) elemento;
                if (fauna.getForca() > highestForca) {
                    highestForca = fauna.getForca();
                    strongerFauna = fauna;
                }
            }
        }
        return strongerFauna;
    }

    @Override
    public FaunaState getState() {
        return FaunaState.MOVIMENTO;
    }
}
