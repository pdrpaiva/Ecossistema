package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.Direction;
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

        data.mover();
        // Verifica se a força caiu abaixo de 35 após o movimento
        if (data.getForca() < 35) {
            changeState(FaunaState.PROCURA_COMIDA);
            return true;
        }

        if (data.getForca() >= 50) {
            Fauna strongerFauna = data.findStrongerFauna();
            if (strongerFauna != null) {
                data.moveParaAlvo(strongerFauna);
                System.out.println("A seguir fauna mais forte");
                data.verificarReproducao(strongerFauna);
                return true;
            }
        }

        // Mudança aleatória de direção
        Random random = new Random();
        if (random.nextInt(100) < 10) {
            Direction currentDirection = data.getDirecao();
            if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
                data.setDirecao(random.nextBoolean() ? Direction.UP : Direction.DOWN);
            } else if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
                data.setDirecao(random.nextBoolean() ? Direction.LEFT : Direction.RIGHT);
            }
        }
        return false;
    }

    @Override
    public FaunaState getState() {
        return FaunaState.MOVIMENTO;
    }

    @Override
    public String toString() {
        return "Movimento";
    }
}