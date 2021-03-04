package Raposas_e_Coelhos_simulacao;
import java.util.List;
import java.util.Random;


/**
 *Classe que representa a FoxBane, uma platinha que envenena a
 * raposa quando ela come.
 * @author Gabriel
 */

public class FoxBane extends Actor{
        private static final int MAX_AGE = 25; //Idade maxima que a planta vive.
        private static final double BREEDING_PROBABILITY = 0.1; // Probabilidade de nascer novas platinhas;
        private static final int MAX_BRANCHES_SIZE = 1; // Numero maximo de ramos.
        private static final Random rand = new Random();
        private int age;
    
        
    /**
     * Construtor padrão da calsse.
     * @param randomAge caso sejá true gera uma idade Aleatória para a planta.
     * @param field campo em que a plata vai ocupar.
     * @param location localização da planta no campo.
     */
    public FoxBane(boolean randomAge, Field field, Location location) {
        super(location, field);
        age = 0;
        if(randomAge){
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * Forma como a plata se comporta,bastante simples
     * ela apenas gera novas platinhas sempre.
     * @param newBane lista de plantas
     */
    @Override
    public void act(List<Actor> newBane) {
        incrementAge();
        if (isActive()) {
            giveSeed(newBane);
        }
    }
    /**
     * Incrementa a idade da planta.
     */
    private void incrementAge() {
       age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    /**
     * Responsavel por criar novas platinhas.
     * @param newBane 
     */
    private void giveSeed(List<Actor> newBane) {
        List<Location> free = field.getFreeAndNotBurned(getLocation());
        int branches = branches();
        for(int i = 0; i < branches && free.size() > 0; i++) {
            Location loc = free.remove(0);
            FoxBane seed = new FoxBane(false, field, loc);
            newBane.add(seed);
        }
    }

    /**
     * Numero de ramos (filhos) que a platinha vai ter.
     * @return 
     */
    private int branches() {
        int branches = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            branches = rand.nextInt(MAX_BRANCHES_SIZE) + 1;
        }
        return branches;   
    }
}
