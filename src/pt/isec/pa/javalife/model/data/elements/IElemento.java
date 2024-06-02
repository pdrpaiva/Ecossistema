package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

import java.io.Serializable;

public sealed interface IElemento
        extends Serializable
        permits ElementoBase {
    int getId();
    Elemento getTipo();
    Area getArea();
    void setPosicaoX(int x);
    void setPosicaoY(int y);

    double getPositionX();
    public double getPositionY();
}

