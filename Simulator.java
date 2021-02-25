package Raposas_e_Coelhos_simulacao;


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
    private static final int DEFAULT_WIDTH = 120;
    // A profundidade padrão da grade.
    private static final int DEFAULT_DEPTH = 80;

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
        // Fornece espaço para o animal recém-nascido.
        List<Animal> newAnimales = new ArrayList<Animal>();
        // Deixe todos os animais agirem.
        for(Iterator<Animal> ite = generator.getAnimalsList().iterator(); ite.hasNext();)
        {
            Animal animal = ite.next();
            animal.act(newAnimales);
            if(! animal.isAlive())
            {
                ite.remove();
            }
        }
        
        generator.getAnimalsList().addAll(newAnimales);
        

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

