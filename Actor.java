
package Raposas_e_Coelhos_simulacao;
import java.util.List;


/**
 * A superclasse utilizada para representar um ator na simulação.
 * @author Gabriel
 */
public abstract class Actor{
    private boolean active;
    private Location location;
    protected Field field;

    /**
     * Contrutor padrão utilizado para inicializar os atributos.
     * @param location localização onde está o ator.
     * @param field campo que o ator ocupa. 
     */
    public Actor(Location location, Field field) {
        this.active = true;
        this.field = field;
        setLocation(location);
    }

    /** 
     * Utilizado para ver se o ator está vivo.
     * @return retorna true ou false.
     */
    public boolean isActive() {
        return this.active;
    }

    /** 
     * Utilizado para pegar a localização do ator.
     * @return retorna a localição.
     */
    public Location getLocation() {
        return this.location;
    }
    
    /**
     * Definine uma nova localização para o ator
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
     * Define a localização do ator.
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
     * Seta se o ator está ativo ou não.
     * @param active true ou false
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * 
     * @return O field do ator.
     */
    public Field getField() {
        return field;
    }
    
    /**
     * Forma como cada ator vai agir.
     * @param newActors lista onde guardara os atores.
     */
     abstract public void act(List<Actor> newActors);

}
