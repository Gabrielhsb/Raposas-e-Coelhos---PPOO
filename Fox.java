package Raposas_e_Coelhos_simulacao;

import java.util.List;
import java.util.Iterator;
import java.util.Random;


public class Fox extends Animals{
    // Estatisticas das raposas
    private static final int BREEDING_AGE = 10; //Idade que raposa começa procriar
    private static final int MAX_AGE = 150; // Idade maxima que a raposa vive
    private static final double BREEDING_PROBABILITY = 0.09; // Probabilidade da raposa procriar
    private static final int MAX_LITTER_SIZE = 3; // Numero maximo de filhotes
    private static final int RABBIT_FOOD_VALUE = 4; //Numero de passos que a raposa pode dar antes de se alimentar novamente
    private static final Random rand = new Random(); // Gerador de numeros aleatorios compartilhados para controlar a repodução
    private int foodLevel; // Nivel de comida da raposa, aumenta ao comer coelhos

    /**
     * Ao criar uma raposa ela pode nascer com idade zero ou com uma idade aleatoria.
     * 
     * @param randomAge Se verdadeiro, a raposa terá idade e nível de fome aleatórios.
     */


    public Fox(boolean randomAge){
       super(0, true);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            foodLevel = RABBIT_FOOD_VALUE;
        } 
    }
    
    /**
     * Isso é o que a raposa faz na maioria das vezes: ela caça
     * coelhos. No processo, ele pode se reproduzir, morrer de fome,
     * ou morrer de velhice.
     */
    public void act(Field currentField, Field updatedField, List newFoxes){
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            // Novas raposas nascem em locais adjacentes.
            int births = breed();
            for(int b = 0; b < births; b++) {
                Fox newFox = new Fox(false);
                newFoxes.add(newFox);
                Location loc = updatedField.randomAdjacentLocation(getLocation());
                newFox.setLocation(loc);
                updatedField.place(newFox, loc);
            }
            // Mova-se em direção à fonte de alimento, se encontrada
            Location newLocation = findFood(currentField, getLocation());
            if(newLocation == null) {  // nenhum alimento encontrado - mova-se aleatoriamente
                newLocation = updatedField.freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                // não pode se mover nem ficar - superlotação - todos os locais tomados
                setDead();
            }
        }
    }
    
 
    private void incrementHunger(){
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Diga à raposa para procurar coelhos adjacentes à sua localização atual
     * @param field O campo em que deve procurar.
     * @param location Onde está localizado no campo.
     * @return Onde a comida foi encontrada, ou null se não for.
     */

    private Location findFood(Field field, Location location){
        Iterator adjacentLocations =
                          field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setEaten();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
        
     private void incrementAge(){
        setAge(getAge()+1);
        if(getAge() > MAX_AGE) {
            setDead();
        }
    }
    /**
     * Gere um número que representa o número de nascimentos,se pode procriar.
     * @return O número de nascimentos (pode ser zero).
     */

    private int breed(){
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * Uma raposa pode procriar se atingiu a idade reprodutiva.
     */
    private boolean canBreed(){
        return getAge() >= BREEDING_AGE;
    }
  
}
