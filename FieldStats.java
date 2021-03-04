package Raposas_e_Coelhos_simulacao;
import java.util.HashMap;


/**
* Esta classe coleta e fornece alguns dados estatísticos sobre o estado
 * de um campo. É flexível: criará e manterá um contador
 * para qualquer classe de objeto encontrada no campo.
 *
 * @author Gabriel
 */
public class FieldStats{
    // Contadores para cada tipo de entidade (raposa, coelho, etc.) na simulação.
    private HashMap<Class, Counter> counters;
    // Se os contadores estão atualizados atualmente.
    private boolean countsValid;

    /**
     * Constroi um objeto FieldStats.
     */
    public FieldStats()
    {
        // Set up a collection for counters for each type of animal that
        // we might find
        counters = new HashMap<Class, Counter>();
        countsValid = true;
    }

    /**
     * Obtenha detalhes do que está em campo.
     * @return Uma string que descreve o que está no campo.
     */
    public String getPopulationDetails(Field field){
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            Counter info = counters.get(key);
            String name = info.getName();
            String[] resultado = name.split("\\.");
            buffer.append(resultado[1]);
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     Invalide o conjunto atual de estatísticas; reiniciar tudo a zero.
     */
    public void reset(){
        countsValid = false;
        for(Class key : counters.keySet()) {
            Counter count = counters.get(key);
            count.reset();
        }
    }

    /**
     * Aumente a contagem para uma classe de animal.
     * @param animalClass A classe de animal a ser incrementada.
     */
    public void incrementCount(Class animalClass){
        Counter count = counters.get(animalClass);
        if(count == null) {
            // We do not have a counter for this species yet.
            // Create one.
            count = new Counter(animalClass.getName());
            counters.put(animalClass, count);
        }
        count.increment();
    }

    /**
     * Indique que uma contagem de animais foi concluída.
     */
    public void countFinished(){
        countsValid = true;
    }

    /**
     * Determine se a simulação ainda é viável.
     * Ou seja, deve continuar em execução.
     * @return true Se houver mais de uma espécie viva.
     */
    
    public boolean isViable(Field field){
        // How many counts are non-zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            Counter info = counters.get(key);
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Gera contagens do número de raposas e coelhos.
     * Estes não são atualizados quando raposas e coelhos
     * são colocados no campo, mas apenas quando é chamada.
     * @param field O campo para o qual gerar as estatísticas.
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