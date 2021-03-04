package Raposas_e_Coelhos_simulacao;

public class Location{
    // Posições de linha e coluna.
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
     * Implementar igualdade de conteúdo.
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
     * Retorna uma string da linha, coluna do formulário
     * @return Uma representação de string do local.
     */
    public String toString(){
        return row + "," + col;
    }
    
    /**
     * Use os 16 bits superiores para o valor da linha e os inferiores para
     * a coluna. Exceto para grades muito grandes, isso deve dar uma
     * código hash exclusivo para cada par (linha, coluna).
     * @return Um código hash para o local.
     */
    
    public int hashCode(){
        return (row << 16) + col;
    }
    
    /**
     * @return A linha
     */
    public int getRow(){
        return row;
    }
    
    /**
     * @return A coluna
     */
    public int getCol(){
        return col;
    }



}
