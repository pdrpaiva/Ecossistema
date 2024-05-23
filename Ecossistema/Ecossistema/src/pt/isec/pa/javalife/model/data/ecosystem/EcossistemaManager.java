package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.data.elements.IElemento;

import java.util.HashMap;
import java.util.Map;

public class EcossistemaManager {
    private final Ecossistema ecossistema;
    private final Map<Integer, IElemento> elementos;

    public EcossistemaManager() {
        this.ecossistema = new Ecossistema();
        this.elementos = new HashMap<>();
    }

    public void addElemento(IElemento elemento) {
        elementos.put(elemento.getId(), elemento);
        ecossistema.addElemento(elemento);
    }

    public void removeElemento(int id) {
        IElemento elemento = elementos.remove(id);
        if(elemento != null)
            ecossistema.removeElemento(elemento);
    }

    public IElemento getElemento(int id) {
        return elementos.get(id);
    }


    public Ecossistema getEcossistema(){
        return ecossistema;
    }
}
