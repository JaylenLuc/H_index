package H_index_calc;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jfree.chart.axis.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;  
import org.jfree.chart.renderer.category.StandardBarPainter;

//were swinging this cuz i dont wanna bother with JavaFx download

public class mainframe extends JFrame{
    
    class GradientPanel extends JPanel {

        public static final int VERT = 0; 
        //if int is anything other than 1 , 2, 3 then its VERT
        public static final int HOR = 1;
        public static final int DIAG_DOWN = 2;
        public static final int DIAG_UP = 3;

        public Color color_start;
        public Color color_end;
        public int direction;

        public GradientPanel(){
            super();
            this.color_start = Color.BLACK;
            this.color_start = Color.WHITE;
            this.direction = 1;
        }

        public GradientPanel(Color color1,Color color2, int dir){
            super();

            this.color_start = color1;
            this.color_end = color2;
            this.direction = dir;
        }
        
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D graphics2d = (Graphics2D) g;
            graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // System.out.println(this.color_end);
            // System.out.println(this.color_start);
            GradientPaint gradientPaint;

            if (direction == HOR){
                gradientPaint = new GradientPaint(0, getHeight()/2, this.color_start, getWidth(), getHeight()/2,this.color_end);
            }else if (direction == DIAG_DOWN){
                
                gradientPaint = new GradientPaint(0, getHeight(), this.color_start, getWidth(), 0,this.color_end);


            }else if (direction == DIAG_UP){
                gradientPaint = new GradientPaint(0, 0, this.color_start, getWidth(), getHeight(),this.color_end);
            }else{
                gradientPaint = new GradientPaint(0, 0, this.color_start,0, getHeight(),this.color_end);
            }
            graphics2d.setPaint(gradientPaint);
            graphics2d.fillRect(0,0,getWidth(),getHeight());
        }

        public void set_colors(Color color1, Color color2){
            this.color_end = color2;
            this.color_start = color1;
        }

        public void set_dir(int x){
            this.direction = x;
        }

        public int get_direction(){
            return this.direction;
        }

        public Color get_color_start(){

            return this.color_start;
        }

        public Color get_color_end(){

            return this.color_end;
        }

        public String display_constants(){

            return (String.format("VERT = %d\nHOR = %d\nDIAG_DOWN = %d, DIAG_UP = %d",
            this.VERT,this.HOR,
            this.DIAG_DOWN,this.DIAG_UP));

        }





    }

//semi_utility - functional inner classes----------------------------------------------------------------------------------------------------------------

    class calculator {
        public static Integer[] h_nums;
        public static Map<Integer, Double> percentile;
        public static int tot_cites = 0;
        public static int h_number = -1;

        //public static int total_cites = 0;
        private  static void populateMap(){
            if (percentile != null){
                return ;
            }
            //no dependency issue, just an issue of instantiating a HashMap safely and securely. This brought by the reason to do
            //the calculator class statically to maek it a pure utility class
            //where theres smoke theres fire huh. it knows of there existence , the instantiation does not work
            //what I did was create a function that is called to populate the map, it is called when
            // static functions are called and conditionals that check if the hashmap is not null is false;
            percentile = new HashMap<Integer, Double>();
            percentile.put(0,0.0 );
            percentile.put(1, 0.0);
            percentile.put(2, 0.0);
            percentile.put(3, 0.0);
            percentile.put(4, 34.0);
            percentile.put(5, 52.0);
            percentile.put(6, 65.0);
            percentile.put(7, 73.0);
            percentile.put(8, 78.0);
            percentile.put(9, 82.0);
            percentile.put(10, 85.0);
            percentile.put(11, 88.0);
            percentile.put(12, 89.0);
            percentile.put(13, 90.558);
            percentile.put(14, 91.776);
            percentile.put(15, 92.776);
            percentile.put(16, 93.614);
            percentile.put(17, 94.317);
            percentile.put(18, 94.908);
            percentile.put(19, 95.428);
            //conditionals
            //20 - 40  98
            //41-80 99
            //81 - 120 99.5
            //121 - 132 99.8
    
            percentile.put(133, 99.982);
            percentile.put(134, 99.982);
            percentile.put(135, 99.982);
            percentile.put(136, 99.983);
            percentile.put(137, 99.983);
            percentile.put(138, 99.984);
            percentile.put(139, 99.984);
            percentile.put(140, 99.985);
            percentile.put(141, 99.986);
            percentile.put(142, 99.986);
            percentile.put(143, 99.987);
            percentile.put(144, 99.987);
            percentile.put(145, 99.987);
            percentile.put(146, 99.987);
            percentile.put(147, 99.988);
            percentile.put(148, 99.988);
            percentile.put(149, 99.988);
            percentile.put(150, 99.989);

        }

        //each h index score is seperated by a single ',' in a String
        // h index (f) = max{i ∈ N : f[i] >= i}
    
    
        public static int h_bomb(String ind){
            //takes in user input and outputs h index
            //System.out.println("fsdafdfdsa");
            try{
    
                h_nums = Stream.of(ind.split(",")).filter(i -> i.length() < 7 && !i.equals(""))
                .map(Integer::valueOf).sorted(Comparator.reverseOrder())
                .toArray(size -> new Integer[size]);
                //  for (int i : h_nums){
                //  	System.out.println(i);
                //  }
    
            }catch(NumberFormatException e){
                System.out.println(e.getMessage());
                return -1;
            }
            int i;
            for (i = 0; i < h_nums.length; i++){
                if (h_nums[i]  < i+1){


                    h_number = i;
                    return i;
                }
                
            }
            h_number = i;
            return i;
            
        }
 
    
        public static Double h_percent(int h){
            //conditionals
            //20 - 40  98
            //41-80 99
            //81 - 120 99.5
            //121 - 132 99.8

            if (percentile == null){
                populateMap();
            }
            //System.out.println("HERE");
            Double d = percentile.get(h);
            //System.out.println("HERE1");
            if (d == null){
                if (h <= 40 && h >= 20){
                    return 98.0;
                }else if (h >= 41 && h <=80){
                    return 99.0;
                }else if (h >= 81 && h <= 120){
                    return 99.5;
                }else if (h >= 21 && h <= 132){
                    return 99.8;

                }else if (h >= 133 && h <= 333){
                    return 99.8;
                }else {
                    return 100.0;
                }

            }else{
                //System.out.println(d);
                return d;
            }
        }
        public static long i10_bomb(){
            try{
                return Stream.of(h_nums).filter(i -> i >= 10).collect(Collectors.counting());
            }catch(Exception e){
                return -1;
            }
        }

        public static int g_bomb(){
            //g^2 <= Σ (i <= g)  ci

            try{
                int sum;
                sum = 0;
                for (int i = 0; i < h_nums.length; i++){
                    sum+= h_nums[i];
                    if (Math.pow(i+1,2) > sum){
                        return i;
                    }
                }
                return h_nums.length;
            }catch(Exception e){
                return -1;
            }
        }

        public static int total_citations(){

            tot_cites = Stream.of(h_nums).collect(Collectors.summingInt(Integer::intValue));
            return tot_cites;

        }

        public static double e_bomb()
        {
            //1,2,3,4,5
            //0,1,2,3,4
            //Σ (j = 1) cit j - h^2
            //no fukcing oracle to test this shit on
            double e_num = 0;
            //h = 5
            for (int i = 0; i < h_number; i++){
                e_num += h_nums[i];
            }

            e_num = e_num - Math.pow(h_number,2);

            return e_num;



        }
    }
    
