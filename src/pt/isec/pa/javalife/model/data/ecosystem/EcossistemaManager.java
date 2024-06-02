package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.command.commands.*;
import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.GameEngineState;
import pt.isec.pa.javalife.model.memento.CareTaker;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EcossistemaManager implements Serializable {
    private final Ecossistema ecossistema;
    private final CommandManager commandManager;
    private final PropertyChangeSupport support;
    private final GameEngine gameEngine;
    private final CareTaker careTaker;

    public EcossistemaManager() {
        this.ecossistema = new Ecossistema();
        this.commandManager = new CommandManager();
        this.support = new PropertyChangeSupport(this);
        this.gameEngine = new GameEngine();
        this.careTaker = new CareTaker(ecossistema);
        gameEngine.registerClient(ecossistema);
    }

    public int getLargura() {
        return ecossistema.getLargura();
    }

    public int getAltura() {
        return ecossistema.getAltura();
    }

    public boolean iniciarJogo(long intervalo) {
        boolean resultado = gameEngine.start(intervalo);
       // System.out.println("Resultado da inicialização do GameEngine: " + resultado);
        return resultado;
    }

    public void pausarJogo() {
        gameEngine.pause();
    }

    public void retomarJogo() {
        gameEngine.resume();
    }

    public void definirIntervaloJogo(long novoIntervalo) {
        gameEngine.setInterval(novoIntervalo);
    }


    public IElemento encontrarElementoMaisProximo(Area origem, Elemento tipo) {
        return ecossistema.encontrarElementoMaisProximo(origem, tipo);
    }


    public void limparElementos() {
        ecossistema.limparElementos();
    }


    public IElemento buscarElemento(int id) {
        return ecossistema.buscarElemento(id);
    }
    public void adicionarElemento(IElemento elemento) {
        ICommand cmd = new AddElementoCmd(this, elemento);
        commandManager.executeCommand(cmd);
        support.firePropertyChange("elemento_adicionado", null, elemento);
    }

    public void adicionarElementoAleatoriamente2(Elemento tipoElemento) {
        IElemento elemento = ecossistema.adicionarElementoAleatoriamente(tipoElemento);
        adicionarElemento(elemento);
    }

    public void removerElemento(IElemento elemento) {
        ICommand cmd = new RemoveElementoCmd(this, elemento);
        commandManager.executeCommand(cmd);
        support.firePropertyChange("elemento_removido", elemento, null);
    }

    public void removerElemento(int id) {
        IElemento elemento = buscarElemento(id);
        if (elemento != null) {
            removerElemento(elemento);
        }
    }


    public void criarCerca() {
        ecossistema.cerca();
    }

    public Ecossistema getEcossistema() {
        return ecossistema;
    }


    public boolean isRunning() {
        return gameEngine.getCurrentState() == GameEngineState.RUNNING;
    }

    public boolean isPaused() {
        return gameEngine.getCurrentState() == GameEngineState.PAUSED;
    }

    public Collection<IElemento> obterElementos() {
        return ecossistema.getElementos();
    }


    public void exportarElementosParaCSV(File file) throws IOException {
        ecossistema.exportarElementosParaCSV(file);
    }

    public void importarElementosDeCSV(File file) throws IOException {
        ecossistema.importarElementosDeCSV(file);
    }

    public void setLargura(int largura) {
        ecossistema.setLargura(largura);
        support.firePropertyChange("ecossistema_atualizado", null, ecossistema);
    }

    public void setAltura(int altura) {
        ecossistema.setAltura(altura);
        support.firePropertyChange("ecossistema_atualizado", null, ecossistema);
    }

    public void undo() {
        commandManager.undo();
        support.firePropertyChange("undo", null, null);
    }

    public void redo() {
        commandManager.redo();
        support.firePropertyChange("redo", null, null);
    }

    // Métodos para snapshots
    public void salvarSnapshot() throws IOException {
        careTaker.save();
    }

    public void restaurarSnapshot() throws IOException, ClassNotFoundException {
        careTaker.undo();
    }

    public void clearCommandHistory() {
        commandManager.clearHistory();
        support.firePropertyChange("command_history_cleared", null, null);
    }

    public void injectForce(Fauna fauna, double additionalForce) {
        ICommand cmd = new InjectForceCmd(this, fauna, additionalForce);
        commandManager.executeCommand(cmd);
        support.firePropertyChange("forca_injetada", null, fauna);
    }

    public void applyHerbicide(Flora flora) {
        ICommand cmd = new ApplyHerbicideCmd(this, flora);
        commandManager.executeCommand(cmd);
        support.firePropertyChange("herbicida_aplicado", null, flora);
    }

    public void applySun() {
        ICommand cmd = new ApplySunCmd(this);
        commandManager.executeCommand(cmd);
        support.firePropertyChange("sol_aplicado", null, null);
    }

}