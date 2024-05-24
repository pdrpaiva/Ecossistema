package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;

public final class Fauna extends ElementoBase implements IElementoComImagem, IElementoComForca {

    private double forca;
    private String imagem;
    private boolean vivo;

    public Fauna(int id, Area area, double forca, String imagem) {
        super(id, Elemento.FAUNA, area);
        this.forca = forca;
        this.imagem = imagem;
        this.vivo = true;
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

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }


}
