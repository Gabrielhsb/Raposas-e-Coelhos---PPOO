
package Raposas_e_Coelhos_simulacao;

import Raposas_e_Coelhos_simulacao.Field;
import Raposas_e_Coelhos_simulacao.Location;
import java.util.List;


/**
 * A superclasse utilizada para representar um animal
 * @author Gabriel
 */
public abstract class Actor{
    private boolean active;
    private Location location;
    protected Field field;

    
    /**
     * Contrutor padrão utilizado para inicializar os atributos.
     * @param location localização onde o animal vai aparecer
     * @param field ?
     */
    public Actor(Location location, Field field) {
        this.active = true;
        this.field = field;
        setLocation(location);
    }

    /** 
     * Utilizado para ver se o animal está vivo.
     * @return retorna true ou false.
     */
    public boolean isActive() {
        return this.active;
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
        active = false;
        if(location != null){
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Forma como cada animal vai agir.
     * @param newActors ?
     */
     abstract public void act(List<Actor> newActors);

}
