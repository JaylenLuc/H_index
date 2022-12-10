package H_index_calc;

import java.util.*;
import java.util.stream.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;  
import java.awt.event.*;  
import java.awt.image.*;
//MOST IMPORTANT LAYOUT FEATURES: gridbaglayout, gridbagconstraints, insets
//POSSIBLE FEATURES THEREAFTER:
//questions tab that includes common questions in subtabs that open another tab
//h index limitations tab
//calculate percentile
//calculate other index variants or similar indexes
//normalization based on field
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class mainframe extends JFrame{

    private JFrame frame;
    private JDialog n;//creator dialog
	private final Color defaultcolor = new Color(238,238,238);
    private WindowEvent creatorwindow;
    private JTextArea textArea;
    private JPanel panel;
    private JTextField houtput;
    private GridBagConstraints gbc;
    
    public mainframe(){
        
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
        houtput = new JTextField(3);
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


    
	//ACTION LISTENERS----------------------------------------------------------------------------------------------------------------

    class exit_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }
    class Hbutton_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int res = calculator.h_bomb(textArea.getText());
            if (res != -1){
                houtput.setText(String.valueOf(res));

            }
            
        }
    }
    class creator_listener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            System.out.println(n);
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
                    if (System.getProperty("os.name").contains("Mac")){
                        file = new File(System.getProperty("user.dir") + "/jaylen");
                    }
                    BufferedImage mePicture = ImageIO.read(file);
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
                System.out.println(pos);
                
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
            }
            
        }
    
    }

    class color_listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String colorEvent = ((JMenuItem) event.getSource()).getText();
            
            switch(colorEvent) {
                case "red":

                    frame.getContentPane().setBackground(new Color(242, 177, 149));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;

                case "green":
                    
                    frame.getContentPane().setBackground( new Color(191, 227, 180));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;

                case "blue":
                    frame.getContentPane().setBackground(new Color(171, 215, 235));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;

                case "white":
                    frame.getContentPane().setBackground(Color.WHITE);
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;

                case "grey":
                
                    frame.getContentPane().setBackground(defaultcolor);
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    //System.out.println(defaultcolor);
                    break;
                case "purple":
                    //189 182 206
                    frame.getContentPane().setBackground(new Color(189, 182, 206));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;
                //R:253, G:217, B:209
                case "pink":
                    //189 182 206
                    frame.getContentPane().setBackground(new Color(253, 217, 209));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;
                case "yellow":

                    frame.getContentPane().setBackground(new Color(253, 253, 150));
                    houtput.setBorder(BorderFactory.createLineBorder(rgb_complement_color(), 2));
                    break;
                

            }
            //rgb complement conversion algorithm
            rgb_complement(n);
              
              
        }

    }
    //----------------------------------------------------------------------------------------------------------------
	
    public static void main(String[] args)
	{
        mainframe i = new mainframe();
        
		
	}
}
