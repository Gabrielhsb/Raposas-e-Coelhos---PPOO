package Raposas_e_Coelhos_simulacao;

import java.util.List;
import java.util.Random;


public class Rabbit extends Animals{
    // Estatisticas dos coelhos

    private static final int BREEDING_AGE = 5; //Idade que coelho começa procriar
    private static final int MAX_AGE = 50;// Idade maxima que a coelho vive
    private static final double BREEDING_PROBABILITY = 0.15;// Probabilidade do coelho procriar
    private static final int MAX_LITTER_SIZE = 5;// Numero maximo de filhotes
    private static final Random rand = new Random();// Gerador de numeros aleatorios compartilhados para controlar a repodução

    /**
     * Ao criar uma raposa ela pode nascer com idade zero ou com uma idade aleatoria.
     * 
     * @param randomAge Se verdadeiro, a raposa terá idade e nível de fome aleatórios.
     */

    public Rabbit(boolean randomAge){
        super(0, true);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    } 
    
    public void run(Field updatedField, List newRabbits){
        incrementAge();
        if(isAlive()) {
            int births = breed();
            for(int b = 0; b < births; b++) {
                Rabbit newRabbit = new Rabbit(false);
                newRabbits.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(getLocation());
                newRabbit.setLocation(loc);
                updatedField.place(newRabbit, loc);
            }
            Location newLocation = updatedField.freeAdjacentLocation(getLocation());
            // Apenas transfira para o campo atualizado se houver um local livre
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                // não pode se mover nem ficar - superlotação - todos os locais tomados
                setAlive(false);
            }
        }
    }
    
      private void incrementAge(){
        setAge(getAge()+1);
        if(getAge() > MAX_AGE) {
           setAlive(false);
        }
    }
    /**
     * Gere um número que representa o número de nascimentos,
     * se pode procriar.
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
    * Um coelho pode procriar se tiver atingido a idade reprodutiva.
    */

    private boolean canBreed(){
        return getAge() >= BREEDING_AGE;
    }
    
    public void setEaten(){
        setAlive(false);
    }
    
}
