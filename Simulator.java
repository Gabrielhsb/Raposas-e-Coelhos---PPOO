package Raposas_e_Coelhos_simulacao;

/**
 *  Trabalho final de Práticas de Programação Orientada a Objetos
 * 
 * @author Gabriel Henrique Silva Bernardo
 * @author Lucas Floriano Bernardes de Castro
 * @author Kleber Halley Sallum Guimarães
 * @author Paulo Eduardo Tomaz de Biaso 
 */


import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;


public class Simulator
{
      // Constantes que representam informações de configuração para a simulação.
     // A largura padrão da grade.
    private static final int DEFAULT_WIDTH = 100;
    // A profundidade padrão da grade.
    private static final int DEFAULT_DEPTH = 100;

    // A etapa atual da simulação.
    private int step;
    // O estado atual do campo.
    private Field field;
    // Uma visão gráfica da simulação.
    private SimulatorView view;
    
    private PopulationGenerator generator;

      /**
      * Construa um campo de simulação com tamanho padrão.
      */
    public Simulator(){
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
      /**
      * Crie um campo de simulação com o tamanho determinado.
      * @param depth Profundidade do campo. Deve ser maior que zero.
      * @param width Largura do campo. Deve ser maior que zero.
      */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        field = new Field(depth, width);
        // Crie uma visualização do estado de cada local no campo.
        view = new SimulatorView(depth, width);
        generator = new PopulationGenerator(field, view);
        // Configure um ponto de partida válido.
        reset();
    }
    
/**
      * Execute a simulação de seu estado atual por um período razoavelmente longo(4000 etapas).
      */
    public void runLongSimulation(){
        simulate(4000);
    }
    
      /**
      * Execute a simulação de seu estado atual para um determinado número de etapas.
      * Pare antes de um determinado número de etapas se deixar de ser viável.
      * @param numSteps O número de etapas a serem executadas.
      */
    public void simulate(int numSteps) {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
      * Execute a simulação de seu estado atual para uma única etapa.
      * Repita em todo o campo atualizando o estado de cada
      * raposa e coelho.
      */
    public void simulateOneStep(){
        step++;
        // Fornece espaço para o novo actor .
        List<Actor> newActors = new ArrayList<Actor>();
        // Deixe todos os animais agirem.
        for(Iterator<Actor> ite = generator.getAnimalsList().iterator(); ite.hasNext();)
        {
            Actor actor = ite.next();
            actor.act(newActors);
            if(! actor.isActive())
            {
                ite.remove();
            }
        }
        
        generator.getAnimalsList().addAll(newActors);
        

        view.showStatus(step, field);
    }
        
    /**
      * Redefina a simulação para uma posição inicial.
      */
    
    public void reset(){
        step = 0;
        generator.getAnimalsList().clear();
        generator.populate();
        
        // Mostra o estado inicial na visualização.
        view.showStatus(step, field);
    }

        public PopulationGenerator getGenerator(){
        return this.generator;
    }

}
