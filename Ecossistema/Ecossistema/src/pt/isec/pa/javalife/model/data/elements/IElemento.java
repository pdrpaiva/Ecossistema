package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

import java.io.Serializable;

public sealed interface IElemento
        extends Serializable
        permits ElementoBase {
    int getId(); // retorna o identificador
    Elemento getTipo(); // retorna o tipo
    Area getArea(); // retorna a área ocupada
    void setPosicaoX(int x);
    void setPosicaoY(int y);

}

