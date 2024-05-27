package pt.isec.pa.javalife.model.data.fsm.states;

import pt.isec.pa.javalife.model.data.fsm.FaunaState;

public interface IFaunaState {

    boolean executar();
    FaunaState getState();
}
