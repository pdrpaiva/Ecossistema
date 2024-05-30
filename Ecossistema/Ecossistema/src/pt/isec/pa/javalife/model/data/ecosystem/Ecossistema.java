package pt.isec.pa.javalife.model.data.ecosystem;

import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.data.fsm.FaunaContext;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

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

    public Ecossistema() {
        this.elementos = new HashSet<>();
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
    public Ecossistema getEcossistema() {
        return this;
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
        }
    }

    public void addElemento(IElemento elemento) {
        elementos.add(elemento);
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
        System.out.println("Evoluindo o ecossistema");
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
                        addElemento(novaFlora);
                        flora.setForca(50);
                        flora.incrementaNumeroReproducoes();
                    }
                }
            }
        }

        elementos.removeAll(elementosParaRemover);
        totalPassos++;
    }

    public void cerca() {
        int wallThickness = 4;

        // Parede Superior
        Inanimado top = new Inanimado(0, 0);
        top.setArea(0, 0, wallThickness, this.getLargura());

        // Parede Inferior
        Inanimado bottom = new Inanimado(0, 0);
        bottom.setArea(this.getAltura() - wallThickness, 0, this.getAltura(), this.getLargura());

        // Parede Esquerda
        Inanimado left = new Inanimado(0, 0);
        left.setArea(0, 0, this.getAltura(), wallThickness);

        // Parede Direita
        Inanimado right = new Inanimado(0, 0);
        right.setArea(0, this.getLargura() - wallThickness, this.getAltura(), this.getLargura());

        // Adiciona as paredes ao ecossistema
        adicionarElemento(top);
        adicionarElemento(bottom);
        adicionarElemento(left);
        adicionarElemento(right);
    }

    public void adicionarElemento(IElemento elemento) {
        elementos.add(elemento);
    }

    public Area encontrarAreaAdjacenteLivre(Area area) {
        // Implementar a lógica para encontrar uma área adjacente livre
        return null;
    }

    // Métodos para geração de IDs únicos para cada tipo de elemento
    public int gerarProximoIdFauna() {
        return nextFaunaId++;
    }

    public int gerarProximoIdFlora() {
        return nextFloraId++;
    }

    public int gerarProximoIdInanimado() {
        return nextInanimadoId++;
    }

    // Métodos para criação de elementos específicos
    public Fauna criarFauna(double cima, double esquerda) {
        Fauna fauna = new Fauna(cima, esquerda, this);
        addElemento(fauna);
        return fauna;
    }

    public Flora criarFlora(Area area, double forca, String imagem) {
        Flora flora = new Flora(area.cima(), area.esquerda());
        flora.setForca(forca);
        flora.setImagem(imagem);
        addElemento(flora);
        return flora;
    }

    public Inanimado criarInanimado(Area area) {
        Inanimado inanimado = new Inanimado(area.cima(), area.esquerda());
        addElemento(inanimado);
        return inanimado;
    }

    public void setEcossistema(Ecossistema novoEcossistema) {
        limparElementos();
        elementos.addAll(novoEcossistema.obterElementos());
    }

    public void addElementToRandomFreePosition(Elemento elementType) {
        int height = getUnidadesY();
        int width = getUnidadesX();
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
                    if (isAreaFree(area)) {
                        elemento = new Inanimado(y, x);
                        addElemento(elemento);  // Adiciona o elemento ao ecossistema
                        added = true;
                    }
                    break;
                case FAUNA:
                    area = new Area(y, x, y + 32, x + 32); // Supondo tamanho de fauna como 32x32
                    if (isAreaFree(area)) {
                        elemento = new Fauna(y, x, this);
                        addElemento(elemento);  // Adiciona o elemento ao ecossistema
                        added = true;
                    }
                    break;
                case FLORA:
                    area = new Area(y, x, y + 13, x + 13); // Supondo tamanho de flora como 13x13
                    if (isAreaFree(area)) {
                        elemento = new Flora(y, x);
                        addElemento(elemento);  // Adiciona o elemento ao ecossistema
                        added = true;
                    }
                    break;
            }
        }
    }
}