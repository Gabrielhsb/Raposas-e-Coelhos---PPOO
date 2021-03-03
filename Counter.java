package Raposas_e_Coelhos_simulacao;
import java.awt.Color;

/**
 * Classe responsavel pela contagem dos animais na simulação
 * @author Gabriel
 */
public class Counter{
    
    // Um ​​nome para este tipo de participante da simulação.
    private String name;
    // Quantos desse tipo existem na simulação.
    private int count;

    /**
     * Forneça um nome para um dos tipos de simulação.
     * @param name  Um nome, por exemplo "Raposa".
     */
    public Counter(String name){
        this.name = name;
        count = 0;
    }
    
    /**
     * @return retorna o nome 
     */
    public String getName(){
        return name;
    }

    /**
     * @return A contagem atual para este tipo.
     */
    public int getCount(){
        return count;
    }

    /**
     * Aumente a contagem atual em um.
     */
    public void increment(){
        count++;
    }
    
    /**
     * Redefina a contagem atual para zero.
     */
    public void reset(){
        count = 0;
    }
}

