
package Raposas_e_Coelhos_simulacao;

import Raposas_e_Coelhos_simulacao.Field;
import Raposas_e_Coelhos_simulacao.Location;
import java.util.List;


/**
 * A superclasse utilizada para representar um animal
 * @author Gabriel
 */
public abstract class Animal{
    private boolean alive;
    private Location location;
    protected Field field;

    
    /**
     * Contrutor padrão utilizado para inicializar os atributos.
     * @param location localização onde o animal vai aparecer
     * @param field ?
     */
    public Animal(Location location, Field field) {
        this.alive = true;
        this.field = field;
        setLocation(location);
    }

    /** 
     * Utilizado para ver se o animal está vivo.
     * @return retorna true ou false.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /** 
     * Utilizado para pegar a localização do animal.
     * @return retorna a localição.
     */
    public Location getLocation() {
        return this.location;
    }
    
    /**
     * Definine uma nova localização para o animal
     * @param newlocation nova localização
     */
    public void setLocation(Location newlocation) {
        
        if (location != null){
            field.clear(location);
        }
        location = newlocation;
        field.place(this, newlocation);
    }
     
    /**
     * Define a localização do animal.
     * @param row A coordenada vertical do local (linha).
     * @param col A cordenada horizontal do local (coluna).
     */
    public void setLocation(int row, int col){
        this.location = new Location(row, col);
    } 

    public void setDead(){
        alive = false;
        if(location != null){
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Forma como cada animal vai agir.
     * @param newAnimales ?
     */
     abstract public void act(List<Animal> newAnimales);
     
     
     


}
