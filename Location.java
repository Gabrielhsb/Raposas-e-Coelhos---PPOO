package Raposas_e_Coelhos_simulacao;

public class Location{
    //Posições de linha e coluna.
    private int row;
    private int col;

    /**
     * Representa uma linha e coluna.
     * @param row A linha.
     * @param col A coluna.
     */
    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    /**
     * Implementa igualdade de conteúdo.
     */
    public boolean equals(Object obj){
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }
    
    /**
     * Retorna uma string com a linha e a coluna.
     * @return Uma representação da string do local
     */
    public String toString(){
        return row + "," + col;
    }
    
    /**
     * Usa os 16bits superiores para o valor da linha e os inferiores
     * para a coluna. Exceto para grades muito grandes, isso deve dar
     *um codigo hash exclusivo para cada par (linha, coluna).
     */
    public int hashCode(){
        return (row << 16) + col;
    }
    
    public int getRow(){
        return row;
    }
    

    public int getCol(){
        return col;
    } 
}
