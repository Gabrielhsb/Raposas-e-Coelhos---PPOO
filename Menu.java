package Raposas_e_Coelhos_simulacao;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;

/**
 *Classe que gera um Menu com botões para controlar a execução.
 * @author Gabriel
 */
public class Menu {
    private JFrame frame;
    private JButton nextStep;
    private JButton start;
    private Simulator simulator =  new Simulator();

    
    public Menu() {
        simulator.reset();
        frame = new JFrame("Menu");
        nextStep = new JButton("NextStep");
        start = new JButton("Start");
        createFrame();
        
        
        start.addActionListener(new ActionListener(){
           
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int i = 0; i < 50; i++) {
                     simulator.simulateOneStep();
                }
            }
        });
        
                
       nextStep.addActionListener(new ActionListener(){
           
            @Override
            public void actionPerformed(ActionEvent ae) {
              simulator.simulateOneStep();
            }
        });
    }
    
    private void createFrame(){
        frame.setSize(250,80);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setLayout(new FlowLayout());
         frame.add(start);
        frame.add(nextStep);
        
    }
    
    public void show(){
        frame.setVisible(true);
    }
}
