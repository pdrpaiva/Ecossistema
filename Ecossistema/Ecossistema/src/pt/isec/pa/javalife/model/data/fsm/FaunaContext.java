package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.data.fsm.states.MovimentoState;

public class FaunaContext {
    IFaunaState atual;
    Fauna data;

    public FaunaContext(Fauna data){
        this.data = data;
        this.atual = new MovimentoState(this,data);
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

    public boolean procurarComida() {
        return atual.procurarComida();
    }




    public boolean atacar() {
        return atual.atacar();
    }

    public boolean morrer() {
        return atual.morrer();
    }


}
