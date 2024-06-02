package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

public abstract sealed class ElementoBase
        implements IElemento
        permits Inanimado, Flora, Fauna {

    private static int ultimoID = 0;
    private final int id;
    private final Elemento tipo;
    protected Area area;

    public ElementoBase(Elemento tipo, double cima, double esquerda, double largura, double altura) {
        this.id = ++ultimoID;
        this.tipo = tipo;
        this.area = new Area(cima, esquerda, cima + altura, esquerda + largura);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Elemento getTipo() {
        return tipo;
    }

    @Override
    public Area getArea() {
        return area;
    }

    public void setArea(double cima, double esquerda, double largura, double altura) {
        this.area = new Area(cima, esquerda, cima + altura, esquerda + largura);
    }


    public double getPositionX() {
        return area.esquerda();
    }

    public double getPositionY() {
        return area.cima();
    }

    @Override
    public ElementoBase clone() {
        try {
            ElementoBase cloned = (ElementoBase) super.clone();
            cloned.area = new Area(this.area.cima(), this.area.esquerda(), this.area.baixo(), this.area.direita());
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public void setPosicaoX(int x) {
        setPosition(x, area.cima());
    }

    @Override
    public void setPosicaoY(int y) {
        setPosition(area.esquerda(), y);
    }

    public void setPosition(double positionX, double positionY) {
        double largura = area.direita() - area.esquerda();
        double altura = area.baixo() - area.cima();
        setArea(positionY, positionX, largura, altura);
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
