package Raposas_e_Coelhos_simulacao;

/**
 *  Trabalho final de Práticas de Programação Orientada a Objetos
 * 
 * @author Gabriel Henrique Silva Bernardo
 * @author Lucas Floriano Bernardes de Castro
 * @author Kleber Halley Sallum Guimarães
 * @author Paulo Eduardo Tomaz de Biaso 
 */

 
public class Application{
private Simulator simulator;
    
    public Application()
    {
        simulator = new Simulator();
        simulator.reset();
        test();
        
    }
    
    private void test()
    {
        simulator.simulate(6);
        //simulator.runLongSimulation();
//        simulator.simulateOneStep();
//        simulator.simulateOneStep();
//        simulator.simulateOneStep();
//        simulator.simulateOneStep();
//        simulator.simulateOneStep();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Application app = new Application();
    }
    
}