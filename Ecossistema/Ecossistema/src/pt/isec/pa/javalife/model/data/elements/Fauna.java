package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;

public final class Fauna extends ElementoBase implements IElementoComImagem, IElementoComForca {
    private double forca;
    private String imagem;
    private boolean vivo;
    private final FaunaContext faunaContext;

    public Fauna(int id, Area area, double forca, String imagem, Ecossistema ecossistema) {
        super(id, Elemento.FAUNA, area);
        this.forca = forca;
        this.imagem = imagem;
        this.vivo = true;
        this.faunaContext = new FaunaContext(this,ecossistema); // Inicializa o contexto da fauna
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

    public FaunaContext getFaunaContext() {
        return faunaContext;
    }
}
