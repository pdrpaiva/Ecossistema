package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.fsm.FaunaState;

public interface IFaunaState {
    boolean mover();
    boolean procurarComida();
    boolean alimentar();
    boolean atacar();
    boolean morrer();

    FaunaState getState();
}
