package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.command.commands.AddElementoCmd;
import pt.isec.pa.javalife.model.command.commands.EditElementoCmd;
import pt.isec.pa.javalife.model.command.commands.RemoveElementoCmd;
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

    public void pararJogo() {
        gameEngine.stop();
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

    public GameEngineState getEstadoAtual() {
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

    public boolean isAreaLivre(Area area) {
        return ecossistema.isAreaFree(area);
    }

    public int getUnidadesX() {
        return ecossistema.getUnidadesX();
    }

    public int getUnidadesY() {
        return ecossistema.getUnidadesY();
    }

//    public Set<IElemento> getElementos() {
//        return ecossistema.getElementos();
//    }

    public Set<IElemento> getElementos() {
      return ecossistema.getElementos();
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
    public void adicionarElemento(IElemento elemento) {
        ICommand cmd = new AddElementoCmd(this, elemento);
        commandManager.executeCommand(cmd);
        support.firePropertyChange("elemento_adicionado", null, elemento);
    }

    public void adicionarElementoAleatoriamente2(Elemento tipoElemento) {
        IElemento elemento = ecossistema.adicionarElementoAleatoriamente(tipoElemento);
        adicionarElemento(elemento);
    }
    public void editarElemento(IElemento original, IElemento updated) {
        ICommand cmd = new EditElementoCmd(this, original, updated);
        commandManager.executeCommand(cmd);
        support.firePropertyChange("elemento_editado", original, updated);
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

    public boolean verificarAreaLivre(Area area) {
        return ecossistema.verificarAreaLivre(area);
    }

    public boolean verificarLimites(Area area) {
        return ecossistema.verificarLimites(area);
    }

    public void evoluir(long tempoAtual) {
        ecossistema.evolve(gameEngine, tempoAtual);
        support.firePropertyChange("evolucao", null, null);
    }

    public void criarCerca() {
        ecossistema.cerca();
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

    public void adicionarElementoAleatoriamente(Elemento tipoElemento) {
        ecossistema.adicionarElementoAleatoriamente(tipoElemento);
    }

    public void setEcossistema(Ecossistema novoEcossistema) {
        ecossistema.setEcossistema(novoEcossistema);
        support.firePropertyChange("ecossistema_atualizado", null, ecossistema);
    }

    public Ecossistema getEcossistema() {
        return ecossistema;
    }

    public void adicionarPropertyChangeListener(String nomePropriedade, PropertyChangeListener listener) {
        support.addPropertyChangeListener(nomePropriedade, listener);
    }

    public void removerPropertyChangeListener(String nomePropriedade, PropertyChangeListener listener) {
        support.removePropertyChangeListener(nomePropriedade, listener);
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

    public void aplicarHerbicida(Flora flora) {
        ecossistema.aplicarHerbicida(flora);
    }

    public void injetarForca(Fauna fauna) {
        ecossistema.injetarForca(fauna);
    }

    public void aplicarSol() {
        ecossistema.aplicarSol();
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

    public void replaySnapshot() throws IOException, ClassNotFoundException {
        careTaker.redo();
    }

    public boolean hasUndo() {
        return careTaker.hasUndo();
    }

    public boolean hasRedo() {
        return careTaker.hasRedo();
    }

    public void clearCommandHistory() {
        commandManager.clearHistory();
        support.firePropertyChange("command_history_cleared", null, null);
    }
}