package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.data.elements.IElemento;

import java.util.HashMap;
import java.util.Map;

public class EcossistemaManager {

    private final Ecossistema ecossistema;

    public EcossistemaManager() {
        this.ecossistema = new Ecossistema();
    }

    public void addElemento(IElemento elemento) {
        ecossistema.addElemento(elemento);
    }

    public void removeElemento(int id) {
            ecossistema.removeElemento(id);
    }

    public Ecossistema getEcossistema(){
        return ecossistema;
    }


    public void evolve(long currentTime){
        //ecossistema.evolve(currentTime);
    }


    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema.getElementos().clear();
        this.ecossistema.getElementos().addAll(ecossistema.getElementos());
    }
}
