package Raposas_e_Coelhos_simulacao;

import java.util.List;
import java.util.Random;


/**
 * 
 * @author Gabriel
 */
public class Rabbit extends Animal{

    private static final int BREEDING_AGE = 5; //Idade que coelho começa procriar
    private static final int MAX_AGE = 50;// Idade maxima que a coelho vive
    private static final double BREEDING_PROBABILITY = 0.15;// Probabilidade do coelho procriar
    private static final int MAX_LITTER_SIZE = 5;// Numero maximo de filhotes
    private static final Random rand = new Random();// Gerador de numeros aleatorios compartilhados para controlar a repodução
    private int age;
    
    
    /**
     * Ao criar uma coelho ela pode nascer com idade zero ou com uma idade aleatoria.
     * @param randomAge Se verdadeiro, a coelho terá idade e nível de fome aleatórios.
     */
    public Rabbit(boolean randomAge, Field field, Location location){
        super(location, field);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    } 
    /** 
     * Função responsavel pela forma em que o coelho se comporta.
     * No processo, ele pode se reproduzir, morrer de fome,
     * ou morrer de velhice.
     * @param newRabbits lista de coelhos
     */
    public void act(List<Animal> newRabbits){
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Mova-se em direção a uma fonte de alimento, se encontrada.
            Location newLocation = field.freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Superlotação.
                setDead();
            }
        }
    }
    /**
     * Aumenta a idade da coelho, se chegar na idade maxima ela morre de velhice.
     */
      private void incrementAge(){
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    /**
     * Gere um número que representa o número de nascimentos.
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
     * Responsavel pela geração de novos coelhos
     * @param newRabbits 
     */
     private void giveBirth(List<Animal> newRabbits){
         // Novas coelhos nascem em locais adjacentes.
        // Obtenha uma lista de locais livres adjacentes.
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }
    /**
     * @return A idade que o coelho começa a procriar 
     */
    private boolean canBreed(){
        return age >= BREEDING_AGE;
    }
    
}
