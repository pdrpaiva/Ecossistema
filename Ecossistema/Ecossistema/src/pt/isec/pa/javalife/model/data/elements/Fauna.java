package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;
import pt.isec.pa.javalife.model.data.fsm.Direction;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;

import java.util.Set;

public final class Fauna extends ElementoBase implements IElementoComImagem, IElementoComForca {

    private static final double FORCA_INICIAL = 50;
    private static final double FORCA_MAXIMA = 100;
    private static final double CUSTO_MOVIMENTO = 0.5;
    private static final int TEMPO_REPRODUCAO = 10;
    private static final double DISTANCIA_REPRODUCAO = 5;
    private static final int TAMANHO = 32;
    private static final double CUSTO_ATAQUE = 10;
    private double forca;
    private String imagem;
    private boolean vivo;
    private final FaunaContext faunaContext;
    private Direction direcao;
    private double velocidade;
    private int tempoProximidadeOutroFauna;

    public Fauna(double cima, double esquerda, Ecossistema ecossistema) {
        super(Elemento.FAUNA, cima, esquerda, TAMANHO, TAMANHO);
        this.forca = FORCA_INICIAL;
        this.imagem = "default.png";  // Placeholder para a imagem, pode ser alterada conforme necessário
        this.vivo = true;
        this.faunaContext = new FaunaContext(this, ecossistema);
        this.direcao = Direction.RIGHT;
        this.velocidade = 1.0;
        this.tempoProximidadeOutroFauna = 0;
    }

    public double getForca() {
        return forca;
    }

    public void setForca(double forca) {
        this.forca = Math.min(forca, FORCA_MAXIMA);
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public FaunaContext getFaunaContext() {
        return faunaContext;
    }

    public Direction getDirecao() {
        return direcao;
    }

    public void setDirecao(Direction direcao) {
        this.direcao = direcao;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public int getTempoProximidadeOutroFauna() {
        return tempoProximidadeOutroFauna;
    }

    public void incrementarTempoProximidadeOutroFauna() {
        this.tempoProximidadeOutroFauna++;
    }

    public void resetarTempoProximidadeOutroFauna() {
        this.tempoProximidadeOutroFauna = 0;
    }

    public void mover() {
        Area novaArea = getArea().mover(direcao, velocidade);
        if (!faunaContext.getEcossistema().verificarLimites(novaArea)) {
            setArea(novaArea.cima(), novaArea.esquerda(), novaArea.baixo(), novaArea.direita());
            perderForca(CUSTO_MOVIMENTO);
        } else {
            direcao = direcao.oposta();
            perderForca(CUSTO_MOVIMENTO);
        }
    }

    public void perderForca(double quantidade) {
        setForca(getForca() - quantidade);
        if (getForca() <= 0) {
            setVivo(false);
            faunaContext.changeState(FaunaState.MORTO.getInstance(faunaContext, this));
        }
    }

    public void atacar(Fauna outraFauna) {
        setForca(getForca() - CUSTO_ATAQUE);
        if (getForca() > 0) {
            outraFauna.setForca(outraFauna.getForca() - getForca());
            if (outraFauna.getForca() <= 0) {
                outraFauna.setVivo(false);
                faunaContext.getEcossistema().removerElemento(outraFauna.getId());
                setForca(getForca() + outraFauna.getForca());
            }
        } else {
            outraFauna.setForca(outraFauna.getForca() + getForca());
            setVivo(false);
            faunaContext.getEcossistema().removerElemento(getId());
        }
    }

    public void verificarReproducao(Fauna outraFauna) {
        if (Area.distancia(this.getArea(), outraFauna.getArea()) < TAMANHO) {
            incrementarTempoProximidadeOutroFauna();
            outraFauna.incrementarTempoProximidadeOutroFauna();

            if (getTempoProximidadeOutroFauna() >= TEMPO_REPRODUCAO && outraFauna.getTempoProximidadeOutroFauna() >= TEMPO_REPRODUCAO) {
                Area areaReproducao = faunaContext.getEcossistema().encontrarAreaAdjacenteLivre(this.getArea());
                if (areaReproducao != null) {
                    faunaContext.getEcossistema().criarFauna(areaReproducao.cima(), areaReproducao.esquerda());
                    perderForca(25);
                    outraFauna.perderForca(25);
                    resetarTempoProximidadeOutroFauna();
                    outraFauna.resetarTempoProximidadeOutroFauna();
                }
            }
        } else {
            resetarTempoProximidadeOutroFauna();
            outraFauna.resetarTempoProximidadeOutroFauna();
        }
    }

    public void moveParaAlvo(IElemento alvo) {
        double deltaX = alvo.getArea().esquerda() - this.getArea().esquerda();
        double deltaY = alvo.getArea().cima() - this.getArea().cima();

        Direction dirX = (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
        Direction dirY = (deltaY > 0) ? Direction.DOWN : Direction.UP;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            setDirecao(dirX);
        } else {
            setDirecao(dirY);
        }

        if (!getArea().intersecta(alvo.getArea())) {
            mover();
        }
    }

    public void mudarParaDirecaoAleatoria() {
        setDirecao(Direction.direcaoAleatoria());
    }

    public boolean temObstaculoAFrente(Set<IElemento> elementos) {
        Area areaFutura = getArea().mover(direcao, velocidade);

        for (IElemento elemento : elementos) {
            if (elemento.getTipo() == Elemento.INANIMADO && areaFutura.intersecta(elemento.getArea())) {
                return true;
            }
        }

        return false;
    }


    @Override
    public void setPosicaoX(int x) {

    }

    @Override
    public void setPosicaoY(int y) {

    }
}
