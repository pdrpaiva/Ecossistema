package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.data.fsm.states.MovimentoState;

import java.util.Set;
import java.util.HashSet;

public class FaunaContext {
    private IFaunaState currentState;
    private Fauna data;
    private final Set<IElemento> elementos;
    private final Ecossistema ecossistema;

    public FaunaContext(Fauna data, Ecossistema ecossistema) {
        this.data = data;
        this.elementos = new HashSet<>();
        this.currentState = FaunaState.MOVIMENTO.getInstance(this, data); // Estado inicial
        this.ecossistema = ecossistema;
    }

    public void setData(Fauna data) {
        this.data = data;
    }

    public FaunaState getState() {
        return currentState.getState();
    }

    public void changeState(IFaunaState newState) {
        currentState = newState;
    }

    public boolean executar() {
        return currentState.executar();
    }

    public void addElemento(IElemento elemento) {
        elementos.add(elemento);
    }

    public void removeElemento(int id) {
        elementos.removeIf(elemento -> elemento.getId() == id);
    }

    public Set<IElemento> getElementos() {
        return new HashSet<>(elementos);
    }

    public Ecossistema getEcossistema() {
        return ecossistema;
    }
}
