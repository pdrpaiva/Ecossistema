package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;

import java.util.Random;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem {
    private static final double FORCA_INICIAL = 50;
    private static final double INCREMENTO_FORCA = 0.5;
    private static final int TAMANHO = 13;
    private double forca;
    private String imagem;
    private int numeroReproducoes;
    private boolean tentativaDeReproduzir = false;
    private int altura1;
    private int largura1;

    public Flora(double cima, double esquerda, int largura, int altura) {
        super(Elemento.FLORA, cima, esquerda, largura, altura);
        this.forca = FORCA_INICIAL;
        this.numeroReproducoes = 0;
        altura1=altura;
        largura1=largura;
    }

    public Flora(double cima, double esquerda) {
        this(cima, esquerda, gerarTamanhoAleatorio(), gerarTamanhoAleatorio());
    }

    private static int gerarTamanhoAleatorio() {
        Random rand = new Random();
        return 10 + rand.nextInt(50 - 10 + 1);
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

        for (int i = 0; i < 4; i++) {
            int larguraAleatoria = gerarTamanhoAleatorio();
            int alturaAleatoria = gerarTamanhoAleatorio();
            Area novaArea = null;

            switch (i) {
                case 0: // Esquerda
                    novaArea = new Area(areaAtual.cima(), areaAtual.esquerda() - larguraAleatoria, areaAtual.cima() + altura1, areaAtual.esquerda());
                    break;
                case 1: // Direita
                    novaArea = new Area(areaAtual.cima(), areaAtual.esquerda() + largura1, areaAtual.cima() + altura1, areaAtual.esquerda() + largura1 + larguraAleatoria);
                    break;
                case 2: // Abaixo
                    novaArea = new Area(areaAtual.baixo(), areaAtual.esquerda(), areaAtual.baixo() + alturaAleatoria, areaAtual.esquerda() + largura1);
                    break;
                case 3: // Acima
                    novaArea = new Area(areaAtual.cima() - alturaAleatoria, areaAtual.esquerda(), areaAtual.cima(), areaAtual.esquerda() + largura1);
                    break;
            }

            if (novaArea != null && !ecossistema.verificarLimites(novaArea) && ecossistema.verificarAreaLivre(novaArea)) {
                ecossistema.criarFloraComTamanho(novaArea, FORCA_INICIAL, this.imagem, larguraAleatoria, alturaAleatoria);
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