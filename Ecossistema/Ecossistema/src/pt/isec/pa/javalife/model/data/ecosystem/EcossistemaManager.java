package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

public class EcossistemaManager {

    private final Ecossistema ecossistema;
    private final CommandManager commandManager;
    private final PropertyChangeSupport support;
    private final GameEngine gameEngine;

    public EcossistemaManager() {
        this.ecossistema = new Ecossistema();
        this.commandManager = new CommandManager();
        this.support = new PropertyChangeSupport(this);
        this.gameEngine = new GameEngine();
        gameEngine.registerClient(ecossistema);
    }

    public int getLargura() {
        return ecossistema.getLargura();
    }

    public int getAltura() {
        return ecossistema.getAltura();
    }
    public boolean startGame(long interval) {
        return gameEngine.start(interval);
    }



    public void stopGame() {
        gameEngine.stop();
    }

    public void pauseGame() {
        gameEngine.pause();
    }

    public void resumeGame() {
        gameEngine.resume();
    }

    public void setGameInterval(long newInterval) {
        gameEngine.setInterval(newInterval);
    }

    public GameEngineState getCurrentState() {
        return gameEngine.getCurrentState();
    }


    public void definirUnidadesX(int unidadesX) {
        ecossistema.definirUnidadesX(unidadesX);
    }

    public void definirUnidadesY(int unidadesY) {
        ecossistema.definirUnidadesY(unidadesY);
    }

    public IElemento encontrarElementoMaisProximo(Area origem, Elemento tipo) {
        return ecossistema.encontrarElementoMaisProximo(origem, tipo);
    }

    public boolean isAreaFree(Area area) {
        return ecossistema.isAreaFree(area);
    }

    public int getUnidadesX() {
        return ecossistema.getUnidadesX();
    }

    public int getUnidadesY() {
        return ecossistema.getUnidadesY();
    }

    public Set<IElemento> getElementos() {
        return ecossistema.getElementos();
    }

    public Set<IElemento> obterElementos() {
        return ecossistema.obterElementos();
    }

    public int obterPassos() {
        return ecossistema.obterPassos();
    }

    public void resetarContadorDePassos() {
        ecossistema.resetarContadorDePassos();
    }

    public Fauna encontrarFaunaMaisFraca(int ignorarID) {
        return ecossistema.encontrarFaunaMaisFraca(ignorarID);
    }

    public void limparElementos() {
        ecossistema.limparElementos();
    }

    public Fauna encontrarFaunaMaisForte(int ignorarID) {
        return ecossistema.encontrarFaunaMaisForte(ignorarID);
    }

    public IElemento buscarElemento(int id) {
        return ecossistema.buscarElemento(id);
    }

    public void removerElemento(int id) {
        ecossistema.removerElemento(id);
    }

    public void addElemento(IElemento elemento) {
        ecossistema.addElemento(elemento);
        support.firePropertyChange("elemento_adicionado", null, elemento);
    }

    public boolean verificarAreaLivre(Area area) {
        return ecossistema.verificarAreaLivre(area);
    }

    public boolean verificarLimites(Area area) {
        return ecossistema.verificarLimites(area);
    }

    public void evolve(long currentTime) {
        ecossistema.evolve(gameEngine, currentTime);
        support.firePropertyChange("evolucao", null, null);
    }

    public void cerca() {
        ecossistema.cerca();
    }

    public void adicionarElemento(IElemento elemento) {
        ecossistema.adicionarElemento(elemento);
    }

    public Area encontrarAreaAdjacenteLivre(Area area) {
        return ecossistema.encontrarAreaAdjacenteLivre(area);
    }

    public int gerarProximoIdFauna() {
        return ecossistema.gerarProximoIdFauna();
    }

    public int gerarProximoIdFlora() {
        return ecossistema.gerarProximoIdFlora();
    }

    public int gerarProximoIdInanimado() {
        return ecossistema.gerarProximoIdInanimado();
    }

    public Fauna criarFauna(double cima, double esquerda) {
        return ecossistema.criarFauna(cima, esquerda);
    }

    public Flora criarFlora(Area area, double forca, String imagem) {
        return ecossistema.criarFlora(area, forca, imagem);
    }

    public Inanimado criarInanimado(Area area) {
        return ecossistema.criarInanimado(area);
    }

    public void addElementToRandomFreePosition(Elemento elementType) {
        ecossistema.addElementToRandomFreePosition(elementType);
    }

    public void setEcossistema(Ecossistema novoEcossistema) {
        ecossistema.setEcossistema(novoEcossistema);
        support.firePropertyChange("ecossistema_atualizado", null, ecossistema);
    }
    public Ecossistema getEcossistema() {
        return ecossistema.getEcossistema();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    public boolean isRunning() {
        return gameEngine.getCurrentState() == GameEngineState.RUNNING;
    }

    // Método isPaused para verificar se o jogo está pausado
    public boolean isPaused() {
        return gameEngine.getCurrentState() == GameEngineState.PAUSED;
    }
}