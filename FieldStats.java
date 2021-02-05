package Raposas_e_Coelhos_simulacao;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;



public class FieldStats{
    // Contadores para cada tipo de entidade (fox, rabbit, etc.) na simulação.
    private HashMap counters;
    // Se os contadores estão atualizados atualmente.
    private boolean countsValid;

    /**
     * Constroe um objeto de estatisticas de campo.
     */
    public FieldStats(){
        // Configura uma coleção de contadores para cada tipo de animal
        // que encontramos.
        counters = new HashMap();
        countsValid = true;
    }

    /**
     * @return Uma string que descreve quais animais estão no campo.
     */
    public String getPopulationDetails(Field field){
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Invalida as atuais estatisticas reinicia tudo.
     * zera os contadores.
     */
    public void reset(){
        countsValid = false;
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter cnt = (Counter) counters.get(keys.next());
            cnt.reset();
        }
    }

    /**
     * Incrementa a contagem de uma classe de animal.
     */
    public void incrementCount(Class animalClass){
        Counter cnt = (Counter) counters.get(animalClass);
        if(cnt == null) {
            // não temos um contador para esta espécie ainda - crie um
            cnt = new Counter(animalClass.getName());
            counters.put(animalClass, cnt);
        }
        cnt.increment();
    }

    /**
     * Indica que a contagem de aniamais foi concluida.
     */
    public void countFinished(){
        countsValid = true;
    }

    /**
     * Determina se a simulação ainda é viável.
     * Ou seja, deve continuar em execução.
     * @return true se houver mais de uma espécie viva.
     */
    public boolean isViable(Field field){
        // Quantas contagens são diferentes de zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Gera contagens do Numero de Fox e Rabbit.
     * Estes não sao atualizados como Fox e Rabbit.
     * São colocados no campo, mas apenas quando um pedido
     * é feito para a informação.
     */
    private void generateCounts(Field field){
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
}

