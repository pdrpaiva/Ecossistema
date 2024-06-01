package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem {
    private static final double FORCA_INICIAL = 50;
    private static final double INCREMENTO_FORCA = 0.5;
    private static final int TAMANHO = 13;
    private double forca;
    private String imagem;
    private int numeroReproducoes;
    private boolean tentativaDeReproduzir = false;

    public Flora(double cima, double esquerda) {
        super(Elemento.FLORA, cima, esquerda, TAMANHO, TAMANHO);
        this.forca = FORCA_INICIAL;
        this.numeroReproducoes = 0;
    }

//    public void evolve(Ecossistema ecossistema, long tempoAtual) {
//        if (forca == 0) {
//            return;
//        }
//
//        // Comentar esta linha pois não temos eventos ainda.
//        // forca += ecossistema.isEventoSolAtivo() ? INCREMENTO_FORCA * 2 : INCREMENTO_FORCA;
//        forca += INCREMENTO_FORCA;
//
//        if (forca >= 90 && numeroReproducoes < 2) {
//            if (tentarReproduzir(ecossistema)) {
//                forca = 60;
//                numeroReproducoes++;
//            }
//        }
//    }
public void evolve(Ecossistema ecossistema, long tempoAtual) {
    if (forca == 0) {
        return;
    }

    // Incrementa a força
   // forca += INCREMENTO_FORCA;

    // Tenta reproduzir se a força for >= 90 e o número de reproduções for < 2
    if (forca >= 90 && numeroReproducoes < 2) {
        if (tentarReproduzir(ecossistema)) {
            forca = 60;
            numeroReproducoes++;
        }
    }
}

    private boolean tentarReproduzir(Ecossistema ecossistema) {
        Area areaAtual = this.getArea();
        double[][] posicoesAdjacentes = {
                {areaAtual.esquerda() - TAMANHO, areaAtual.cima()},
                {areaAtual.esquerda() + TAMANHO, areaAtual.cima()},
                {areaAtual.esquerda(), areaAtual.baixo()},
                {areaAtual.esquerda(), areaAtual.cima() - TAMANHO}
        };

        for (double[] pos : posicoesAdjacentes) {
            Area novaArea = new Area(pos[1], pos[0], pos[1] + TAMANHO, pos[0] + TAMANHO);
            if (!ecossistema.verificarLimites(novaArea) && ecossistema.verificarAreaLivre(novaArea)) {
                ecossistema.criarFlora(novaArea, FORCA_INICIAL, this.imagem);
                return true;
            }
        }
        return false;
    }

    public double getForca() {
        return forca;
    }

    public void setForca(double forca) {
        this.forca = forca;
        if (this.forca > 100) {
            this.forca = 100;
        }
        if (this.forca < 0) {
            this.forca = 0;
        }
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getNumeroReproducoes() {
        return numeroReproducoes;
    }

    public void incrementaNumeroReproducoes() {
        numeroReproducoes += numeroReproducoes + 1;
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