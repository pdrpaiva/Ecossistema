package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElementoComForca;
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
        System.out.println("Executando estado Movimento");

        // Verifica se a força está abaixo do limiar para procurar comida
        if (data.getForca() < 35) {
            System.out.println("Força abaixo de 35, mudando para PROCURA_COMIDA");
            changeState(FaunaState.PROCURA_COMIDA);
            return true;
        }

        // Movimento da fauna e perda de força associada
        System.out.println("Movendo fauna");
        data.mover();

        // Verifica se a força caiu abaixo de 35 após o movimento
        if (data.getForca() < 35) {
            System.out.println("Força caiu abaixo de 35 após o movimento, mudando para PROCURA_COMIDA");
            changeState(FaunaState.PROCURA_COMIDA);
            return true;
        }

        // Outras lógicas de movimento
        // Se a força estiver acima de 50, procurar fauna mais forte para se dirigir
        if (data.getForca() >= 50) {
            Fauna strongerFauna = findStrongerFauna();
            if (strongerFauna != null) {
                System.out.println("Encontrou fauna mais forte, movendo-se para a área dessa fauna");
                moverPara(strongerFauna.getArea());
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

    private void moverPara(Area areaAlvo) {
        Direction direcaoParaNovaArea = calcularDirecaoPara(areaAlvo);

        // Movimenta a fauna na direção calculada
        Area novaArea = data.getArea().mover(direcaoParaNovaArea, data.getVelocidade());

        // Verifica se a nova área está dentro dos limites do ecossistema
        if (!context.getEcossistema().verificarLimites(novaArea)) {
            data.setArea(novaArea.cima(), novaArea.esquerda(),
                    novaArea.direita() - novaArea.esquerda(),
                    novaArea.baixo() - novaArea.cima());
            data.perderForca(PERDA_FORCA_MOVIMENTO);
        } else {
            // Se a nova área estiver fora dos limites, inverte a direção e perde força
            direcaoParaNovaArea = direcaoParaNovaArea.oposta();
            data.perderForca(PERDA_FORCA_MOVIMENTO);
        }
    }

    private Direction calcularDirecaoPara(Area areaAlvo) {
        double deltaX = areaAlvo.esquerda() - data.getArea().esquerda();
        double deltaY = areaAlvo.cima() - data.getArea().cima();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
        } else {
            return (deltaY > 0) ? Direction.DOWN : Direction.UP;
        }
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
