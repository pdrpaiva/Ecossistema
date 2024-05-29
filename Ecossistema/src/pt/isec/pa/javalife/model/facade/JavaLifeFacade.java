package pt.isec.pa.javalife.model.facade;

import pt.isec.pa.javalife.model.data.ecosystem.Ecossistema;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.command.ICommand;
import pt.isec.pa.javalife.model.command.commands.AddElementoCmd;
import pt.isec.pa.javalife.model.command.commands.EditElementoCmd;
import pt.isec.pa.javalife.model.command.commands.RemoveElementoCmd;
//import pt.isec.pa.javalife.model.memento.Caretaker;
//import pt.isec.pa.javalife.model.memento.Memento;
//import pt.isec.pa.javalife.model.memento.Snapshot;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class JavaLifeFacade {
    private final EcossistemaManager manager;
    private final CommandManager commandManager;
    private final PropertyChangeSupport support;
    private final GameEngine gameEngine;

    public JavaLifeFacade() {
        this.manager = new EcossistemaManager();
        this.commandManager = new CommandManager();
        this.support = new PropertyChangeSupport(this);
        this.gameEngine = new GameEngine();
        gameEngine.registerClient(manager.getEcossistema());
    }

    // Métodos de manipulação de elementos
    public void addElemento(IElemento elemento) {
        ICommand command = new AddElementoCmd(manager, elemento);
        commandManager.executeCommand(command);
        notifyObservers("elementoAdicionado", null, elemento);
    }

    public void editElemento(IElemento original, IElemento updated) {
        ICommand command = new EditElementoCmd(manager, original, updated);
        commandManager.executeCommand(command);
        notifyObservers("elementoEditado", original, updated);
    }

    public void removeElemento(IElemento elemento) {
        ICommand command = new RemoveElementoCmd(manager, elemento);
        commandManager.executeCommand(command);
        notifyObservers("elementoRemovido", elemento, null);
    }

    // Métodos de undo/redo
    public void undo() {
        commandManager.undo();
        notifyObservers("undo", null, null);
    }

    public void redo() {
        commandManager.redo();
        notifyObservers("redo", null, null);
    }

    // Métodos de snapshots
    public void saveSnapshot() {
      //  Memento memento = new Snapshot(manager.getEcossistema());
       // caretaker.addMemento(memento);
        notifyObservers("snapshotSaved", null, null);
    }

    public void restoreSnapshot() {
       // Memento memento = caretaker.undo();
       // if (memento != null) {
        //    manager.setEcossistema((Ecossistema) memento.getState());
         //   notifyObservers("snapshotRestored", null, null);
       // }
    }

    // Métodos de persistência
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(manager.getEcossistema());
        }
        notifyObservers("savedToFile", null, null);
    }

    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Ecossistema ecossistema = (Ecossistema) ois.readObject();
            manager.setEcossistema(ecossistema);
        }
        notifyObservers("loadedFromFile", null, null);
    }

    // Métodos de importação/exportação CSV
    public void exportToCSV(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (IElemento elemento : manager.getEcossistema().getElementos()) {
                writer.write(elemento.getType() + "," + elemento.getId() + "," +
                        elemento.getArea().cima() + "," + elemento.getArea().esquerda() + "," +
                        elemento.getArea().baixo() + "," + elemento.getArea().direita() + "\n");
            }
        }
        notifyObservers("exportedToCSV", null, null);
    }

    public void importFromCSV(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Crie e adicione elementos com base nos dados do CSV
                // Certifique-se de evitar sobreposições e elementos fora da área de simulação
            }
        }
        notifyObservers("importedFromCSV", null, null);
    }

    // Métodos de gestão do GameEngine
    public void startSimulation(long interval) {
        gameEngine.start(interval);
        notifyObservers("simulationStarted", null, null);
    }

    public void stopSimulation() {
        gameEngine.stop();
        notifyObservers("simulationStopped", null, null);
    }

    public void pauseSimulation() {
        gameEngine.pause();
        notifyObservers("simulationPaused", null, null);
    }

    public void resumeSimulation() {
        gameEngine.resume();
        notifyObservers("simulationResumed", null, null);
    }

    // Métodos de observação
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    private void notifyObservers(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

    // Métodos auxiliares
    public Set<IElemento> getElementos() {
        return new HashSet<>(manager.getEcossistema().getElementos());
    }

    public EcossistemaManager getManager() {
        return manager;
    }
}