//semi_utility - functional inner classes END----------------------------------------------------------------------------------------------------------------

    private JFrame frame;
    private JDialog n;//creator dialog
	private final Color defaultcolor = new Color(238,238,238);
    public WindowEvent creatorwindow;
    private JTextArea textArea;
    private JPanel panel;
    private JTextField houtput;
    private JTextField hpercout;
    private GridBagConstraints gbc;

    public JDialog graph_window;
    public ChartPanel graph_panel;

    public JDialog g_graph_window;
    public ChartPanel g_graph_panel;

    public JDialog e_graph_window;
    public ChartPanel e_graph_panel;

    public XYSeriesCollection graph_dataset = new XYSeriesCollection();  
    public XYSeriesCollection e_graph_dataset = new XYSeriesCollection();  

    public XYSeries series =  new XYSeries("Publication");  
    public int prev_ = -1;


    public JFreeChart chart;
    public JFreeChart g_chart;
    public JFreeChart e_chart;

    public ValueMarker marker1;
    public ValueMarker marker;
    public ValueMarker marker2;
    public GradientPanel nnn;
    public JDialog notif;
    public JDialog more_window;
    public GradientPanel more_panel;

    public JTextField i10field;
    public JLabel i10Label;

    public JTextField gfield;
    public JLabel gLabel;

    public JTextField sumfield;
    public JLabel sumLabel;

    public JTextField e_field;
    public JLabel e_Label;

    public ValueMarker gmarker;
    public ValueMarker gmarker1;

    public mainframe(){
        graph_dataset.addSeries(series);
        //System.out.println(calculator.percentile);
        //System.out.println(calculator.percentile.get(1));
        //System.out.println(n);
        frame = new JFrame();
        //frame.setLayout(new BorderLayout())   ;
        frame.setSize(600,600);
        frame.setTitle("Check your H-Index!!");
        frame.setJMenuBar(createMenu());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setContentPane(createIO());
        //Box box1 = Box.createVerticalBox();
        //box1.add(frame.getContentPane());
        frame.getContentPane().setBackground(new Color(196,178,162));
        //gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createButtonOutPut(),gbc);
        houtput = new JTextField(4);
        //houtput.setText("99.545");
        houtput.setEditable(false);
        //gbc.gridwidth = 50;
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel hres = new JLabel("H-Index: ");
        panel.add(hres,gbc);
        gbc.insets = new Insets(10,140,10,10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        Color pass = rgb_complement_color();
        Border border = BorderFactory.createLineBorder(pass, 2);
        houtput.setBorder(border);
        panel.add(houtput, gbc);
        gbc.insets = new Insets(10,140,10,10);
        gbc.gridx = 0;
        gbc.gridy = 5;
        hpercout = new JTextField(4);
        Border border1 = BorderFactory.createLineBorder(pass, 2);
        hpercout.setBorder(border1);
        JLabel hpercent = new JLabel("H-Index Percentile Approximation: ");
        panel.add(hpercout, gbc);
        gbc.insets = new Insets(10,10,10,157);
        gbc.gridx = 0;
        gbc.gridy = 5;
        hpercout.setEditable(false);
        panel.add(hpercent, gbc);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 6;
        JButton more_button = new JButton("Click to see further metrics and other bibliometric indices");
        more_button.addActionListener(new more_listener());
        panel.add(more_button,gbc);


        //frame.add(houtput);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

    }
    //returns the complement color for any specified j dialog 'x'
    private void rgb_complement(JDialog x){
        if (x != null){
            
            Color temp = frame.getContentPane().getBackground();
            int r = temp.getRed();
            int g = temp.getGreen();
            int b = temp.getBlue();
            Integer[] temparr = {r,g,b};
            ArrayList<Integer> color_array = new ArrayList<Integer>(Arrays.asList(temparr));
            r = Collections.max(color_array) + Collections.min(color_array) - r;
            b = Collections.max(color_array) + Collections.min(color_array) - b;
            g = Collections.max(color_array) + Collections.min(color_array) - g;
            x.getContentPane().setBackground(new Color(r,g,b));
        }
    }
    //returms the complement color for the main frame
    private Color rgb_complement_color(){
        //if (x != null){
            
        Color temp = frame.getContentPane().getBackground();
        int r = temp.getRed();
        int g = temp.getGreen();
        int b = temp.getBlue();
        Integer[] temparr = {r,g,b};
        ArrayList<Integer> color_array = new ArrayList<Integer>(Arrays.asList(temparr));
        r = Collections.max(color_array) + Collections.min(color_array) - r;
        b = Collections.max(color_array) + Collections.min(color_array) - b;
        g = Collections.max(color_array) + Collections.min(color_array) - g;
        return new Color(r,g,b);
        //}
        
    }
    // private Color rgb_complement_color2(){
    //     //if (x != null){
            
    //     Color temp = frame.getContentPane().getBackground();
    //     int r = temp.getRed();
    //     int g = temp.getGreen();
    //     int b = temp.getBlue();
    //     Integer[] temparr = {r,g,b};
    //     ArrayList<Integer> color_array = new ArrayList<Integer>(Arrays.asList(temparr));
    //     r = (Collections.max(color_array) + Collections.min(color_array) - r )  ;
    //     b = (Collections.max(color_array) + Collections.min(color_array) - b) ;
    //     g = (Collections.max(color_array) + Collections.min(color_array) - g) ;
    //     return new Color(r,g,b);
    //     //}
        
    // }
        

    private JMenuBar createMenu(){
        JMenu setting = new JMenu("Settings");
        JMenu color = new JMenu("Color");  
        JMenuBar menubar = new JMenuBar();  
        JMenuItem exitItem = new JMenuItem("Exit");  
        JMenuItem red = new JMenuItem("red");  
        JMenuItem green = new JMenuItem("green");
        JMenuItem blue = new JMenuItem("blue");  
        JMenuItem white = new JMenuItem("white"); 
        JMenuItem grey = new JMenuItem("grey");   
        JMenuItem yellow = new JMenuItem("yellow");   
        JMenuItem purple = new JMenuItem("purple");
        JMenuItem pink = new JMenuItem("pink");      
        JMenuItem brown = new JMenuItem("brown");    
        JMenu graph_menu = new JMenu("Graph Visualizers");
        JMenuItem h_graph = new JMenuItem("H-Index Graph"); 
        JMenuItem g_graph = new JMenuItem("g-Index Graph"); 

        g_graph.addActionListener(new graph_listener());
        h_graph.addActionListener(new graph_listener());
        pink.addActionListener(new color_listener());
        purple.addActionListener(new color_listener());
        brown.addActionListener(new color_listener());
        yellow.addActionListener(new color_listener());
        grey.addActionListener(new color_listener());
        red.addActionListener(new color_listener());
        green.addActionListener(new color_listener());
        blue.addActionListener(new color_listener());
        white.addActionListener(new color_listener());

        graph_menu.add(h_graph);
        graph_menu.add(g_graph);

        color.add(red); color.add(green); color.add(blue); 
        color.add(white); color.add(grey); color.add(yellow);
        color.add(purple); color.add(pink); color.add(brown);
        exitItem.addActionListener(new exit_listener());
        JMenuItem creator = new JMenuItem("the developer");
        creator.addActionListener(new creator_listener());
        setting.add(exitItem);
        setting.add(creator);
        menubar.add(setting);
        menubar.add(color);
        menubar.add(graph_menu);
        
        return menubar;


    }

    private JPanel createIO() {
        final int ROWS = 10; // Lines of text
        final int COLUMNS = 30; // Characters in each row  JTextArea textArea = new JTextArea(ROWS, COLUMNS);
        panel = new JPanel(new GridBagLayout()); 
        gbc = new GridBagConstraints();
        
        //panel.setLayout(new BorderLayout());  
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);
        panel.add(new JLabel("<html>Enter the number of citations seperated by a ',' with no space:  <br/></html>"), gbc);
        //panel.setLayout(new GridBagLayout());
        //panel.setLayout(new BorderLayout());
        //gbc = new GridBagConstraints();

        textArea = new JTextArea(ROWS,COLUMNS);
        textArea.setLineWrap(true);
        //textArea.setContentType("text/html");
        textArea.setText("Example: 3,4,7,38");
        textArea.addKeyListener(new Htext_listener());
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(scrollPane, gbc);
        return panel;
    }

    private JButton createButtonOutPut(){
        JButton j = new JButton("Calculate H-Index!");
        j.addActionListener(new Hbutton_listener());
        
        
        return j;
    }

    private void createGraph(){
        //graph_dataset -> data set we are going to use
        //appends the new chart to the graph_panel
        //it first adds it to the chartpanel
        chart = ChartFactory.createScatterPlot(  
        "Raw H-Index Graph (discrete)",   
        "Research paper count", "Citations", graph_dataset);
        ((XYPlot)chart.getPlot()).getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ((XYPlot)chart.getPlot()).getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        if (graph_panel == null){
            graph_panel = new ChartPanel(chart,500,500,500,500,500,500,
            true,true,true,true,true,true);
            
        }else{
            graph_panel.setChart(chart);
        }

    }

    //g index popualtion of g_chart and g_graph_panel
    private void g_createGraph(){
        g_chart = ChartFactory.createXYBarChart("g-Index Bar Graph", 
        "Research paper count", false,"Citations", graph_dataset);
        //((XYPlot)g_chart).getValueAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ((XYPlot)g_chart.getPlot()).getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ((XYPlot)g_chart.getPlot()).getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        if (g_graph_panel == null){
            g_graph_panel = new ChartPanel(g_chart,500,500,500,500,500,500,
            true,true,true,true,true,true);

        }else{
            g_graph_panel.setChart(g_chart);
        }
    }

    private void change_data_set(){
        series.clear();
        for (int i = 0; i < calculator.h_nums.length; i++){
            series.add(i+1,calculator.h_nums[i]);
        }
        //series.setSeriesPaint();

    }

    private void e_createGraph(){
        //create a bar chart which shows aggregate citations in all articles that are above the H index and at or below the H - index
        //grpuped by ignored excess citations and counted citations (counted citations include all publications at the H-index  so its all cits that are < h_number)
    }




    
	//ACTION LISTENERS----------------------------------------------------------------------------------------------------------------

    class exit_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }


    class more_listener implements ActionListener {
        //g^2 <= Σ ci
        public void actionPerformed(ActionEvent event) {
            if (more_window == null){
                more_window = new JDialog();
                more_window.setDefaultCloseOperation(HIDE_ON_CLOSE);
                more_window.setTitle("More Bibliometric Indices");
                more_window.setSize(600,600);
                more_panel = new GradientPanel(frame.getContentPane().getBackground(), rgb_complement_color(), 3);
               
                more_window.setContentPane(more_panel);

                i10Label = new JLabel("i10 Index: ");
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(5,5,5,5);


                
                more_panel.add(i10Label, gbc);

                i10field = new JTextField(4);

                i10field.setEditable(false);

                i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));

                more_panel.add(i10field, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.insets = new Insets(5,5,5,5);

                gLabel = new JLabel("G-Index:");
                more_panel.add(gLabel,gbc);
                gfield = new JTextField(4);

                gfield.setEditable(false);

                gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));

                more_panel.add(gfield,gbc);

                sumLabel = new JLabel("Total Citations:");
                more_panel.add(sumLabel,gbc);
                sumfield = new JTextField(4);

                sumfield.setEditable(false);

                sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));

                more_panel.add(sumfield,gbc);


                e_Label = new JLabel("e-Index:");
                more_panel.add(e_Label,gbc);
                e_field = new JTextField(4);

                e_field.setEditable(false);

                e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));

                more_panel.add(e_field,gbc);





            }
            if (frame.getContentPane().getBackground().equals(new Color(255,255,255))){
                sumfield.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                gfield.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                i10field.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
            }
            if (!houtput.getText().isBlank()){
                i10field.setText(String.valueOf(calculator.i10_bomb()));
                gfield.setText(String.valueOf(calculator.g_bomb()));
                sumfield.setText(String.valueOf(calculator.total_citations()));
                e_field.setText(String.valueOf(calculator.e_bomb()));
            }


            more_window.setVisible(true);
        }
    }

    //listens for h button press and calculates and sets the number into the output box respectively

    class Hbutton_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //System.out.print("HELLO:::   ");
            //System.out.println(calculator.get(1));

            int res = calculator.h_bomb(textArea.getText()); //popualtes the public static variable : h_nums

            long i_res = calculator.i10_bomb();

            int g_res = calculator.g_bomb();

            double e_res = calculator.e_bomb();

            double resperc = calculator.h_percent(res);

            if (res != -1 && !textArea.getText().contains("Example:")){
                houtput.setText(String.valueOf(res));
                hpercout.setText(String.valueOf(resperc));
                change_data_set();

            }
            if (textArea.getText().contains("Example:")){
                series.clear();
            }
            if (g_graph_window != null){
                g_createGraph();
                g_graph_window.add(g_graph_panel);
            }
            if (graph_window != null ){
                //System.out.println(frame.getContentPane().getBackground().toString());
                createGraph();
                graph_window.add(graph_panel);
                // JLabel ttt = new JLabel("Right-Click for addtional chart options");
                // gbc.gridx = 0;
                // gbc.gridy = -7;
                // graph_window.add(ttt);
                //

                //markers for h index graph
                if (res != -1){
                    //System.out.println(res);
                    marker = new ValueMarker(res);  // position is the value on the axis
                    marker.setPaint(rgb_complement_color());
                    marker.setStroke(new BasicStroke(1.5f));
                    marker1 = new ValueMarker(res);  // position is the value on the axis
                    marker1.setPaint(rgb_complement_color());
                    marker1.setStroke(new BasicStroke(1.5f));
                    marker.setLabel(String.format("Papers with at least\n %d citations",res));
                    marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                    marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                    marker1.setLabel(String.format("Papers with less than %d citations",res));
                    marker1.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
                    marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                    marker2 = new ValueMarker(res);  // position is the value on the axis
                    marker2.setPaint(rgb_complement_color());
                    marker2.setStroke(new BasicStroke(1.5f));
                    marker2.setLabel(String.format("%d most highly cited papers",res));
                    marker2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                    marker2.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
                    //r=253,g=253,b=150
                    if (frame.getContentPane().getBackground().getRed() == 189 &&frame.getContentPane().getBackground().getGreen() == 182 
                    &&frame.getContentPane().getBackground().getBlue() == 206){
                        marker.setPaint(new Color(130, 180, 70));
                        marker1.setPaint(new Color(130, 180, 70));
                        marker2.setPaint(new Color(130, 180, 70));
        
                    }
                    else if (frame.getContentPane().getBackground().getRed() == 191 &&frame.getContentPane().getBackground().getGreen() == 227 
                        &&frame.getContentPane().getBackground().getBlue() == 180){
                            marker.setPaint(new Color(218,112,234));
                            marker1.setPaint(new Color(218,112,234));
                            marker2.setPaint(new Color(218,112,234));
            
                    }
                    else if (frame.getContentPane().getBackground().getRed() == 196 &&frame.getContentPane().getBackground().getGreen() == 178 
                    &&frame.getContentPane().getBackground().getBlue() == 162){
                        //145,162,176
                        marker.setPaint(new Color(145,162,176));
                        marker1.setPaint(new Color(145,162,176));
                        marker2.setPaint(new Color(145,162,176));
                    }
                    //blue
                    //171, 215, 235
                    else if (frame.getContentPane().getBackground().getRed() == 171 &&frame.getContentPane().getBackground().getGreen() == 215 
                    &&frame.getContentPane().getBackground().getBlue() == 235){
                        //235,191,171
                        marker.setPaint(new Color(255,191,181));
                        marker1.setPaint(new Color(255,191,181));
                        marker2.setPaint(new Color(255,191,181));
                    }
                    else if (frame.getContentPane().getBackground().getRed() == 255 &&frame.getContentPane().getBackground().getGreen() == 244 
                    &&frame.getContentPane().getBackground().getBlue() == 189){
                        //177,156,217
                        marker.setPaint(new Color(177,156,217));
                        marker1.setPaint(new Color(177,156,217));
                        marker2.setPaint(new Color(177,156,217));
    
                    }
                //[r=191,g=227,b=180
                    //marker.setLabel("here"); // see JavaDoc for labels, colors, strokes

                    XYPlot plot =chart.getXYPlot();
                    plot.addRangeMarker(marker1);
                    plot.addDomainMarker(marker);
                    plot.addRangeMarker(marker2);
                }
                
                //graph_window.setVisible(true);
            }

            if (g_graph_panel != null){
                gmarker1 = null;
                gmarker = null;
                g_createGraph();

                //gplot.setBackgroundPaint(SystemColor.inactiveCaption);
                //((BarRenderer)gplot.getRenderer()).setBarPainter(new StandardBarPainter());
                //g_chart.setSeriesPaint(0, Color.blue);
                //graph_window.add(graph_panel);


                g_graph_window.add(g_graph_panel);
                rgb_complement(g_graph_window);
            
                if (!houtput.getText().isEmpty() ){
                    //gmarker(1)
                    int ttem = (int)calculator.g_bomb();
                    gmarker = new ValueMarker(ttem);
                    gmarker.setPaint(rgb_complement_color());

                    gmarker1 = new ValueMarker((int)calculator.h_nums[ttem-1]);
                    gmarker1.setPaint(rgb_complement_color());
                    //setStandardTickUnits(NumberAxis.createIntegerTickUnits() )
                    XYPlot plot = g_chart.getXYPlot();
                    //plot.addRangeMarker(gmarker1);

                    gmarker1.setStroke(new BasicStroke(1.5f));
                    gmarker.setStroke(new BasicStroke(1.5f));
                    gmarker1.setLabel(String.format("Top %d Papers",calculator.g_bomb()));
                    gmarker1.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                    gmarker1.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
                    
                    plot.addDomainMarker(gmarker);
                    plot.addRangeMarker(gmarker1);

                    if (frame.getContentPane().getBackground().getRed() == 189 &&frame.getContentPane().getBackground().getGreen() == 182 
                    &&frame.getContentPane().getBackground().getBlue() == 206){
                        gmarker.setPaint(new Color(130, 180, 70));
                        gmarker1.setPaint(new Color(130, 180, 70));
                        
        
                    }
                    //r=191,g=227,b=180
                    else if (frame.getContentPane().getBackground().getRed() == 191 &&frame.getContentPane().getBackground().getGreen() == 227 
                    &&frame.getContentPane().getBackground().getBlue() == 180){
                        gmarker.setPaint(new Color(218,112,234));
                        gmarker1.setPaint(new Color(218,112,234));
                        
        
                    }
                    //196,178,162
                    else if (frame.getContentPane().getBackground().getRed() == 196 &&frame.getContentPane().getBackground().getGreen() == 178 
                    &&frame.getContentPane().getBackground().getBlue() == 162){
                        //145,162,176
                        gmarker.setPaint(new Color(145,162,176));
                        gmarker1.setPaint(new Color(145,162,176));
                    
                    }
                    //blue
                    //171, 215, 235
                    else if (frame.getContentPane().getBackground().getRed() == 171 &&frame.getContentPane().getBackground().getGreen() == 215 
                    &&frame.getContentPane().getBackground().getBlue() == 235){
                        //235,191,171
                        gmarker.setPaint(new Color(255,191,181));
                        gmarker1.setPaint(new Color(255,191,181));
                    
                    }
                    //255, 244, 189
                    else if (frame.getContentPane().getBackground().getRed() == 255 &&frame.getContentPane().getBackground().getGreen() == 244 
                    &&frame.getContentPane().getBackground().getBlue() == 189){
                        //177,156,217
                        gmarker.setPaint(new Color(177,156,217));
                        gmarker1.setPaint(new Color(177,156,217));
                    

                    }
                }
            }

            if (more_window != null && i_res != -1 && g_res != -1 && e_res != -1){
                i10field.setText(String.valueOf(i_res));
                gfield.setText(String.valueOf(g_res));
                sumfield.setText(String.valueOf(calculator.total_citations()));
                e_field.setText(String.valueOf(e_res));

            }
           

            
        }
    }

    // BUG:: fix the label alignment issue
    //  -sort of fixed 
    // BUG:: value markers do not dissapear when the h index is 0 or 1. not substantially breaking but is not pleasing to the eye
    

    class graph_listener implements ActionListener {
        private int counter = 0;
        public void actionPerformed(ActionEvent event) {
            counter +=1 ;
            if (graph_window == null ){
                if (((JMenuItem)event.getSource()).getText() == "H-Index Graph"){
                    graph_window = new JDialog();
                    graph_window.setLayout(new GridBagLayout());
                    graph_window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    graph_window.setSize(600,600);
                    graph_window.setTitle("Raw H-index Cartesian Visualizer");
                    graph_window.setResizable(false);
                    createGraph();
                    //graph_window.add(graph_panel);
                    graph_window.add(graph_panel);
                    //Paint p = new GradientPaint(0, 0, Color.red, 100, 100, Color.pink, true); 
                    //         graph_panel.setBackground(Color.RED);
                    //         graph_panel.setPreferredSize(new java.awt.Dimension(graph_panel.getWidth(), panel.getHeight()));
                    //         graph_panel.setSize(new java.awt.Dimension(graph_panel.getWidth(), graph_panel.getHeight()));
                    // // frame.invalidate();
                    //         graph_window.validate(); 
                    //chart.getXYPlot().setBackgroundPaint(rgb_complement_color());  
                    // ((XYPlot)chart).getPlot().setBackgroundPaint( rgb_complement_color() );
                    //graph_panel.setBackground( rgb_complement_color() );
                    rgb_complement(graph_window);
                    //graph_window.setContentPane(graph_);
                    //maybe set contentpane of grapj_window to Chart Panel which in this case is the graph_panel
                    //graph_window.setVisible(true);
                }
                //GRADIENT PANEL EXTENSION--------------------------------------------------------------------------
                // public static final int VERT = 0;
                // public static final int HOR = 1;
                // public static final int DIAG_DOWN = 2;
                // public static final int DIAG_UP = 3;

                // public Color color_start, color_end;
                // public int direction;

                // public GradientPanel(){
                //     super();
                //     this.color_start = Color.BLACK;
                //     this.color_start = Color.WHITE;
                //     this.direction = 1;
                // }

                // public GradientPanel(Color color1,Color color2, int dir){
                //     super();

                //     this.color_start = color1;
                //     this.color_start = color2;
                //     this.direction = dir;
                // }  
                
                notif = new JDialog();
                notif.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                
                notif.setSize(450,85);
                notif.setResizable(false);
                // System.out.println(rgb_complement_color());
                // System.out.println(frame.getContentPane().getBackground());
                nnn = new GradientPanel(rgb_complement_color(),
                frame.getContentPane().getBackground()
                ,2);
                JLabel ttt = new JLabel("Right click on the chart for additonal features or to zoom in/out.");
                nnn.add(ttt);
                //notif.setContentPane(nnn);
                
                notif.setContentPane(nnn);
                
                
                //----------------------------------------------------------------------------------------------------
                if (!houtput.getText().isEmpty() ){
                    //System.out.println(houtput.getText());
                    marker = new ValueMarker(Double.valueOf(houtput.getText()));  // position is the value on the axis
                    marker.setPaint(rgb_complement_color());
                    marker.setStroke(new BasicStroke(1.5f));
                    //marker.setLabel("Original Close (02:00)");
                    marker1 = new ValueMarker(Double.valueOf(houtput.getText()));  // position is the value on the axis
                    marker1.setPaint(rgb_complement_color());
            
                    marker1.setStroke(new BasicStroke(1.5f));
                    marker.setLabel(String.format("Papers with at least\n %s citations",houtput.getText()));
                    marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                    marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                    marker1.setLabel(String.format("Papers with less than %s citations",houtput.getText()));
                    marker1.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
                    marker1.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                    marker2 = new ValueMarker(Double.valueOf(houtput.getText()));  // position is the value on the axis
                    marker2.setPaint(rgb_complement_color());
                    marker2.setStroke(new BasicStroke(1.5f));
                    marker2.setLabel(String.format("%s most highly cited papers",houtput.getText()));
                    marker2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                    marker2.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
                    //System.out.println(frame.getContentPane().getBackground().toString());
                    //r=189,g=182,b=206
                    if (frame.getContentPane().getBackground().getRed() == 189 &&frame.getContentPane().getBackground().getGreen() == 182 
                        &&frame.getContentPane().getBackground().getBlue() == 206){
                            marker.setPaint(new Color(130, 180, 70));
                            marker1.setPaint(new Color(130, 180, 70));
                            marker2.setPaint(new Color(130, 180, 70));
            
                    }
                    //r=191,g=227,b=180
                    else if (frame.getContentPane().getBackground().getRed() == 191 &&frame.getContentPane().getBackground().getGreen() == 227 
                    &&frame.getContentPane().getBackground().getBlue() == 180){
                        marker.setPaint(new Color(218,112,234));
                        marker1.setPaint(new Color(218,112,234));
                        marker2.setPaint(new Color(218,112,234));
        
                    }
                    //196,178,162
                    else if (frame.getContentPane().getBackground().getRed() == 196 &&frame.getContentPane().getBackground().getGreen() == 178 
                    &&frame.getContentPane().getBackground().getBlue() == 162){
                        //145,162,176
                        marker.setPaint(new Color(145,162,176));
                        marker1.setPaint(new Color(145,162,176));
                        marker2.setPaint(new Color(145,162,176));
                    }
                    //blue
                    //171, 215, 235
                    else if (frame.getContentPane().getBackground().getRed() == 171 &&frame.getContentPane().getBackground().getGreen() == 215 
                    &&frame.getContentPane().getBackground().getBlue() == 235){
                        //235,191,171
                        marker.setPaint(new Color(255,191,181));
                        marker1.setPaint(new Color(255,191,181));
                        marker2.setPaint(new Color(255,191,181));
                    }
                    //255, 244, 189
                    else if (frame.getContentPane().getBackground().getRed() == 255 &&frame.getContentPane().getBackground().getGreen() == 244 
                    &&frame.getContentPane().getBackground().getBlue() == 189){
                        //177,156,217
                        marker.setPaint(new Color(177,156,217));
                        marker1.setPaint(new Color(177,156,217));
                        marker2.setPaint(new Color(177,156,217));
    
                    }
                
                    
                    //
                    //marker.setLabel("here"); // see JavaDoc for labels, colors, strokes
                    if (((JMenuItem)event.getSource()).getText() == "H-Index Graph"){
                    XYPlot plot =chart.getXYPlot();
                    plot.addRangeMarker(marker1);
                    plot.addDomainMarker(marker);
                    plot.addRangeMarker(marker2);
                    }
                    
                }
            } 
            if (g_graph_window == null){
                if (((JMenuItem)event.getSource()).getText() == "g-Index Graph"){
                    g_graph_window = new JDialog();
                    g_graph_window.setLayout(new GridBagLayout());
                    g_graph_window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    g_graph_window.setSize(600,600);
                    g_graph_window.setTitle("g-index Cartesian Visualizer");
                    g_graph_window.setResizable(false);
                    g_createGraph();

                    //gplot.setBackgroundPaint(SystemColor.inactiveCaption);
                    //((BarRenderer)gplot.getRenderer()).setBarPainter(new StandardBarPainter());
                    //g_chart.setSeriesPaint(0, Color.blue);
                    //graph_window.add(graph_panel);


                    g_graph_window.add(g_graph_panel);
                    rgb_complement(g_graph_window);
                
                    if (!houtput.getText().isEmpty() ){
                        //gmarker(1)
                        int ttem = (int)calculator.g_bomb();
                        gmarker = new ValueMarker(ttem);
                        gmarker.setPaint(rgb_complement_color());

                        gmarker1 = new ValueMarker((int)calculator.h_nums[ttem-1]);
                        gmarker1.setPaint(rgb_complement_color());
                        //setStandardTickUnits(NumberAxis.createIntegerTickUnits() )
                        XYPlot plot = g_chart.getXYPlot();
                        //plot.addRangeMarker(gmarker1);

                        gmarker1.setStroke(new BasicStroke(1.5f));
                        gmarker.setStroke(new BasicStroke(1.5f));
                        plot.addDomainMarker(gmarker);
                        plot.addRangeMarker(gmarker1);
                        gmarker1.setLabel(String.format("Top %d Papers",calculator.g_bomb()));
                        gmarker1.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                        gmarker1.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);

                        if (frame.getContentPane().getBackground().getRed() == 189 &&frame.getContentPane().getBackground().getGreen() == 182 
                        &&frame.getContentPane().getBackground().getBlue() == 206){
                            gmarker.setPaint(new Color(130, 180, 70));
                            gmarker1.setPaint(new Color(130, 180, 70));
                            
            
                        }
                        //r=191,g=227,b=180
                        else if (frame.getContentPane().getBackground().getRed() == 191 &&frame.getContentPane().getBackground().getGreen() == 227 
                        &&frame.getContentPane().getBackground().getBlue() == 180){
                            gmarker.setPaint(new Color(218,112,234));
                            gmarker1.setPaint(new Color(218,112,234));
                            
            
                        }
                        //196,178,162
                        else if (frame.getContentPane().getBackground().getRed() == 196 &&frame.getContentPane().getBackground().getGreen() == 178 
                        &&frame.getContentPane().getBackground().getBlue() == 162){
                            //145,162,176
                            gmarker.setPaint(new Color(145,162,176));
                            gmarker1.setPaint(new Color(145,162,176));
                           
                        }
                        //blue
                        //171, 215, 235
                        else if (frame.getContentPane().getBackground().getRed() == 171 &&frame.getContentPane().getBackground().getGreen() == 215 
                        &&frame.getContentPane().getBackground().getBlue() == 235){
                            //235,191,171
                            gmarker.setPaint(new Color(255,191,181));
                            gmarker1.setPaint(new Color(255,191,181));
                           
                        }
                        //255, 244, 189
                        else if (frame.getContentPane().getBackground().getRed() == 255 &&frame.getContentPane().getBackground().getGreen() == 244 
                        &&frame.getContentPane().getBackground().getBlue() == 189){
                            //177,156,217
                            gmarker.setPaint(new Color(177,156,217));
                            gmarker1.setPaint(new Color(177,156,217));
                           
        
                        }
                    }

                }
            
            }
            
        
            
            if (graph_window != null && ((JMenuItem)event.getSource()).getText() == "H-Index Graph") graph_window.setVisible(true);
            if (g_graph_window != null && ((JMenuItem)event.getSource()).getText() == "g-Index Graph") g_graph_window.setVisible(true);
            if (counter < 2){notif.setVisible(true);}
            

        }
    }
    
    class creator_listener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            //System.out.println(n);
            if (n == null){
                n = new JDialog();
                n.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                n.setSize(600,600);
                n.setTitle("The Dev");
                n.setResizable(false);
                n.setLayout(new BorderLayout());
            
                JLabel meee;
                JPanel y;
                String iurl = "https://media-exp1.licdn.com/dms/image/D5603AQEfV5LhlycK0w/profile-displayphoto-shrink_400_400/0/1670322353185?e=1675900800&v=beta&t=CIhnVk7-shv8FJGrEbdl9YY-RvouBt6IldnYg8_gzG0";
                try{
                   
                    File file = new File("");
                    File file2 = new File("./jaylen");
                    
                    //System.out.println("--------");
                    //String url = mainframe.class.getResource("jaylen").getPath();
                    //System.out.println(url);
                
                    file = new File("./jaylen");
                    
                    //EXAMPLE FILEPATH of photo :: /Users/jaylenluc/Desktop/H_index_calc/jaylen
                    //String user_dir = System.getProperty("user.dir");
                    // /Users/jaylenluc/Desktop/H_index_calc/flag.txt
                    //^prints out /Users/jaylenluc/jaylen
                    BufferedImage mePicture = ImageIO.read(file2);
                    meee = new JLabel(new ImageIcon(mePicture));
                    y = new JPanel();
                    
                    
                }catch(IOException e){

                    try{
                        URL url = new URL(iurl);
                        BufferedImage mePicture = ImageIO.read(url);

                        meee = new JLabel(new ImageIcon(mePicture));
                        y = new JPanel();
                    }catch(IOException x){
                        meee = new JLabel(new ImageIcon());
                        y = new JPanel();

                    }
                    
                    
                    
                    
                }
                y.setLayout(new BorderLayout());
                y.add(meee, BorderLayout.CENTER);
                y.add(new JLabel("<html>Hi everyone! Thanks for using my application I made for fun instead of studying for finals.<br/> "+
                 "I am 20 years old living in a declining US empire and cannot wait for winter break to start because the "+
                 "quarter system is disillusioning me. I have strong opinions <br/>about Acid Communism, Corporate homogeneity,"+
                  "and Uninformed Opinions on tech extremism<br/><br/>Here is a protait of me not under the influence of any gabapentenoids or any pharmacologically active subtasnces</html>"), BorderLayout.NORTH);
                n.setContentPane(y);
                n.getContentPane().setBackground(frame.getContentPane().getBackground());
                //System.out.println(frame.getBackground());
                rgb_complement(n);
                n.setVisible(true);
                
                creatorwindow = new WindowEvent(n, WindowEvent.WINDOW_ACTIVATED);
                //n.dispatchEvent(creatorwindow);
                
                
            }else{
                //System.out.println("n");
                n.setVisible(true);
                
            }
        }
    }
    //FIXED STATUS :: YES
    //*************************************************************************************************************************************************************** 
    //stream takes care and handles for both these bugs. now the challenge is preventing them from even occuring at the keybaord press
    //KNOWN BUG:: ability to place mutliple commas consequtively in places other than the last index if and only if there is not a comma at the last char space
    //get caret position and see if the position to the right or left contains , in which you consume()
    //CARET POS:: 0a1b2c3d4e5
    //FIXED::
    //KNOWN BUG:: ability to place numbers with length greater than 9
    //parse text and count digit chars until a comma is reached or index 0 is reached. if num > 9 then consume()
    //*************************************************************************************************************************************************************** 
    class Htext_listener implements KeyListener {
        //public boolean prev = false;

        public boolean good = true;
        public void keyPressed(KeyEvent event) {
            //System.out.println(((JTextArea)event.getSource()).getText());
            good = true;
            int code = event.getKeyCode();
            char c = event.getKeyChar();
            //System.out.println(code);

            if (((JTextArea)event.getSource()).getText().equals("Example: 3,4,7,38") && 
            Character.isDigit(event.getKeyChar())){
                ((JTextArea)event.getSource()).setText("");
                //System.out.println("fdsaf");

            }
            else if (((JTextArea)event.getSource()).getText().equals("Example: 3,4,7,38") && 
            !Character.isDigit(event.getKeyChar())){
                good = false;
                this.keyTyped(event);

            }
            // else if (!(((JTextArea)event.getSource()).getText().equals("Example: 3,4,7,38")) && c == ',' &&
            // ((JTextArea)event.getSource()).getCaretPosition() !=
            // ((JTextArea)event.getSource()).getText().length-1){

            //     System.out.println("arrar");
            //     good = false;
            //     this.keyTyped(event);
            // }

            else if(!Character.isDigit(c) && c != ','  && code != KeyEvent.VK_BACK_SPACE 
            && code != KeyEvent.VK_LEFT && code != KeyEvent.VK_RIGHT) {
                good = false;
                this.keyTyped(event); // ignore event
            }
            else if (Character.isDigit(c)){
                int count = 0;
                char[] char_arr = ((JTextArea)event.getSource()).getText().toCharArray();
                for (int b = ((JTextArea)event.getSource()).getText().length()-1; b >= 0 ; b--){
                    if (Character.isDigit(char_arr[b])){
                        count++;
                    }else{
                        break;
                    }
                    //0000,000
                    if (count > 5){
                        good = false;
                        this.keyTyped(event); // ignore event
                    }
                }
            }
            else{
                int pos = ((JTextArea)event.getSource()).getCaretPosition() ;
                String text = ((JTextArea)event.getSource()).getText();
                //System.out.println(pos);
                
                if ((c == ',')&&(pos != 0 && text.charAt(pos-1) == ',' || (text.length() != pos && text.charAt(pos) == ','))){
                    good = false;
                    this.keyTyped(event);
                }

            }
            //0h1i
            
        }
        public void keyReleased(KeyEvent event){
            if (((JTextArea)event.getSource()).getText().equals("")){
                ((JTextArea)event.getSource()).setText("Example: 3,4,7,38");

            }
            
        }

        public void keyTyped(KeyEvent event){
            int code = event.getKeyCode();
            char c = event.getKeyChar();
            if(!good){
                //System.out.println("consume");
                event.consume();
            }else{
                houtput.setText("");
                hpercout.setText("");
                if (i10field != null){
                    i10field.setText("");
                    gfield.setText("");
                    sumfield.setText("");
                    e_field.setText("");
                }
                series.clear();

                
            
                if (marker != null){
                    //System.out.println(marker.getValue());
                    marker = null;
                    marker1 = null;
                    marker2 = null;
                    // System.out.println(marker.getValue());
                    // System.out.println(marker1.getValue());
                    // System.out.println(marker2.getValue());
                }
                if(gmarker != null){
                    gmarker1 = null;
                    gmarker = null;
                }
                
               
            }
            
        }
    
    }

    class color_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String colorEvent = ((JMenuItem) event.getSource()).getText();
            //also set the contnetnpane background of graph_window which is graph_panel
            //set them to cusotm color objects
            //System.out.println(frame.getContentPane().getBackground().toString());
            switch(colorEvent) {
                case "red":

                    frame.getContentPane().setBackground(new Color(242, 177, 149));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }

                    break;

                case "green":
                    
                    frame.getContentPane().setBackground( new Color(191, 227, 180));

                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }


                    break;

                case "blue":
                    
                    frame.getContentPane().setBackground(new Color(171, 215, 235));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }

                    //System.out.println(rgb_complement_color());
                    break;

                case "white":
                    frame.getContentPane().setBackground(Color.WHITE);
                    houtput.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(new Color(	90, 90, 90), 2));
                    }

                    break;

                case "grey":
                
                    frame.getContentPane().setBackground(defaultcolor);
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }

                    //System.out.println(defaultcolor);
                    break;
                case "purple":
                    //189 182 206
                    frame.getContentPane().setBackground(new Color(189, 182, 206));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }

                    break;
                //R:253, G:217, B:209
                case "pink":
                    //189 182 206
                    frame.getContentPane().setBackground(new Color(253, 217, 209));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }

                    break;
                case "yellow":
                    
                    frame.getContentPane().setBackground(new Color(255, 244, 189));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }

                    break;
                case "brown":
                    
                    frame.getContentPane().setBackground(new Color(196,178,162));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    if (i10field != null){
                        i10field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        gfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        sumfield.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                        e_field.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    }

                    //System.out.println(rgb_complement_color());
                    break;


            }
            //177,156,217
            //rgb complement conversion algorithm for creator window
            if (marker != null && graph_window != null){
                if (colorEvent == "purple"){
                    marker.setPaint(new Color(130, 180, 70));
                    marker1.setPaint(new Color(130, 180, 70));
                    marker2.setPaint(new Color(130, 180, 70));

                }
                //240,255,240
                else if (colorEvent == "green"){
                    marker.setPaint(new Color(218,112,234));
                    marker1.setPaint(new Color(218,112,234));
                    marker2.setPaint(new Color(218,112,234));

                }
                else if (colorEvent == "brown"){
                    //145,162,176
                    marker.setPaint(new Color(145,162,176));
                    marker1.setPaint(new Color(145,162,176));
                    marker2.setPaint(new Color(145,162,176));
                }
                //blue
                else if (colorEvent == "blue"){
                    //235,191,171
                    marker.setPaint(new Color(255,191,181));
                    marker1.setPaint(new Color(255,191,181));
                    marker2.setPaint(new Color(255,191,181));
                }
                //brown
                else if (colorEvent == "yellow"){
                    //177,156,217
                    marker.setPaint(new Color(177,156,217));
                    marker1.setPaint(new Color(177,156,217));
                    marker2.setPaint(new Color(177,156,217));

                }
                else{

                    marker.setPaint(rgb_complement_color());
                    marker1.setPaint(rgb_complement_color());
                    marker2.setPaint(rgb_complement_color());
                }
            }
            if (gmarker != null && g_graph_window != null){
                if (colorEvent == "purple"){
                    gmarker.setPaint(new Color(130, 180, 70));
                    gmarker1.setPaint(new Color(130, 180, 70));
                    

                }
                //240,255,240
                else if (colorEvent == "green"){
                    gmarker.setPaint(new Color(218,112,234));
                    gmarker1.setPaint(new Color(218,112,234));
                    

                }
                else if (colorEvent == "brown"){
                    //145,162,176
                    gmarker.setPaint(new Color(145,162,176));
                    gmarker1.setPaint(new Color(145,162,176));
                    
                }
                //blue
                else if (colorEvent == "blue"){
                    //235,191,171
                    gmarker.setPaint(new Color(255,191,181));
                    gmarker1.setPaint(new Color(255,191,181));
                    
                }
                //brown
                else if (colorEvent == "yellow"){
                    //177,156,217
                    gmarker.setPaint(new Color(177,156,217));
                    gmarker1.setPaint(new Color(177,156,217));
                    

                }
                else{

                    gmarker.setPaint(rgb_complement_color());
                    gmarker1.setPaint(rgb_complement_color());
                    
                }
            }
                
            if (graph_window != null){

                nnn.set_colors(rgb_complement_color(),frame.getContentPane().getBackground());
                notif.setContentPane(nnn);
            }
            if (g_graph_window != null){
                nnn.set_colors(rgb_complement_color(),frame.getContentPane().getBackground());
                notif.setContentPane(nnn);
            }
            if (more_window != null){
                more_panel.set_colors(frame.getContentPane().getBackground(),rgb_complement_color());
                more_window.setContentPane(more_panel);
            }
            //all new windows can be converted accordingly using this function. checks for null
            rgb_complement(n);
            rgb_complement(graph_window);
            rgb_complement(g_graph_window);
            // if (graph_window != null){
            //     ((XYPlot)chart).getPlot().setBackgroundPaint( rgb_complement_color() );
            //     graph_panel.setBackground( rgb_complement_color() );
            //     rgb_complement(graph_window);
            // }
              
              
        }

    }
    //END OF ACTION LISTENERS----------------------------------------------------------------------------------------------------------------
	
    public static void main(String[] args)
	{
        mainframe i = new mainframe();
        
		
	}
}



//TODO::
//add new colors *** ONGOING

// h index alts

//  1******. make another jdialog and display all available alt scores for the data input

// graph the indexes



//potentialluy add google scholar support which will allow me to do more complex
//calulcations invovling more variables such as year, and co authorship






// DEFUNCT--------------------------------------------------------------------------------------------------------------
//a- ********have a button in mainframe to dispatch the window
//          -"see further metrics and how your score comapres to other bibliometric indices"
//      b-have a drop down in the mainframe to dispatch the window
//      
//  2.make a jCOMBOBOX that is in teh mainframe and have a selection there 

//JCOMBO boxes are good for selections that are mutually exclusive

// add h index info tab including the math, the set notation and the algorthitm i used
//add limitations tab
// add tab having a breif description of all other author level bibliometric indices used in the h index alt j dialogue

//graph all graphable indices

//maybe add a normalization thingy with jcombo box and maybe maybe graph normalized citations???