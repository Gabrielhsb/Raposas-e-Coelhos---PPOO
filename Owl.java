
package Raposas_e_Coelhos_simulacao;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *Classe que represanta a coruja.
 * @author Gabriel 
 * @author Lucas Bernardes
 */

public class Owl extends Actor{
    private static final int BREEDING_AGE = 10; //Idade que coruja começa procriar.
    private static final int MAX_AGE = 200; // Idade maxima que a coruja vive.
    private static final double BREEDING_PROBABILITY = 0.30; // Probabilidade da coruja procriar.
    private static final int MAX_LITTER_SIZE = 3; // Numero maximo de filhotes.
    private static final int RABBIT_FOOD_VALUE = 15; //Numero de passos que a coruja pode dar antes de se alimentar novamente.
    private static final Random rand = new Random(); // Gerador de numeros aleatorios compartilhados para controlar a repodução
    private int foodLevel; // Nivel de comida, aumenta ao comer coelhos ou frutinhas.
    private int age;
    
    /**
     * Ao criar uma coruja ela pode nascer com idade zero ou com uma idade aleatoria.
     * @param randomAge Se verdadeiro, a coruja terá idade e nível de fome aleatórios.
     * @param field campo onde a coruja vai ocupar.
     * @param location localização no campo onde a coruja vai ficar.
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
    /**
     * Forma como a coruja se comporta,
     * Normalmente ela procura comida seja coelho ou plantas
     * gera novas corujas.
     * @param newOwl 
     */
    @Override
    public void act(List<Actor> newOwl) {
        incrementAge();
        incrementeHunger();
        if(isActive()){
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
    /**
     * Incrementa a idade da coruja.
     */
    private void incrementAge() {
            age++;
            if(age > MAX_AGE) {
                setDead();
            }
    }
    /**
     * Incrementa a fome da coruja.
     */
    private void incrementeHunger() {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    /**
     * Gera novos filhotes de corujas.
     * @param newOwl lista de corujas.
     */
    private void giveBirth(List<Actor> newOwl) {
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Owl young = new Owl(false, field, loc);
            newOwl.add(young);
        }
    }
    
    /**
     * Responsavel por fazer a coruja buscar comida,
     * sendo coelho ou planta.
     * @return  o alimento encontrado ou null se não tiver.
     */
    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location result = null;
        
        while(it.hasNext()){
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit){
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isActive()){
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    result = where;
                }
            } else if(animal instanceof FoxBane){
                FoxBane foxBane = (FoxBane) animal;
                if(foxBane.isActive()){
                    foxBane.setDead();
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
    
    /**
     * Responsavel gerar um numero aleatorio de quantos filhos
     * a coruja vai ter.
     * @return numero de filhos.
     */
    private int breed(){
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY){
            births = rand.nextInt(MAX_LITTER_SIZE)+1;
        }
        return births;
    }

    /**
     * Verifica se a coruja pode procriar.
     * @return true ou false.
     */
    private boolean canBreed() {
        return age >= BREEDING_AGE;    
    }
    
    
    
}
