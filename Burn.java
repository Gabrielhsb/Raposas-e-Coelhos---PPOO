/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Raposas_e_Coelhos_simulacao;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Gabriel
 */
public class Burn extends Actor{
        private int lifeTime;
        private static final int MAX_TIME = 2; //Tempo maximo que dura a queimada
        private static final Random rand = new Random(); // Gerador de numeros aleatorios.

        
    public Burn(Field field,Location location) {
        super(location, field);
        this.lifeTime = rand.nextInt(MAX_TIME);
    }
    
    @Override
    public void act(List<Actor> newBurn) {
        incrementTime();
        if(isActive()){
            System.out.println("ENTREI1");
            Location newLocation = findLives(); 
           
            //O fogo so propaga se queimar algo, animal ou planta
            if(newLocation != null){
                Burn burn = new Burn(field, newLocation);
                        newBurn.add(burn);
            }else {
                //Se não ele apaga
                setDead();
               
            }
        }
    }
    
    
        private void incrementTime(){
            lifeTime++;
            if(lifeTime > MAX_TIME) {
                setDead();
                
            }
    }
 
    private Location findLives(){
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location result = null;
        while(it.hasNext()){
            Location where = it.next();
            Object life = field.getObjectAt(where);
            if(life instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) life;
                     if(rabbit.isActive()) { 
                         rabbit.setDead();
                         result = where;
                     }else if(life instanceof Owl){
                            Owl owl = (Owl) life;
                            if(owl.isActive()){
                                owl.setDead();
                                result = where;
                            }
                        }else if(life instanceof Fox){
                           Fox fox = (Fox) life;
                           if(fox.isActive()){
                               fox.setDead();
                               result = where;
                           }
                        }else {
                            result = null;
                        }
                 }
        }
        return result;
    }
}
