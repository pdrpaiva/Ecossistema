package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema
        implements Serializable, IGameEngineEvolve {
    private final Set<IElemento> elementos;
    private final FaunaContext faunaContext;
    private final PropertyChangeSupport pcs;
    private final Area area;



    public Ecossistema() {
        this.elementos = new HashSet<>();
        this.faunaContext = new FaunaContext(null); // Inicialize com dados apropriados
        this.pcs = new PropertyChangeSupport(this);
        this.area = new Area(0, 0, 500, 500); // Inicializa com valores padrão
    }

    public void addElemento(IElemento elemento) {
        elementos.add(elemento);
        pcs.firePropertyChange("elemento adicionado",null,elemento); // ver o que significa
    }

    public void removeElemento(int id) {
        IElemento elementoRemovido = elementos.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (elementoRemovido != null) {
            elementos.remove(elementoRemovido);
            pcs.firePropertyChange("elementoRemovido", elementoRemovido, null); //ver o que significa
        }
    }

    public FaunaContext getFaunaContext() {
        return faunaContext;
    }


    public Set<IElemento> getElementos() {
        return new HashSet<>(elementos);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }


    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        // Implementação da Lógica de evolução do ecossistema
        faunaContext.getElementos().stream()
                .filter(e -> e instanceof Fauna)
                .forEach(e -> faunaContext.mover());

    }
    //TODO
}
