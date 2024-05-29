package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

public abstract sealed class ElementoBase
        implements IElemento
        permits Inanimado, Flora, Fauna {

    private final int id;
    private final Elemento type;
    private final Area area;


    public ElementoBase(int id, Elemento type, Area area) {
        this.id = id;
        this.type = type;
        this.area = area;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public Elemento getType() {
        return type;
    }

    @Override
    public Area getArea() {
        return area;
    }
}
