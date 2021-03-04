package Raposas_e_Coelhos_simulacao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Classe que representa um campo delimitado bidimensional.
 * O campo é composto por um número fixo de locais, que são
 * organizados em linhas e colunas.
 * @author gabriel
 * @author Lucas Bernardes
 */
public class Field{
    private static final Random rand = new Random();
    
    //A profundidade e largura do campo.
    private int depth, width;
    // Armazenamento para os animais.
    private Object[][] field;


    /**
     * Representa um campo das dimensões fornecidas.
     * @param depth A profundidade do campo.
     * @param width A largura do campo.
     */
    public Field(int depth, int width){
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
    }
    
    /**
     * Função responsavel por esvaziar o campo.
     */
    public void clear(){
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Função responsavel por esvaziar uma localização do campo
     * @param location
     */
    public void clear(Location location){
        field[location.getRow()][location.getCol()] = null;
    }
    
/**
     * Coloque um animal no local determinado.
     * Se já houver um animal no local ele vai se perder.
     * @param animal O animal a ser colocado.
     * @param row Coordenada de linha do local.
     * @param col Coordenada da coluna do local.
     */
    
    public void place(Object animal, int row, int col){
        place(animal, new Location(row, col));
    }
    
    /**
     * Coloque um animal no local determinado.
     * Se já houver um animal no local ele vai se perder.
     * @param animal O animal a ser colocado.
     * @param location Onde colocar o animal.
     */
    public void place(Object animal, Location location) {
        field[location.getRow()][location.getCol()] = animal;
    }
    
    /**
     * Devolva o animal no local informado, se houver.
     * @param location Onde no campo.
     * @return O animal no local fornecido ou nulo se não houver nenhum.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Devolva o animal no local informado, se houver.
     * @param row A linha desejada.
     * @param col A coluna desejada.
     * @return O animal no local fornecido ou nulo se não houver nenhum.
     */
    
    public Object getObjectAt(int row, int col){
        return field[row][col];
    }
    
    /**
     * Gere um local aleatório adjacente ao
     * determinado local ou é o mesmo local.
     * O local retornado estará dentro dos limites válidos
     * do campo.
     * @param location O local a partir do qual gerar uma adjacência.
     * @return Um local válido dentro da área da grade. Esta
     * pode ser o mesmo objeto que o parâmetro de localização.
     */
    public Location randomAdjacentLocation(Location location){
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    /**
     * Tente encontrar um local livre adjacente ao
     * determinado local. Se não houver nenhum, retorne a atual
     * localização, se estiver livre. Caso contrário, retorna nulo.
     * O local retornado estará dentro dos limites válidos
     * do campo.
     * @param location O local a partir do qual gerar uma adjacência.
     * @return Um local válido dentro da área da grade. Este pode ser o
     * mesmo objeto que o parâmetro de localização, ou nulo se todos
     * as localizações ao redor estão cheias.
     */
    public List<Location> getFreeAdjacentLocations(Location location){
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null || getObjectAt(next) instanceof Burned ) {
                free.add(next);
            }
        }
        return free;
    }
    
   public List<Location> getFreeAndNotBurned(Location location){
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
                if(getObjectAt(next) == null){
                     free.add(next);
                }
            
        }
        return free;
    }

    public Location freeAdjacentLocation(Location location){
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }
    
    
    /**
     * Gere um iterador sobre uma lista embaralhada de locais adjacentes
     * para o dado. A lista não incluirá o local em si.
     * Todos os locais ficarão dentro da grade.
     * @param location O local a partir do qual gerar adjacências.
     * @return Um iterador sobre locais adjacentes àquele fornecido.
     */
    public List<Location> adjacentLocations(Location location){
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclui locais inválidos e o local original.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            // Misture a lista. Vários outros métodos dependem da lista estando em uma ordem aleatória.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }
    
    
    /**
     * @return A profundidade do campo.
     */
    
    public int getDepth(){
        return depth;
    }
    
    /**
     * @return A largura do campo.
     */
    public int getWidth(){
        return width;
    }

/**
 * Demarca que aquele local está queimado.
 * @param location localização para demarcar.
 */
  public void setBurned(Location location){
        Burned burned = new Burned();
       place(burned, location);
    }

    
    
}