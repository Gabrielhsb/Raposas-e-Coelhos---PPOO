
package Raposas_e_Coelhos_simulacao;
import java.util.Iterator;
import java.util.List;

/**
 *Classe que represeta as queimadas.
 * @author Gabriel
 */
public class Burn extends Actor{
        private int lifeTime; //Tempo que a queimada vai durar.
        private static final int MAX_TIME = 100; //Tempo maximo que dura a queimada.
      

    /**
     * Construtor padrão da queimada.
     * @param field Campo onde a queimada vai ficar.
     * @param location localização da queimada.
     */
    public Burn(Field field,Location location) {
        super(location, field);
        this.lifeTime = 0;
    }
    /**
     * Forma como a queimada age, ela queima tudo oque ela tem contato
     * ocasionado a morte do mesmo.
     * @param newBurn Lista de queimadas.
     */
    @Override
    public void act(List<Actor> newBurn) {
        incrementTime();
        if(isActive()){
            Location newLocation = findLives(); 
            if(newLocation != null){
                Burn burn = new Burn(field, newLocation);
                newBurn.add(burn);
            }
        }
    }
        
    /**
     * Incremeta o tempo de duração da queimada.
     */
        private void incrementTime(){
            lifeTime++;
            if(lifeTime > MAX_TIME) {
                setEnd();
            }
    }
        /**
         * Busca formas de vidas proximas para "alimentar" a queimada,
         * se econtrar o fogo propaga.
         * @return um animal ou plata se encontrar ou null senão encontrar.
         */
        
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
                        }else if(life instanceof FoxBane){
                           FoxBane fb = (FoxBane) life;
                           if(fb.isActive()){
                               fb.setDead();
                               result = where;
                           }
                        }else {
                            result = null;
                        }
                }
        }
         return result;
    }
    
    /**
     * Ocasiona o fim da queimada,unica diferença para 
     * o setDead() é que ele seta o local que teve quiemada
     * como "Burned" assim não nasce planta lá mais.
     */
     public void setEnd(){
        setActive(false);
        if(getLocation() != null){
            Field f = getField();
            f.clear(getLocation());
            f.setBurned(getLocation());  
            
        }
    }
}
