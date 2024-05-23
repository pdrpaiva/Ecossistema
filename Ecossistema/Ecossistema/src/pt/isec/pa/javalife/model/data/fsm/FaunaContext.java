package pt.isec.pa.javalife.model.data.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.IElemento;
import pt.isec.pa.javalife.model.data.fsm.states.IFaunaState;
import pt.isec.pa.javalife.model.data.fsm.states.MovimentoState;

import java.util.HashSet;
import java.util.Set;

public class FaunaContext {
    private IFaunaState atual;
    private final Set<IElemento> elementos;
    private Fauna data;

    public FaunaContext(Fauna data){
        this.data = data;
        this.elementos = new HashSet<>();
        this.atual = new MovimentoState(this,data);
    }

    //package private
    void changeState(IFaunaState newState) {
        atual = newState;
    }

    public FaunaState getState() {
        return atual.getState();
    }

    public boolean mover() {
        return atual.mover();
    }

    public boolean procurarComida() {
        return atual.procurarComida();
    }

    public boolean alimentar(){
        return atual.alimentar();
    }


    public boolean atacar() {
        return atual.atacar();
    }

    public boolean morrer() {
        return atual.morrer();
    }

    public void addElemento(IElemento elemento){
        elementos.add(elemento);
    }

    public void removeElemento(int id){
        elementos.removeIf(elemento -> elemento.getId() == id);
    }

    public Set<IElemento> getElementos(){
        return new HashSet<>(elementos);
    }

}
