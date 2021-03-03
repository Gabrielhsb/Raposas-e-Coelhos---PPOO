

package Raposas_e_Coelhos_simulacao;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PopulationGenerator{
    
    // A probabilidade de uma raposa ser criada em qualquer posição da grade.
    private static final double FOX_CREATION_PROBABILITY = 0.03;
    // A probabilidade de um coelho ser criado em qualquer posição da grade.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;  
    private static final double OWL_CREATION_PROBABILITY = 0.04;    
    private static final double BURN_CREATION_PROBABILITY = 0.02;    

    // Listas de animais no campo. Listas separadas são mantidas para facilitar a iteração.
    private List<Actor> actors;

    // O estado atual do campo.
    private Field field;  
    
    
    // Uma visão gráfica da simulação
    private SimulatorView view;

    /**
     * Contrutor padrão
     * @param field
     * @param view 
     */
    public PopulationGenerator(Field field, SimulatorView view){
        actors = new ArrayList<>();
       this.field = field;
       this.view = view;
       
        setColor();
        
    }
     /**
      *
      * @return A lista de atores
      */
     public List<Actor> getAnimalsList(){
        return this.actors;
    }
    
    /**
     * Retorna o número total de raposas e coelhos na lista.
     * @return O número total de raposas e coelhos na lista.
     */
    public int getNumberOfAnimals(){
       int i = actors.size();
       return i;
    }   
    
    /**
     * Retorna todos os animais no campo
     * @return a quantidade de animais no campo
     */
     public int getAllAnimalsOnField(){
        ArrayList<Object> allAnimals;
        allAnimals = new ArrayList<>();
        
        
        for(int y = 0; y < getField().getDepth(); y++)
        {
            for(int x = 0; x < getField().getWidth(); x++)
            {
                Object obj = getField().getObjectAt(y,x);
                
             if(obj instanceof Rabbit || obj instanceof Fox || obj instanceof Owl) {
                 allAnimals.add(obj);
             }
  
            }
        }
        return allAnimals.size();
    }
    
     
 
        /**
      * Retorna o campo.
      *
      * @return o campo
      */
     
    private Field getField(){
        return this.field;
    }

    /**
     * Preencher o campo aleatoriamente com os atores
     */
    public void populate(){
        Random rand;
        rand = new Random();
        getField().clear();
        for(int row = 0; row < getField().getDepth(); row++) {
            for(int col = 0; col < getField().getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, getField(), location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, getField(), location);
                    actors.add(rabbit);
                }else if (rand.nextDouble() <= OWL_CREATION_PROBABILITY) {
                     Location location = new Location(row, col);
                     Owl owl = new Owl(true, getField(), location);
                     actors.add(owl);
                }else if (rand.nextDouble() <= BURN_CREATION_PROBABILITY) {
                     Location location = new Location(row, col);
                     Burn burn = new Burn(getField(), location);
                     actors.add(burn);
                }
                // senão deixe o local vazio.
            }
        }
    }
    
        /**
         * Define as cores dos Atores:
         * Coelhos são laranja,
         * Raposas são azuis,
         * Queimadas são vermelhas.
         */
        private void setColor(){
        this.view.setColor(Rabbit.class, Color.ORANGE);
        this.view.setColor(Fox.class, Color.BLUE);
        this.view.setColor(Owl.class, Color.DARK_GRAY);
         this.view.setColor(Burn.class, Color.RED);

        
        
    }
    
}