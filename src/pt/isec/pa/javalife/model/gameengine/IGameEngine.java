package pt.isec.pa.javalife.model.gameengine;

public interface IGameEngine {
    void registerClient(IGameEngineEvolve listener);
    void unregisterClient(IGameEngineEvolve listener);
    boolean start(long interval);

    boolean stop();

    boolean pause();
    boolean resume();
    GameEngineState getCurrentState();

    long getInterval();

    void setInterval(long newInterval);

    void waitForTheEnd();
}