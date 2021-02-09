
package Raposas_e_Coelhos_simulacao;


public abstract class Animal{

    private int age;
    private boolean alive;
    private Location location;

    public Animal(int age, boolean alive) {
        this.age = age;
        this.alive = alive;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {//A idade é mutavel? Pois ja é setado no construtor.
        this.age = age;          //caso negativo funcão desnecessaria
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        setAlive(false);
    }
    
    //Forma como cada animal vai agir
     abstract public void act(Field currentField, Field updatedField, List newAnimals);

    //idade que o animal começa a procriar
     abstract public int getBreedingAge();
}
