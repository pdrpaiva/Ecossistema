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
    private static final double CUSTO_MOVIMENTO = 0.1;
    private static final int TEMPO_REPRODUCAO = 10;
    private static final double DISTANCIA_REPRODUCAO = 50;
    private static final int TAMANHO = 13;
    private static final double CUSTO_ATAQUE = 10;
    private double forca;
    private String imagem;
    private boolean vivo;
    private final FaunaContext faunaContext;
    private Direction direcaoAtual;
    private Direction direcaoAlternativa;
    private int contadorIteracoes = 0;
    private final int MAX_ITERACOES = 4;
    private double velocidade;
    private int tempoProximidadeOutroFauna;

    public Fauna(double cima, double esquerda, Ecossistema ecossistema) {
        super(Elemento.FAUNA, cima, esquerda, TAMANHO, TAMANHO);
        this.forca = FORCA_INICIAL;
        this.imagem = "default.png"; // Placeholder para a imagem, pode ser alterada conforme necessário
        this.vivo = true;
        this.faunaContext = new FaunaContext(this, ecossistema);
        this.direcaoAtual = Direction.RIGHT;
        direcaoAlternativa = null;
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
        return direcaoAtual;
    }

    public void setDirecao(Direction direcao) {
        this.direcaoAtual = direcao;
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
        Area areaAntiga = getArea();
        Area novaArea = areaAntiga.mover(direcaoAtual, velocidade);

        boolean dentroDosLimites = !faunaContext.getEcossistema().verificarLimites(novaArea);

        boolean colide = false;
        for (IElemento elemento : faunaContext.getEcossistema().getElementos()) {
            // Verifica se o elemento é do tipo fauna ou inanimado
            if (elemento.getTipo() == Elemento.INANIMADO || elemento.getTipo() == Elemento.FAUNA) {
                Area areaElemento = elemento.getArea();
                if (!areaElemento.equals(areaAntiga) && novaArea.intersecta(areaElemento)) {
                    colide = true;
                    break;
                }
            }
        }
        if (dentroDosLimites && !colide) {
            setArea(novaArea.cima(), novaArea.esquerda(), TAMANHO, TAMANHO);
            perderForca(CUSTO_MOVIMENTO);
        } else {
            // Evitar a inversão de direção imediata
            if (direcaoAlternativa == null) {
                direcaoAlternativa = (direcaoAtual == Direction.LEFT || direcaoAtual == Direction.RIGHT) ? Direction.UP : Direction.LEFT;
                contadorIteracoes = 0;
            } else {
                direcaoAtual = direcaoAlternativa;
            }
            perderForca(CUSTO_MOVIMENTO);
        }
    }

    public Fauna findStrongerFauna() {
        Ecossistema ecossistema = faunaContext.getEcossistema();
        Fauna strongerFauna = null;

        for (IElemento elemento : ecossistema.getElementos()) {
            if (elemento instanceof Fauna) {
                Fauna fauna = (Fauna) elemento;
                if (fauna.getForca() > this.getForca()) {
                    if (strongerFauna == null || fauna.getForca() > strongerFauna.getForca()) {
                        strongerFauna = fauna;
                    }
                }
            }
        }

        return strongerFauna;
    }
    public Fauna findNearbyFauna() {
        Ecossistema ecossistema = faunaContext.getEcossistema();
        for (IElemento elemento : ecossistema.getElementos()) {
            if (elemento instanceof Fauna) {
                Fauna fauna = (Fauna) elemento;
                if (fauna != this && Area.distancia(this.getArea(), fauna.getArea()) < Fauna.DISTANCIA_REPRODUCAO) {
                    return fauna;
                }
            }
        }
        return null;
    }
    public void moveParaAlvo(IElemento alvo) {
        double deltaX = alvo.getArea().esquerda() - this.getArea().esquerda();
        double deltaY = alvo.getArea().cima() - this.getArea().cima();

        Direction dirX = (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
        Direction dirY = (deltaY > 0) ? Direction.DOWN : Direction.UP;

        boolean obstaculoDirX = temObstaculoNaDirecao(dirX);
        boolean obstaculoDirY = temObstaculoNaDirecao(dirY);

        // Se a direção alternativa ainda não expirou, continue usando-a
        if (direcaoAlternativa != null && contadorIteracoes < MAX_ITERACOES) {
            contadorIteracoes++;
            direcaoAtual = direcaoAlternativa;
        } else {
            // Resetar a direção alternativa e o contador
            direcaoAlternativa = null;
            contadorIteracoes = 0;

            // Decidir a nova direção com base nos obstáculos
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (!obstaculoDirX) {
                    direcaoAtual = dirX;
                } else if (!obstaculoDirY) {
                    direcaoAtual = dirY;
                    direcaoAlternativa = dirY; // Definir a direção alternativa
                } else {
                    // Se ambas as direções principais estão bloqueadas, escolher a melhor alternativa
                    direcaoAtual = (dirY == Direction.DOWN) ? Direction.UP : Direction.DOWN;
                    direcaoAlternativa = direcaoAtual; // Definir a direção alternativa
                }
            } else {
                if (!obstaculoDirY) {
                    direcaoAtual = dirY;
                } else if (!obstaculoDirX) {
                    direcaoAtual = dirX;
                    direcaoAlternativa = dirX; // Definir a direção alternativa
                } else {
                    // Se ambas as direções principais estão bloqueadas, escolher a melhor alternativa
                    direcaoAtual = (dirX == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
                    direcaoAlternativa = direcaoAtual; // Definir a direção alternativa
                }
            }
        }

        setDirecao(direcaoAtual);

        if (!getArea().intersecta(alvo.getArea())) {
            mover();
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
        if (Area.distancia(this.getArea(), outraFauna.getArea()) < DISTANCIA_REPRODUCAO) {
            System.out.println("GOIABA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            incrementarTempoProximidadeOutroFauna();
            outraFauna.incrementarTempoProximidadeOutroFauna();

            if (getTempoProximidadeOutroFauna() >= TEMPO_REPRODUCAO && outraFauna.getTempoProximidadeOutroFauna() >= TEMPO_REPRODUCAO) {
                Area areaReproducao = faunaContext.getEcossistema().encontrarAreaAdjacenteLivre(this.getArea());
                System.out.println("AREA_REPRODUCAO!!!!!!!!!!!!!!!!!!!!!!!");
                if (areaReproducao != null) {
                    System.out.println("AREA REPRODUCAO NÃO É NULL!!!!!!!!!!!!!");
                    if(faunaContext.getEcossistema().criarFauna(areaReproducao.cima(), areaReproducao.esquerda()) == null){
                        System.out.println("NÃO REPRODUZIU????????????????????????????????");
                    }else {
                        System.out.println("REPRODUZIU????????????????????????????????");
                    }
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


    public boolean temObstaculoNaDirecao(Direction dir) {
        // Calcular a área futura com base na direção fornecida
        Area areaFutura = getArea().mover(dir, velocidade);

        for (IElemento elemento : faunaContext.getEcossistema().getElementos()) {
            if (elemento.getTipo() == Elemento.INANIMADO &&elemento.getTipo() == Elemento.FAUNA && areaFutura.intersecta(elemento.getArea())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setPosicaoX(int x) {
        // Implementação para atualizar a posição X
    }

    @Override
    public void setPosicaoY(int y) {
        // Implementação para atualizar a posição Y
    }
}
