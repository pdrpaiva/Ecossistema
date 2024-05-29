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

    public Flora(double cima, double esquerda) {
        super(Elemento.FLORA,cima,esquerda,TAMANHO,TAMANHO);
        this.forca = FORCA_INICIAL;
        this.numeroReproducoes = 0;
    }


    public void evolve(Ecossistema ecossistema,long tempoAtual){

        if(forca == 0)
            return;

        //forca += ecossistema.isEventoSolAtivo() ? INCREMENTO_FORCA * 2 : INCREMENTO_FORCA; Comentado pois não temos eventos ainda...

        if(forca >= 90 && numeroReproducoes < 2){
            if(tentarReproduzir(ecossistema)){
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

        // for (double[] pos : posicoesAdjacentes) {
        // Area novaArea = new Area(pos[1], pos[0], pos[1] + TAMANHO, pos[0] + TAMANHO);
        // if (!ecossistema.isForaLimites(novaArea) && ecossistema.isAreaLivre(novaArea)) {
        // ecossistema.addElemento(Elemento.FLORA, pos[0], pos[1]);
        // return true;
        // }
        // }
        return false;
    }

    public double getForca() {
        return forca;
    }

    public void setForca(double forca) {
        this.forca = forca;
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

    public void incrementaNumeroReproducoes(){
       numeroReproducoes += numeroReproducoes + 1;
    }


}