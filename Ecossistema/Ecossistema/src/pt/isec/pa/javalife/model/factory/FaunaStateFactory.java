package pt.isec.pa.javalife.model.factory;

import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;

public class FaunaStateFactory {
    private FaunaStateFactory(){}


    public static IFaunaState getInstance(FaunaState type, FaunaContext context, Fauna data) {
        return switch (type) {
            case INITIAL -> new InitialState(context, data);
            case RUNNING -> new RunningState(context, data);
            case PAUSED -> new PausedState(context, data);
            case DEAD -> new DeadState(context, data);
        };
    }
}
