package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.command.CommandManager;
import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import java.util.Random;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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

    public void addElemento(IElemento elemento) {
        ecossistema.addElemento(elemento);
        support.firePropertyChange("elemento_adicionado", null, elemento);
    }

    public void removeElemento(int id) {
        IElemento elemento = ecossistema.buscarElemento(id);
        ecossistema.removerElemento(id);
        support.firePropertyChange("elemento_removido", elemento, null);
    }


    public Ecossistema getEcossistema(){
        return ecossistema;
    }
    public void cerca(){
        ecossistema.cerca();
    }

    public void evolve(long currentTime) {
        ecossistema.evolve(gameEngine, currentTime);
        support.firePropertyChange("evolucao", null, null);
    }

    public void addElementToRandomFreePosition(Elemento elementType) {
        int height = ecossistema.getUnidadesY();
        int width = ecossistema.getUnidadesX();
        Random random = new Random();

        boolean added = false;
        while (!added) {
            double x = random.nextInt(width);
            double y = random.nextInt(height);

            Area area = null;
            IElemento elemento = null;

            switch (elementType) {
                case INANIMADO:
                    area = new Area(y, x, y + Inanimado.size, x + Inanimado.size);
                    if (ecossistema.isAreaFree(area)) {
                        elemento = new Inanimado(y, x);
                        addElemento(elemento);
                        added = true;
                    }
                    break;
                case FAUNA:
                    area = new Area(y, x, y + 32, x + 32); // Supondo tamanho de fauna como 32x32
                    if (ecossistema.isAreaFree(area)) {
                        elemento = new Fauna(y, x, ecossistema);
                        addElemento(elemento);
                        added = true;
                    }
                    break;
                case FLORA:
                    area = new Area(y, x, y + 13, x + 13); // Supondo tamanho de flora como 13x13
                    if (ecossistema.isAreaFree(area)) {
                        elemento = new Flora(y, x);
                        addElemento(elemento);
                        added = true;
                    }
                    break;
            }
        }
    }

    public void setEcossistema(Ecossistema ecossistema) {
        this.ecossistema.limparElementos();
        this.ecossistema.obterElementos().addAll(ecossistema.obterElementos());
        support.firePropertyChange("ecossistema_atualizado", null, ecossistema);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }
}
