package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;
import java.util.Random;

public final class Inanimado extends ElementoBase {


    private int tamanho;
    private boolean pertenceACerca;

    public Inanimado(double cima, double esquerda) {
        this(cima, esquerda, gerarTamanhoAleatorio(), gerarTamanhoAleatorio());
    }

    public Inanimado(double cima, double esquerda, int largura, int altura) {
        super(Elemento.INANIMADO, cima, esquerda, largura, altura);
        this.tamanho = Math.max(largura, altura); // Definindo o tamanho como o maior valor entre largura e altura
    }

    public Inanimado(double cima, double esquerda, int largura, int altura, boolean pertenceACerca) {
        super(Elemento.INANIMADO, cima, esquerda, largura, altura);
        this.tamanho = Math.max(largura, altura); // Definindo o tamanho como o maior valor entre largura e altura
        this.pertenceACerca = pertenceACerca;
    }

    private static int gerarTamanhoAleatorio() {
        Random rand = new Random();
        return 10 + rand.nextInt(50 - 10 + 1);
    }

    @Override
    public void setArea(double cima, double esquerda, double baixo, double direita) {
        this.area = new Area(cima, esquerda, baixo, direita);
        this.tamanho = Math.max((int)(direita - esquerda), (int)(baixo - cima));
    }

    @Override
    public void setPosicaoX(int x) {
        setPosition(x, area.cima());
    }

    @Override
    public void setPosicaoY(int y) {
        setPosition(area.esquerda(), y);
    }
    public boolean isPertenceACerca() {
        return pertenceACerca;
    }

    public void setPertenceACerca(boolean pertenceACerca) {
        this.pertenceACerca = pertenceACerca;
    }

}