package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;

public enum FaunaState {
    INITIAL, RUNNING, PAUSED,DEAD;

    public static IFaunaState getInstance(FaunaState type, FaunaContext context, Fauna data){
        return switch (type){
            case INITIAL -> new InitialState(context,data);
            case RUNNING -> new RunningState(context,data);
            case PAUSED -> new PausedState(context,data);
            case DEAD -> new DeadState(context,data);
        };
    }

    public IFaunaState getInstance(FaunaContext context, Fauna data){
        return switch (this){
            case INITIAL -> new InitialState(context,data);
            case RUNNING -> new RunningState(context,data);
            case PAUSED -> new PausedState(context,data);
            case DEAD -> new DeadState(context,data);
        };
    }
}
