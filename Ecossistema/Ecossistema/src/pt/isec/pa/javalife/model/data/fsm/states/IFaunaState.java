package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.fsm.FaunaState;

public interface IFaunaState {
    boolean mover();
    boolean comer();
    boolean descansar();
    boolean morrer();

    FaunaState getState();
}
