package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Ecossistema implements IGameEngineEvolve {
    private final Set<IElemento> elementos;
    private int totalPassos = 0;
    private int escalaUnidade = 2;
    private int unidadesX = 300;
    private int unidadesY = 300;
    private static int nextFaunaId = 1;
    private static int nextFloraId = 1;
    private static int nextInanimadoId = 1;
    private final PropertyChangeSupport support;

    public Ecossistema() {
        this.elementos = new HashSet<>();
        this.support = new PropertyChangeSupport(this);
    }

    public int getLargura() {
        return escalaUnidade * unidadesX;
    }

    public int getAltura() {
        return escalaUnidade * unidadesY;
    }

    public void definirUnidadesX(int unidadesX) {
        this.unidadesX = unidadesX;
    }

    public void definirUnidadesY(int unidadesY) {
        this.unidadesY = unidadesY;
    }

    public IElemento encontrarElementoMaisProximo(Area origem, Elemento tipo) {
        IElemento maisProximo = null;
        double menorDistancia = Double.MAX_VALUE;
        for (IElemento elemento : elementos) {
            if (elemento.getTipo() == tipo) {
                double distancia = Area.distancia(origem, elemento.getArea());
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    maisProximo = elemento;
                }
            }
        }
        return maisProximo;
    }

    public boolean isAreaFree(Area area) {
        for (IElemento elemento : elementos) {
            if (elemento.getArea().intersecta(area)) {
                return false;
            }
        }
        return true;
    }

    public int getUnidadesX() {
        return unidadesX;
    }

    public int getUnidadesY() {
        return unidadesY;
    }

    public Set<IElemento> getElementos() {
        return elementos;
    }

    public Set<IElemento> obterElementos() {
        return new HashSet<>(elementos);
    }

    public int obterPassos() {
        return totalPassos;
    }

    public void resetarContadorDePassos() {
        totalPassos = 0;
    }

    public Fauna encontrarFaunaMaisFraca(int ignorarID) {
        double menorForca = Double.MAX_VALUE;
        Fauna maisFraca = null;
        for (IElemento elemento : elementos) {
            if (elemento.getTipo() == Elemento.FAUNA && elemento.getId() != ignorarID) {
                Fauna fauna = (Fauna) elemento;
                if (fauna.getForca() < menorForca) {
                    menorForca = fauna.getForca();
                    maisFraca = fauna;
                }
            }
        }
        return maisFraca;
    }

    public void limparElementos() {
        elementos.clear();
        support.firePropertyChange("elementos", null, null); // Notifica a mudança
    }

    public Fauna encontrarFaunaMaisForte(int ignorarID) {
        double maiorForca = 0;
        Fauna maisForte = null;
        for (IElemento elemento : elementos) {
            if (elemento.getTipo() == Elemento.FAUNA && elemento.getId() != ignorarID) {
                Fauna fauna = (Fauna) elemento;
                if (fauna.getForca() > maiorForca) {
                    maiorForca = fauna.getForca();
                    maisForte = fauna;
                }
            }
        }
        return maisForte;
    }

    public IElemento buscarElemento(int id) {
        return elementos.stream().filter(elemento -> elemento.getId() == id).findFirst().orElse(null);
    }

    public void removerElemento(int id) {
        IElemento elemento = buscarElemento(id);
        if (elemento != null) {
            elementos.remove(elemento);
            support.firePropertyChange("elemento_removido", null, elemento); // Notifica a mudança
        }
    }

    public void adicionarElemento(IElemento elemento) {
        elementos.add(elemento);
        support.firePropertyChange("elemento_adicionado", null, elemento); // Notifica a mudança
    }

    public boolean verificarAreaLivre(Area area) {
        for (IElemento elemento : elementos) {
            if (elemento.getArea().intersecta(area)) {
                return false;
            }
        }
        return true;
    }

    public boolean verificarLimites(Area area) {
        return area.esquerda() < 0 || area.direita() > getLargura() || area.cima() < 0 || area.baixo() > getAltura();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        Set<IElemento> elementosParaRemover = new HashSet<>();

        for (IElemento elemento : new HashSet<>(elementos)) {
            if (elemento.getTipo() == Elemento.FAUNA) {
                Fauna fauna = (Fauna) elemento;
                FaunaContext context = fauna.getFaunaContext();
                context.setData(fauna);
                boolean mudou = context.executar();

                if (!fauna.isVivo()) {
                    elementosParaRemover.add(fauna);
                }
            } else if (elemento.getTipo() == Elemento.FLORA) {
                Flora flora = (Flora) elemento;
                flora.setForca(flora.getForca() + 0.5);
                if (flora.getForca() >= 90 && flora.getNumeroReproducoes() < 2) {
                    Area areaLivre = encontrarAreaAdjacenteLivre(flora.getArea());
                    if (areaLivre != null) {
                        Flora novaFlora = criarFlora(areaLivre, 50, flora.getImagem());
                        adicionarElemento(novaFlora);
                        flora.setForca(50);
                        flora.incrementaNumeroReproducoes();
                    }
                }
            }
        }

        elementos.removeAll(elementosParaRemover);
        totalPassos++;
        support.firePropertyChange("evolucao", null, null); // Notifica a mudança
    }

    public void cerca() {
        int espessuraParede = 4;

        // Parede Superior
        Inanimado topo = new Inanimado(0, 0);
        topo.setArea(0, 0, espessuraParede, this.getLargura());

        // Parede Inferior
        Inanimado fundo = new Inanimado(0, 0);
        fundo.setArea(this.getAltura() - espessuraParede, 0, this.getAltura(), this.getLargura());

        // Parede Esquerda
        Inanimado esquerda = new Inanimado(0, 0);
        esquerda.setArea(0, 0, this.getAltura(), espessuraParede);

        // Parede Direita
        Inanimado direita = new Inanimado(0, this.getLargura() - espessuraParede);
        direita.setArea(0, this.getLargura() - espessuraParede, this.getAltura(), this.getLargura());

        // Adiciona as paredes ao ecossistema
        adicionarElemento(topo);
        adicionarElemento(fundo);
        adicionarElemento(esquerda);
        adicionarElemento(direita);
    }

    public Area encontrarAreaAdjacenteLivre(Area area) {
        double[][] posicoesAdjacentes = {
                {area.esquerda() - area.direita(), area.cima()},
                {area.esquerda() + area.direita(), area.cima()},
                {area.esquerda(), area.cima() - area.baixo()},
                {area.esquerda(), area.cima() + area.baixo()}
        };

        for (double[] posicao : posicoesAdjacentes) {
            Area novaArea = new Area(posicao[1], posicao[0], posicao[1] + area.baixo() - area.cima(), posicao[0] + area.direita() - area.esquerda());
            if (verificarAreaLivre(novaArea) && !verificarLimites(novaArea)) {
                return novaArea;
            }
        }

        return null;
    }

    public int gerarProximoIdFauna() {
        return nextFaunaId++;
    }

    public int gerarProximoIdFlora() {
        return nextFloraId++;
    }

    public int gerarProximoIdInanimado() {
        return nextInanimadoId++;
    }

    public Fauna criarFauna(double cima, double esquerda) {
        Fauna fauna = new Fauna(cima, esquerda, this);
        adicionarElemento(fauna);
        return fauna;
    }

    public Flora criarFlora(Area area, double forca, String imagem) {
        Flora flora = new Flora(area.cima(), area.esquerda());
        flora.setForca(forca);
        flora.setImagem(imagem);
        adicionarElemento(flora);
        return flora;
    }

    public Inanimado criarInanimado(Area area) {
        Inanimado inanimado = new Inanimado(area.cima(), area.esquerda());
        adicionarElemento(inanimado);
        return inanimado;
    }

    public void setEcossistema(Ecossistema novoEcossistema) {
        limparElementos();
        elementos.addAll(novoEcossistema.obterElementos());
        support.firePropertyChange("ecossistema_atualizado", null, this); // Notifica a mudança
    }

    public void adicionarElementoAleatoriamente(Elemento tipoElemento) {
        int altura = getUnidadesY();
        int largura = getUnidadesX();
        Random random = new Random();

        boolean adicionado = false;
        while (!adicionado) {
            double x = random.nextInt(largura);
            double y = random.nextInt(altura);

            Area area = null;
            IElemento elemento = null;

            switch (tipoElemento) {
                case INANIMADO:
                    area = new Area(y, x, y + Inanimado.size, x + Inanimado.size);
                    if (isAreaFree(area)) {
                        elemento = new Inanimado(y, x);
                        adicionarElemento(elemento);
                        adicionado = true;
                    }
                    break;
                case FAUNA:
                    area = new Area(y, x, y + 32, x + 32); // Supondo tamanho de fauna como 32x32
                    if (isAreaFree(area)) {
                        elemento = new Fauna(y, x, this);
                        adicionarElemento(elemento);
                        adicionado = true;
                    }
                    break;
                case FLORA:
                    area = new Area(y, x, y + 13, x + 13); // Supondo tamanho de flora como 13x13
                    if (isAreaFree(area)) {
                        elemento = new Flora(y, x);
                        adicionarElemento(elemento);
                        adicionado = true;
                    }
                    break;
            }
        }
    }

    // Métodos para gerenciar os listeners
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}