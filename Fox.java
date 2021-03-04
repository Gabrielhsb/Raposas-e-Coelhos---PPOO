package Raposas_e_Coelhos_simulacao;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Subclasse de actor representa uma raposa.
 * @author Gabriel
 */
public class Fox extends Actor{
    // Estatisticas das raposas
    private static final int BREEDING_AGE = 7; //Idade que raposa começa procriar
    private static final int MAX_AGE = 150; // Idade maxima que a raposa vive
    private static final double BREEDING_PROBABILITY = 0.30; // Probabilidade da raposa procriar
    private static final int MAX_LITTER_SIZE = 2; // Numero maximo de filhotes
    private static final int FOOD_VALUE = 20; //Numero de passos que a raposa pode dar antes de se alimentar novamente
    private static final Random rand = new Random(); // Gerador de numeros aleatorios compartilhados para controlar a repodução
    private int foodLevel; // Nivel de comida da raposa, aumenta ao comer coelhos
    private int age;
    private boolean bane = false;
    
    /**
     * Ao criar uma raposa ela pode nascer com idade zero ou com uma idade aleatoria.
     * @param randomAge Se verdadeiro, a raposa terá idade e nível de fome aleatórios.
     * @param field campo em que a raposa vai ocupar.
     * @param location localização da planta no campo.
     */
    public Fox(boolean randomAge, Field field, Location location){
       super(location,field);
       age = 0;
       if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(FOOD_VALUE);
        }
    }
    /**
     * Função responsavel pela forma em que a raposa se comporta,
     * na maioria das vezes: ela caça coelhos. 
     * No processo, ele pode se reproduzir, morrer de fome,
     * ou morrer de velhice.
     * @param newFoxes lista de raposas
     */
    
    public void act(List<Actor> newFoxes){
        incrementAge();
        incrementHunger();
        if(isActive()) {
            giveBirth(newFoxes);            
            // Mova-se em direção a uma fonte de alimento, se encontrada.
            Location newLocation = findFood();
            if(newLocation == null) { 
                if(bane){
                    setDead();
                }else{
                   // Nenhum alimento foi encontrado - tente ir para um local livre.
                newLocation = field.freeAdjacentLocation(getLocation());
                }
            }
            // Ve se da para se mexer
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
     * Aumenta o nivel de fome da raposa, caso chegue a zero ela morre de fome
     */
    private void incrementHunger(){
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
 
    /**
     * Diz à raposa para procurar coelhos adjacentes à sua localização atual
     * @return Onde a comida foi encontrada, ou null se não for.
     */
        private Location findFood(){
            List<Location> adjacent = field.adjacentLocations(getLocation());
             Iterator<Location> it = adjacent.iterator();
             Location result = null;
             while(it.hasNext()) {
                 Location where = it.next();
                 Object actor = field.getObjectAt(where);
                 if(actor instanceof Rabbit) {
                     Rabbit rabbit = (Rabbit) actor;
                     if(rabbit.isActive()) { 
                         rabbit.setDead();
                         foodLevel = FOOD_VALUE;
                         result = where;
                     }
                 }else if(actor instanceof Owl){
                     Owl owl = (Owl) actor;
                     if(owl.isActive()){
                         owl.setDead();
                         foodLevel = FOOD_VALUE;
                         result = where;
                     }
                 }else if(actor instanceof FoxBane){
                     FoxBane fb = (FoxBane) actor;
                     if(fb.isActive()){
                         fb.setDead();
                         result = null;
                         bane = true;
                         
                     }
                 }
                 else {
                     result = null;
                 }
             }
             return result;
    }
        /**
         * Aumenta a idade da raposa, se chegar na idade maxima ela morre de velhice.
         */
        private void incrementAge(){
            age++;
            if(age > MAX_AGE) {
                setDead();
            }
    }

    /**
     * Gere um número que representa o número de filhotes.
     * @return O número de filhotes (pode ser zero).
     */
    private int breed(){
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * Responsavel pela geração de novas raposas
     * @param newFoxes lista de raposas 
     */
    private void giveBirth(List<Actor> newFoxes) {
         // Novas raposas nascem em locais adjacentes.
        // Obtenha uma lista de locais livres adjacentes.
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fox young = new Fox(false, field, loc);
            newFoxes.add(young);
        }
    }
    
    /**
     * @return A idade que a raposa começa a procriar 
     */
     private boolean canBreed(){
        return age >= BREEDING_AGE;
    }
  

}
