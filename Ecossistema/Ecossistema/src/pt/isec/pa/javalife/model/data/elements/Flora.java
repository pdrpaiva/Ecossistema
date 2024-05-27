package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem {
    private double forca;
    private String imagem;
    private int reproductions;

    public Flora(int id, Area area, double forca, String imagem) {
        super(id, Elemento.FLORA, area);
        this.forca = forca;
        this.imagem = imagem;
        this.reproductions = 0;
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

    public int getReproductions() {
        return reproductions;
    }

    public void incrementReproductions() {
        this.reproductions++;
    }
}