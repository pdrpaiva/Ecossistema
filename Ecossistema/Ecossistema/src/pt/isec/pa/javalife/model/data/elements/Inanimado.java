package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

public final class Inanimado extends ElementoBase {
    public static final int size = 16;

    public Inanimado(double cima, double esquerda) {
        super(Elemento.INANIMADO, cima,esquerda,size,size);
    }
    public void setArea(double cima, double esquerda, double baixo, double direita) {
        this.area = new Area(cima, esquerda, baixo, direita);
    }

  /*  @Override
    public int getId() {
        return 0;
    }

    @Override
    public Elemento getType() {
        return null;
    }

    @Override
    public Area getArea() {
        return null;
    } */
}
