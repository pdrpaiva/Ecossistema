package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;
import pt.isec.pa.javalife.model.data.fsm.Direction;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public final class Fauna extends ElementoBase implements IElementoComImagem, IElementoComForca {

    private static final double FORCA_INICIAL = 50;
    private static final double FORCA_MAXIMA = 100;
    private static final double CUSTO_MOVIMENTO = 0.5;
    private static final int TEMPO_REPRODUCAO = 10;
    private static final double DISTANCIA_REPRODUCAO = 20;
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
    private int tamanho1;
    private int tamanho2;


    public Fauna(double cima, double esquerda, Ecossistema ecossistema) {
        this(cima, esquerda, gerarTamanhoAleatorio(), gerarTamanhoAleatorio(), ecossistema);
    }

    public Fauna(double cima, double esquerda, int largura, int altura, Ecossistema ecossistema) {
        super(Elemento.FAUNA, cima, esquerda, largura, altura);
        this.forca = FORCA_INICIAL;
        this.imagem = "lion"; // Placeholder para a imagem, pode ser alterada conforme necessário
        this.vivo = true;
        this.faunaContext = new FaunaContext(this, ecossistema);
        this.direcaoAtual = Direction.RIGHT;
        this.direcaoAlternativa = null;
        this.velocidade = 1.0;
        this.tempoProximidadeOutroFauna = 0;
        this.tamanho1 = largura ;
        this.tamanho2 = altura ;// Definindo tamanho como a área
    }
    public int getLargura() {
        return tamanho1;
    }

    public int getAltura() {
        return tamanho2;
    }

    private static int gerarTamanhoAleatorio() {
        Random rand = new Random();
        return 10 + rand.nextInt(50 - 10 + 1);
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
            setArea(novaArea.cima(), novaArea.esquerda(), this.tamanho1, this.tamanho2);
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

    public void moveParaAlvo(IElemento alvo) {
        double deltaX = alvo.getArea().esquerda() - this.getArea().esquerda();
        double deltaY = alvo.getArea().cima() - this.getArea().cima();

        Direction dirX = (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
        Direction dirY = (deltaY > 0) ? Direction.DOWN : Direction.UP;

        boolean obstaculoDirX = temObstaculoNaDirecao(dirX);
        boolean obstaculoDirY = temObstaculoNaDirecao(dirY);

        // Se a direção alternativa ainda não expirou
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

    public void verificarReproducao(Set<IElemento> elementos) {
        // Filtra elementos do tipo Fauna
        Set<Fauna> elementosFauna = elementos.stream()
                .filter(e -> e instanceof Fauna)
                .map(e -> (Fauna) e)
                .collect(Collectors.toSet());

        boolean pertoFauna = false;

        for (Fauna outro : elementosFauna) {
            if (outro != this && Area.distancia(this.getArea(), outro.getArea()) < DISTANCIA_REPRODUCAO) {
                pertoFauna = true;
                break;
            }
        }

        if (pertoFauna) {
            incrementarTempoProximidadeOutroFauna();
        } else {
            resetarTempoProximidadeOutroFauna();
        }

        if (tempoProximidadeOutroFauna >= TEMPO_REPRODUCAO && pertoFauna && this.forca >= 25) {
            reproduzir(elementosFauna);
            resetarTempoProximidadeOutroFauna();
        }
    }
    private void reproduzir(Set<Fauna> elementosFauna) {
        // Encontra uma zona livre ou com flora mais próxima
        Area areaReproducao = faunaContext.getEcossistema().encontrarAreaAdjacenteLivre(this.getArea());
        if (areaReproducao != null) {
            Fauna novaFauna = faunaContext.getEcossistema().criarFauna(areaReproducao.cima(), areaReproducao.esquerda());
            if (novaFauna != null) {
                elementosFauna.add(novaFauna);
                this.perderForca(25);
            }
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
