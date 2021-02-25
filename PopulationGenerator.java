

package Raposas_e_Coelhos_simulacao;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PopulationGenerator{
    
    // A probabilidade de uma raposa ser criada em qualquer posição da grade.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // A probabilidade de um coelho ser criado em qualquer posição da grade.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    

    // Listas de animais no campo. Listas separadas são mantidas para facilitar a iteração.
    private List<Animal> animales;

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
        animales = new ArrayList<>();
       this.field = field;
       this.view = view;
       
        setColor();
        
    }
     /**
      *
      * @return A lista de animais
      */
     public List<Animal> getAnimalsList(){
        return this.animales;
    }
    
    /**
     * Retorna o número total de raposas e coelhos na lista.
     * @return O número total de raposas e coelhos na lista.
     */
    public int getNumberOfAnimals(){
       int i = animales.size();
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
                
             if(obj instanceof Rabbit || obj instanceof Fox) 
             {
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
     * Preencher o campo aleatoriamente com raposas e coelhos.
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
                    animales.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, getField(), location);
                    animales.add(rabbit);
                }
                // senão deixe o local vazio.
            }
        }
    }
    
        /**
         * Define as cores dos animais
         * Coelhos são laranja
         * Raposas são azuis
         */
        private void setColor(){
        this.view.setColor(Rabbit.class, Color.ORANGE);
        this.view.setColor(Fox.class, Color.BLUE);
        
    }
    
}