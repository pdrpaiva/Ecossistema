package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema
        implements Serializable, IGameEngineEvolve {
    private Set<IElemento> elementos;
    public Ecossistema() {
        this.elementos = new HashSet<>();
    }

    public void addElemento(IElemento elemento) {
        elementos.add(elemento);
    }

    public void removeElemento(IElemento elemento) {
        elementos.remove(elemento);
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        //Lógica de evolução do ecossistema
    }
    //TODO
}
