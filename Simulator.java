package Raposas_e_Coelhos_simulacao;


import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;


public class Simulator
{
    // As variáveis ​​finais estáticas privadas representam
    // informações de configuração para a simulação.
    // A largura padrão da grade.
    private static final int DEFAULT_WIDTH = 50;
    // A profundidade padrão da grade.
    private static final int DEFAULT_DEPTH = 50;
    // A probabilidade de uma raposa ser criada em qualquer posição da grade.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    //A probabilidade de um coelho ser criado em qualquer posição da grade.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    

    // A lista de animais no campo
    private List animals;
    // A lista de animais recém-nascidos
    private List newAnimals;
    // O estado atual do campo.
    private Field field;
    // Um segundo campo, usado para construir o próximo estágio da simulação.
    private Field updatedField;
    // A etapa atual da simulação.
    private int step;
    // Uma visão gráfica da simulação
    private SimulatorView view;
    
    /**
     * Constroi um campo de simulação com tamanho padrão.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Cria um campo de simulação com o tamanho fornecido.
     * @param depth Profundidade de campo. Deve ser maior que zero.
     * @param width Largura do campo. Deve ser maior que zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("As dimensões devem ser maiores que zero.");
            System.out.println("Usando valores padrao.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        animals = new ArrayList();
        newAnimals = new ArrayList();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);

        // Cria uma visualização do estado de cada local no campo.
        view = new SimulatorView(depth, width);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Rabbit.class, Color.orange);
        
        // Configura um ponto de partida válido.
        reset();
    }
    
    /**
     * Executa a simulação de seu estado atual por um período razoavelmente longo,
     * e.g. 500 etapas.
     */
    public void runLongSimulation()
    {
        simulate(500);
    }
    
    /**
     * Executa a simulação de seu estado atual para um determinado número de etapas.
     * Pare antes de um determinado número de etapas se deixar de ser viável.
     */
    public void simulate(int numSteps){
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Executa a simulação de seu estado atual para uma única etapa.
     * Repita em todo o campo atualizando o estado de cada
     * raposa e coelho.
     */
    public void simulateOneStep()
    {
        step++;
        newAnimals.clear();
        
        // deixa todos os animais agirem

        for(Iterator iter = animals.iterator(); iter.hasNext(); ) {
            Animals animal = (Animals)iter.next();
            if(animal.isAlive()){
                animal.act(field, updateField, newAnimals);
                
            }else{
                  iter.remove();   // remove animais mortos da coleção
                
            }
        }
        // Adiciona animais recem-nascidos a lista
        animals.addAll(newAnimals);
        
        // Troca o campo e updatedField no final da etapa.
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // exibi o novo campo na tela
        view.showStatus(step, field);
    }
        
    /**
     * Redefine a simulação para uma posição inicial.
     */
    public void reset(){
        step = 0;
        animals.clear();
        field.clear();
        updatedField.clear();
        populate(field);
        
        //Mostra o estado inicial na vista.
        view.showStatus(step, field);
    }
    
    /**
     * Povoa o campo com raposas e coelhos.
     */
    private void populate(Field field)
    {
        Random rand = new Random();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    animals.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    animals.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                }
                // caso contrário, deixe o local vazio.
            }
        }
        Collections.shuffle(animals);
    }
}

