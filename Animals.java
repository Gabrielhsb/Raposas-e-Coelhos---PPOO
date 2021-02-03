
package Raposas_e_Coelhos_simulacao;

//Teste
//testeL

public class Animals {

    private int age;
    private boolean alive;
    private Location location;

    public Animals(int age, boolean alive) {
        this.age = age;
        this.alive = alive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
       /**
     * Defina a localização do animal.
     * @param row A coordenada vertical do local.
     * @param col The horizontal coordinate of the location.
     */
    
    public void setLocation(int row, int col){
        this.location = new Location(row, col);
    }


   
}
