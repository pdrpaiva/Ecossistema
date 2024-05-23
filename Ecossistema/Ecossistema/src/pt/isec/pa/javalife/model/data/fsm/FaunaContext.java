package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;

public class FaunaContext {
    IFaunaState atual;
    Fauna data;

    public FaunaContext(Fauna data){
        this.data = data;
        this.atual = new InitialState(this,data);
    }

    //package private
    void changeState(IFaunaState newState) {
        atual = newState;
    }

    public FaunaState getState() {
        return atual.getState();
    }

    public boolean mover() {
        return atual.mover();
    }

    public boolean comer() {
        return atual.comer();
    }

    public boolean descansar() {
        return atual.descansar();
    }

    public boolean morrer() {
        return atual.morrer();
    }


}
