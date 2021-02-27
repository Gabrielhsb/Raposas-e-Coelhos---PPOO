
package Raposas_e_Coelhos_simulacao;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Gabriel 
 */
public class Owl extends Animal{
    
    
    private static final int BREEDING_AGE = 7; //I dade que coruja começa procriar.
    private static final int MAX_AGE = 200; // Idade maxima que a coruja vive.
    private static final double BREEDING_PROBABILITY = 0.04; // Probabilidade da coruja procriar.
    private static final int MAX_LITTER_SIZE = 5; // Numero maximo de filhotes.
    private static final int RABBIT_FOOD_VALUE = 3; //Numero de passos que a coruja pode dar antes de se alimentar novamente.
    private static final Random rand = new Random(); // Gerador de numeros aleatorios compartilhados para controlar a repodução
    private int foodLevel; // Nivel de comida do coelho, aumenta ao comer coelhos.
    private int age;
    
    
    
    
    /**
     * Ao criar uma coruja ela pode nascer com idade zero ou com uma idade aleatoria.
     * @param randomAge Se verdadeiro, a coruja terá idade e nível de fome aleatórios.
     */
    
    public Owl(boolean randomAge, Field field, Location location) {
        super(location, field);
        age =0;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
            
        }
        else {
            age = 0;
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
    }

    @Override
    public void act(List<Animal> newOwl) {
        incrementAge();
        incrementeHunger();
        if(isAlive()){
            giveBirth(newOwl);
            
            Location newLocation = findFood();
            if(newLocation== null){
                newLocation = field.freeAdjacentLocation(getLocation());
            }
            if(newLocation != null){
                setLocation(newLocation);
            }
            else{
                setDead();
            }
        }
        
    }

    private void incrementAge() {
            age++;
            if(age > MAX_AGE) {
                setDead();
            }
    }

    private void incrementeHunger() {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    private void giveBirth(List<Animal> newOwl) {
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Owl young = new Owl(false, field, loc);
            newOwl.add(young);
        }
    }

    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location result = null;
        
        while(it.hasNext()){
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit){
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()){
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    result = where;
                }
            }
            else{
                result = null;
            }
            
        }
        return result;
    }
    
    
    private int breed(){
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY){
            births = rand.nextInt(MAX_LITTER_SIZE)+1;
        }
        return births;
    }

    private boolean canBreed() {
        return age >= BREEDING_AGE;    
    }
    
    
    
}
