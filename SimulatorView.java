package Raposas_e_Coelhos_simulacao;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.FlowLayout;


public class SimulatorView extends JFrame{
    // Cores usadas para locais vazios.
    private static final Color EMPTY_COLOR = Color.white;

    // Cor usada para objetos que não têm cor definida.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    private JButton nextStep;
    // Um mapa para armazenar cores para os participantes da simulação
    private Map<Class, Color> colors;
    // Um objeto de estatística computando e armazenando informações de simulação
    private FieldStats stats;

      /**
      * Crie uma view da largura e altura fornecidas.
      * @param height A altura da simulação.
      * @param width A largura da simulação.
      */
    public SimulatorView(int height, int width){
        stats = new FieldStats();
        colors = new LinkedHashMap<Class, Color>();

        setTitle("Fox and Rabbit Simulation");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        nextStep = new JButton("NextStep");
        setLocation(100, 50);
        
        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.WEST);
        contents.add(population, BorderLayout.SOUTH);
        contents.add(nextStep,FlowLayout.LEFT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
        nextStep.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Simulator simulator = new Simulator();
                simulator.reset();
                simulator.simulateOneStep();
            }
        });
    }
    
  /**
      * Defina uma cor a ser usada para uma determinada classe de animal.
      * @param animalClass O objeto Classe do animal.
      * @param color A cor a ser usada para a classe dada.
      */
    
    public void setColor(Class animalClass, Color color){
        colors.put(animalClass, color);
    }

      /**
      * @return A cor a ser usada para uma determinada classe de animal.
      */
    
    private Color getColor(Class animalClass){
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

     /**
      * Mostra o status atual do campo.
      * @param step Qual etapa de iteração é.
      * @param field O campo cujo status deve ser exibido.
      */
    public void showStatus(int step, Field field){
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

     /**
      * Determine se a simulação deve continuar em execução.
      * @return true Se houver mais de uma espécie viva.
      */
    public boolean isViable(Field field){
        return stats.isViable(field);
    }
    
     /**
      * Fornece uma visão gráfica de um campo retangular. Isto é
      * uma classe aninhada (uma classe definida dentro de uma classe) que
      * define um componente personalizado para a interface do usuário. Esta
      * componente exibe o campo.
      * Este é um material de GUI bastante avançado - você pode ignorar isso
      * para o seu projeto, se desejar.
      */
    private class FieldView extends JPanel{
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

         /**
          * Crie um novo componente FieldView.
          */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
          * Diga ao gerenciador de GUI o quão grande gostaríamos de ser.
          */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

         /**
          * Prepare-se para uma nova rodada de pintura. Já que o componente
          * pode ser redimensionado, calcule o fator de escala novamente.
          */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // Se o tamanho mudou ...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
          /**
          * Pinte no local da grade neste campo em uma determinada cor.
          */
        public void drawMark(int x, int y, Color color){
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

         /**
          * O componente de visualização de campo precisa ser exibido novamente. Copie o
          * imagem interna na tela.
          */
        public void paintComponent(Graphics g){
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Redimensione a imagem anterior.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
 
                
                
        
}

