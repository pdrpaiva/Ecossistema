
package pt.isec.pa.javalife.model.data.area;

import pt.isec.pa.javalife.model.data.fsm.Direction;

import java.io.Serializable;

public record Area(double cima, double esquerda, double baixo, double direita) implements Serializable {

    public static double distancia(Area a1, Area a2) {
        double deltaX = a1.esquerda() - a2.esquerda();
        double deltaY = a1.cima() - a2.cima();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public boolean intersecta(Area outra) {
        return !(this.direita <= outra.esquerda || this.esquerda >= outra.direita ||
                this.baixo <= outra.cima || this.cima >= outra.baixo);
    }
    public boolean isAdjacent(Area outra) {
        boolean adjacenteHorizontalmente = (this.direita == outra.esquerda || this.esquerda == outra.direita) &&
                (this.cima < outra.baixo && this.baixo > outra.cima);

        boolean adjacenteVerticalmente = (this.baixo == outra.cima || this.cima == outra.baixo) &&
                (this.esquerda < outra.direita && this.direita > outra.esquerda);

        return adjacenteHorizontalmente || adjacenteVerticalmente;
    }



    public Area mover(Direction direcao, double velocidade) {
        double largura = this.direita - this.esquerda;
        double altura = this.baixo - this.cima;
        return switch (direcao) {
            case LEFT -> new Area(this.cima, this.esquerda - velocidade, this.cima + altura, this.esquerda - velocidade + largura);
            case RIGHT -> new Area(this.cima, this.esquerda + velocidade, this.cima + altura, this.esquerda + velocidade + largura);
            case UP -> new Area(this.cima - velocidade, this.esquerda, this.cima - velocidade + altura, this.esquerda + largura);
            case DOWN -> new Area(this.cima + velocidade, this.esquerda, this.cima + velocidade + altura, this.esquerda + largura);
        };
    }

}
