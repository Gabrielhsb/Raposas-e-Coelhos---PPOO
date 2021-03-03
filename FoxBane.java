/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Raposas_e_Coelhos_simulacao;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Gabriel
 */
public class FoxBane extends Actor{
    
        private static final int MAX_AGE = 25;
        private static final double BREEDING_PROBABILITY = 0.1; // Probabilidade de nascer novas frutinhas;
        private static final int MAX_BRANCHES_SIZE = 1; // Numero maximo de ramos.
        private static final Random rand = new Random();
        private int age;

    
    
    
    
    public FoxBane(boolean randomAge, Field field, Location location) {
        super(location, field);
        age = 0;
        if(randomAge){
            age = rand.nextInt(MAX_AGE);
        }
    }

    @Override
    public void act(List<Actor> newBane) {
        incrementAge();
        if (isActive()) {
            giveSeed(newBane);
        }
    }

    private void incrementAge() {
       age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    private void giveSeed(List<Actor> newBane) {
        List<Location> free = field.getFreeAndNotBurned(getLocation());
        int branches = branches();
        for(int i = 0; i < branches && free.size() > 0; i++) {
            Location loc = free.remove(0);
            FoxBane seed = new FoxBane(false, field, loc);
            newBane.add(seed);
        }
    }

    
    private int branches() {
        int branches = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            branches = rand.nextInt(MAX_BRANCHES_SIZE) + 1;
        }
        return branches;   
    }


    

}
