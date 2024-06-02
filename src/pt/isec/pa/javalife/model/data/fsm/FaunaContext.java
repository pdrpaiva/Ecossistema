package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.data.fsm.states.MovimentoState;

import java.io.Serializable;

public class FaunaContext implements Serializable {
    private IFaunaState currentState;
    private Fauna data;
    private final Ecossistema ecossistema;

    public FaunaContext(Fauna data, Ecossistema ecossistema) {
        this.data = data;
        this.ecossistema = ecossistema;
        this.currentState = new MovimentoState(this, data);
    }

    public void setData(Fauna data) {
        this.data = data;
    }

    public boolean executar() {
        return currentState.executar();
    }

    public void changeState(IFaunaState newState) {
        this.currentState = newState;
    }

    public Ecossistema getEcossistema() {
        return ecossistema;
    }

    public Fauna getData() {
        return data;
    }

    public IFaunaState getCurrentState() {
        return currentState;
    }

    public String getCurrentStateAsString() {
        return currentState.toString();
    }
}