package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.elements.Inanimado;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.data.fsm.FaunaState;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Ecossistema implements Serializable, IGameEngineEvolve {
    private final Set<IElemento> elementos;
    private static int nextFaunaId = 1;
    private static int nextFloraId = 1;
    private static int nextInanimadoId = 1;

    public Ecossistema() {
        this.elementos = new HashSet<>();
    }

    public void addElemento(IElemento elemento) {
        elementos.add(elemento);
    }

    public void removeElemento(int id) {
        elementos.removeIf(elemento -> elemento.getId() == id);
    }

    public Set<IElemento> getElementos() {
        return new HashSet<>(elementos);
    }

    public int getNextFaunaId() {
        return nextFaunaId++;
    }

    public int getNextFloraId() {
        return nextFloraId++;
    }

    public int getNextInanimadoId() {
        return nextInanimadoId++;
    }

    public Fauna createFauna(Area area, double forca, String imagem) {
        Fauna fauna = new Fauna(getNextFaunaId(), area, forca, imagem,this);
        addElemento(fauna);
        return fauna;
    }

    public Flora createFlora(Area area, double forca, String imagem) {
        Flora flora = new Flora(getNextFloraId(), area, forca, imagem);
        addElemento(flora);
        return flora;
    }

    public Inanimado createInanimado(Area area) {
        Inanimado inanimado = new Inanimado(getNextInanimadoId(), area);
        addElemento(inanimado);
        return inanimado;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        Set<IElemento> elementosToRemove = new HashSet<>();

        for (IElemento elemento : new HashSet<>(elementos)) {
            if (elemento instanceof Fauna) {
                Fauna fauna = (Fauna) elemento;
                FaunaContext context = fauna.getFaunaContext();
                context.setData(fauna); // Atualiza o contexto para a fauna atual

                boolean changed = context.executar();

                if (!fauna.isVivo()) {
                    elementosToRemove.add(fauna); // Marca para remoção
                }
            } else if (elemento instanceof Flora) {
                Flora flora = (Flora) elemento;
                flora.setForca(flora.getForca() + 0.5); // Crescimento da flora
                if (flora.getForca() >= 90 && flora.getReproductions() < 2) {
                    // Lógica de reprodução da flora
                    Area freeArea = findFreeAdjacentArea(flora.getArea());
                    if (freeArea != null) {
                        createFlora(freeArea, 50, flora.getImagem());
                        flora.setForca(50);
                        flora.incrementReproductions();
                    }
                }
            }
        }

        elementos.removeAll(elementosToRemove); // Remove os elementos mortos
    }
    private Area findFreeAdjacentArea(Area area) {
        // Implementar lógica para encontrar uma área adjacente livre
        return null; // Placeholder
    }
}
