package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;
import java.util.Random;

public final class Inanimado extends ElementoBase {


    private int tamanho;

    public Inanimado(double cima, double esquerda) {
        this(cima, esquerda, gerarTamanhoAleatorio(), gerarTamanhoAleatorio());
    }

    public Inanimado(double cima, double esquerda, int largura, int altura) {
        super(Elemento.INANIMADO, cima, esquerda, largura, altura);
        this.tamanho = Math.max(largura, altura); // Definindo o tamanho como o maior valor entre largura e altura
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

    public int getSize() {
        return this.tamanho;
    }

    public void setSize(int tamanho) {
        this.tamanho = tamanho;
    }

    public void mover(double deslocamentoX, double deslocamentoY) {
        double largura = area.direita() - area.esquerda();
        double altura = area.baixo() - area.cima();
        this.area = new Area(area.cima() + deslocamentoY, area.esquerda() + deslocamentoX, area.cima() + deslocamentoY + altura, area.esquerda() + deslocamentoX + largura);
    }
}