package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem {

    private double forca;
    private String imagem;


    public Flora(int id, Area area, double forca, String imagem) {
        super(id, Elemento.FLORA, area);
        this.forca = forca;
        this.imagem = imagem;
    }



   /* @Override
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

    @Override
    public double getForca() {
        return forca;
    }

    @Override
    public void setForca(double forca) {
        this.forca = forca;
    }

    @Override
    public String getImagem() {
        return imagem;
    }

    @Override
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
