package pt.isec.pa.javalife.model.data.fsm.states;

public interface IState {
    void enterState();
    void exitState();
    void handleEvent(String event);
}
