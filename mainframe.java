package H_index_calc;


import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;  
import org.jfree.data.xy.XYDataset;  
import org.jfree.data.xy.XYSeries;  
import org.jfree.data.xy.XYSeriesCollection;  
import java.util.*;
import java.util.stream.*;
import java.awt.geom.*;  
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;
import java.awt.*;  
import java.awt.event.*;  
import java.awt.image.*;
//MOST IMPORTANT LAYOUT FEATURES: gridbaglayout, gridbagconstraints, insets
//POSSIBLE FEATURES THEREAFTER:
//questions tab that includes common questions in subtabs that open another tab
//h index limitations tab
//calculate other index variants or similar indexes
//normalization based on field
//graph that graphs all the points
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.*;  


//were swinging this cuz i dont wanna bother with JavaFx download

public class mainframe extends JFrame{

//semi_utility - functional inner classes----------------------------------------------------------------------------------------------------------------

    class calculator {
        public static Integer[] h_nums;
        public static Map<Integer, Double> percentile;

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
    
                h_nums = Stream.of(ind.split(",")).filter(i -> i.length() < 9 && !i.equals(""))
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
                    return i;
                }
                
            }
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
    public XYSeriesCollection graph_dataset = new XYSeriesCollection();  
    public XYSeries series =  new XYSeries("Publication");  
    public int prev_ = -1;
    public JFreeChart chart;
    public ValueMarker marker1;
    public ValueMarker marker;
    public ValueMarker marker2;
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
        frame.getContentPane().setBackground(new Color(253, 253, 150));
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


        //frame.add(houtput);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

    }
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
    //     r = (Collections.max(color_array) + Collections.min(color_array) - r ) ;
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
        JMenuItem graph = new JMenuItem("Graph Visualizer");

        graph.addActionListener(new graph_listener());
        pink.addActionListener(new color_listener());
        purple.addActionListener(new color_listener());
        yellow.addActionListener(new color_listener());
        grey.addActionListener(new color_listener());
        red.addActionListener(new color_listener());
        green.addActionListener(new color_listener());
        blue.addActionListener(new color_listener());
        white.addActionListener(new color_listener());
        color.add(red); color.add(green); color.add(blue); 
        color.add(white); color.add(grey); color.add(yellow);
        color.add(purple); color.add(pink);
        exitItem.addActionListener(new exit_listener());
        JMenuItem creator = new JMenuItem("the developer");
        creator.addActionListener(new creator_listener());
        setting.add(exitItem);
        setting.add(creator);
        menubar.add(setting);
        menubar.add(color);
        menubar.add(graph);
        
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
        "Raw H-Index Graphed (discrete)",   
        "Research paper count", "Citations", graph_dataset);
        if (graph_panel == null){
            graph_panel = new ChartPanel(chart,500,500,400,400,500,500,
            true,true,true,true,false,true);
            
        }else{
            graph_panel.setChart(chart);
        }

    }
    private void change_data_set(){
        series.clear();
        for (int i = 0; i < calculator.h_nums.length; i++){
            series.add(i+1,calculator.h_nums[i]);
        }
        //series.setSeriesPaint();

    }



    
	//ACTION LISTENERS----------------------------------------------------------------------------------------------------------------

    class exit_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    //listens for h button press and calculates and sets the number into the output box respectively

    class Hbutton_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //System.out.print("HELLO:::   ");
            //System.out.println(calculator.get(1));

            int res = calculator.h_bomb(textArea.getText()); //popualtes the public static variable : h_nums
            double resperc = calculator.h_percent(res);
            if (res != -1 && !textArea.getText().contains("Example:")){
                houtput.setText(String.valueOf(res));
                hpercout.setText(String.valueOf(resperc));
                change_data_set();

            }
            if (textArea.getText().contains("Example:")){
                series.clear();
            }
            if (graph_window != null ){
                //System.out.println(frame.getContentPane().getBackground().toString());
                createGraph();
                graph_window.add(graph_panel);
                //
                if (res != -1){
                    marker = new ValueMarker(res);  // position is the value on the axis
                    marker.setPaint(rgb_complement_color());
                    marker.setStroke(new BasicStroke(1.5f));
                    marker1 = new ValueMarker(res);  // position is the value on the axis
                    marker1.setPaint(rgb_complement_color());
                    marker1.setStroke(new BasicStroke(1.5f));
                    marker.setLabel(String.format("Articles with at least\n %d citations",res));
                    marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                    marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                    marker1.setLabel(String.format("Articles with less than %d citations",res));
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
                        marker.setPaint(new Color(154, 205, 50));
                        marker1.setPaint(new Color(154, 205, 50));
                        marker2.setPaint(new Color(154, 205, 50));
        
                    }
                    if (frame.getContentPane().getBackground().getRed() == 191 &&frame.getContentPane().getBackground().getGreen() == 227 
                        &&frame.getContentPane().getBackground().getBlue() == 180){
                            marker.setPaint(new Color(218,112,214));
                            marker1.setPaint(new Color(218,112,214));
                            marker2.setPaint(new Color(218,112,214));
            
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
           // prev_ = res;
            
        }
    }
    
    class graph_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (graph_window == null){
                graph_window = new JDialog();
                graph_window.setLayout(new GridBagLayout());
                graph_window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                graph_window.setSize(600,600);
                graph_window.setTitle("Raw H_index Cartesian Visualizer");
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
                if (!houtput.getText().isEmpty() ){
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
                            marker.setPaint(new Color(154, 205, 50));
                            marker1.setPaint(new Color(154, 205, 50));
                            marker2.setPaint(new Color(154, 205, 50));
            
                    }
                    //r=191,g=227,b=180
                    if (frame.getContentPane().getBackground().getRed() == 191 &&frame.getContentPane().getBackground().getGreen() == 227 
                    &&frame.getContentPane().getBackground().getBlue() == 180){
                        marker.setPaint(new Color(218,112,214));
                        marker1.setPaint(new Color(218,112,214));
                        marker2.setPaint(new Color(218,112,214));
        
                    }
                    //
                    //marker.setLabel("here"); // see JavaDoc for labels, colors, strokes
                    
                    XYPlot plot =chart.getXYPlot();
                    plot.addRangeMarker(marker1);
                    plot.addDomainMarker(marker);
                    plot.addRangeMarker(marker2);
                    
                }
                
            
            
            
            }
            
            graph_window.setVisible(true);
            

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
                    File file2 = new File("jaylen");
                    
                    //System.out.println("--------");
                    //String url = mainframe.class.getResource("jaylen").getPath();
                    //System.out.println(url);
                
                    file = new File("jaylen");
                    
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
                System.out.println("n");
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
                    if (count > 7){
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
                series.clear();
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
                    break;

                case "green":
                    
                    frame.getContentPane().setBackground( new Color(191, 227, 180));

                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;

                case "blue":
                    frame.getContentPane().setBackground(new Color(171, 215, 235));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;

                case "white":
                    frame.getContentPane().setBackground(Color.WHITE);
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;

                case "grey":
                
                    frame.getContentPane().setBackground(defaultcolor);
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    //System.out.println(defaultcolor);
                    break;
                case "purple":
                    //189 182 206
                    frame.getContentPane().setBackground(new Color(189, 182, 206));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;
                //R:253, G:217, B:209
                case "pink":
                    //189 182 206
                    frame.getContentPane().setBackground(new Color(253, 217, 209));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;
                case "yellow":

                    frame.getContentPane().setBackground(new Color(253, 253, 150));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    hpercout.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;
                

            }
            //rgb complement conversion algorithm for creator window
            if (graph_window != null && marker != null && colorEvent != "purple" ){
                marker.setPaint(rgb_complement_color());
                marker1.setPaint(rgb_complement_color());
                marker2.setPaint(rgb_complement_color());
            }
            if (graph_window != null && marker != null && colorEvent == "purple"){
                marker.setPaint(new Color(154, 205, 50));
                marker1.setPaint(new Color(154, 205, 50));
                marker2.setPaint(new Color(154, 205, 50));

            }
            //240,255,240
            if (graph_window != null && marker != null && colorEvent == "green"){
                marker.setPaint(new Color(218,112,214));
                marker1.setPaint(new Color(218,112,214));
                marker2.setPaint(new Color(218,112,214));

            }
            //all new windows can be converted accordingly using this function. checks for null
            rgb_complement(n);
            rgb_complement(graph_window);
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
//add new colors
//add limitations and information tab
//maybe add a normalization thingy with jcombo box and maybe maybe graph normalized citations???