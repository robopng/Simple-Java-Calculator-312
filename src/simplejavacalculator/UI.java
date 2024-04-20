/**
 * @name        Simple Java Calculator
 * @package     ph.calculator
 * @file        UI.java
 * @author      SORIA Pierre-Henry
 * @email       pierrehs@hotmail.com
 * @link        http://github.com/pH-7
 * @copyright   Copyright Pierre-Henry SORIA, All Rights Reserved.
 * @license     Apache (http://www.apache.org/licenses/LICENSE-2.0)
 * @create      2012-03-30
 *
 * @modifiedby  Achintha Gunasekara
 * @modifiedby  Kydon Chantzaridis
 * @modweb      http://www.achinthagunasekara.com
 * @modemail    contact@achinthagunasekara.com
 * @modemail    kchantza@csd.auth.gr
 */

package simplejavacalculator;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;


public class UI implements ActionListener, KeyListener {
   
   private final JFrame frame;
   private final JFrame gFrame;
   
   private final JPanel panel;
   private final JPanel gPanel;

    private final JTextArea text;
    private final JTextArea gText;

   // convert this into but holding all the buttons?
   // might be nicer for cleanliness of code later on
   private final Calculator calc;
    private final JButton[] but;
    private final JButton butAdd;
    private final JButton butMinus;
    private final JButton butMultiply;
    private final JButton butDivide;
    private final JButton butEqual;
    private final JButton butCancel;
    private final JButton butSquareRoot;
    private final JButton butSquare;
    private final JButton butOneDividedBy;
    private final JButton butCos;
    private final JButton butSin;
    private final JButton butTan;
    private final JButton butxpowerofy;
    private final JButton butlog;
    private final JButton butrate;
    private final JButton butabs;
    private final JButton butBinary;
    private final JButton butln;
    private final JButton butKeyboard;
   private final GraphCalculator gCalc;
    private final JButton[] gBut;
    private final JButton butGraph;
    private final JButton butGraphClose;
    private final JButton butGraphComma;
    private final JButton butGraphGo;
    private final JButton butGraphScatter;
    private final JButton butGraphLine;
    private final JButton butGraphAdd;

   private final String[] buttonValue = {"0", "1", "2", "3", "4", "5", "6",
      "7", "8", "9"};
   

   private final Font font;
   private final Font textFont;
   private final ImageIcon image;

    public UI() throws IOException {
      frame = new JFrame("Calculator PH");
      gFrame = new JFrame("Graphing Component");

      BufferedImageCustom imageReturn = new BufferedImageCustom();
      image = new ImageIcon(imageReturn.imageReturn());      
      
      panel = new JPanel();
      gPanel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

      font = new Font("Consolas",Font.PLAIN, 18);
      
      text = new JTextArea(1, 30);
      gText = new JTextArea(1, 30);
      
      textFont = new Font("Consolas",Font.BOLD, 24);
      
      but = new JButton[10];
      gBut = new JButton[10];
      for (int i = 0; i < 10; i++) {
    		 but[i] = new JButton(String.valueOf(i));
             gBut[i] = new JButton(String.valueOf(i));
      }      
      butAdd = new JButton("+");      
      butMinus = new JButton("-");      
      butMultiply = new JButton("*");      
      butDivide = new JButton("/");      
      butEqual = new JButton("=");      
      butSquareRoot = new JButton("sqrt");      
      butSquare = new JButton("x*x");      
      butOneDividedBy = new JButton("1/x");      
      butCos = new JButton("Cos");      
      butSin = new JButton("Sin");      
      butTan = new JButton("Tan");      
      butln = new JButton("ln");     
      butxpowerofy = new JButton("x^y");      
      butlog = new JButton("log10(x)");      
      butrate = new JButton("x%");      
      butabs = new JButton("abs(x)");      
      butCancel = new JButton("C");      
      butBinary = new JButton("Bin");
      butKeyboard = new JButton("Key");

      butGraph = new JButton("Graph");
      butGraphScatter = new JButton("Scatter");
      butGraphLine = new JButton("Line");
      butGraphComma = new JButton(",");
      butGraphAdd = new JButton("Add");
      butGraphGo = new JButton("Go");
      butGraphClose = new JButton("Close");

      calc = new Calculator();
      gCalc = new GraphCalculator();
   }
   
   public void init() {      
        frame.setSize(450, 450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(image.getImage());
        // for consistency across the two panels
        gFrame.setSize(450, 450);
        gFrame.setLocationRelativeTo(null);
        gFrame.setResizable(false);
        gFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gFrame.setIconImage(image.getImage());

        text.setFont(textFont);
        text.setEditable(false);

        for (int i = 0; i < 10; i++) {
         but[i].setFont(font);
        }
        // this is where having an array might reduce the size of the code a lot...
        butAdd.setFont(font);
        butMinus.setFont(font);
        butMultiply.setFont(font);
        butDivide.setFont(font);
        butEqual.setFont(font);
        butSquareRoot.setFont(font);
        butSquare.setFont(font);
        butOneDividedBy.setFont(font);
        butCos.setFont(font);
        butSin.setFont(font);
        butTan.setFont(font);
        butln.setFont(font);
        butxpowerofy.setFont(font);
        butlog.setFont(font);
        butrate.setFont(font);
        butabs.setFont(font);
        butCancel.setFont(font);
        butBinary.setFont(font);
        butKeyboard.setFont(font);
        butGraph.setFont(font);

        // panel creation for main calculator panel
        panel.add(Box.createHorizontalStrut(100));
        JPanel textSub = new JPanel(new FlowLayout());
        textSub.add(text);
        panel.add(textSub);
        // visualization of panel layouts through arrays
        // mainPanelSubs => any panel that goes like X X X   X X
        JButton[][][] mainPanelSubs = new JButton[][][]{
                {{but[1], but[2], but[3]}, {butAdd, butMinus}},
                {{but[4], but[5], but[6]}, {butMultiply, butDivide}},
                {{but[7], but[8], but[9]}, {butEqual, butCancel}},
        };
        // sidePanelSubs => any panel that goes like X X X X X
        JButton[][] sidePanelSubs = new JButton[][]{
                {but[0], butln, butGraph},
                {butSquare, butSquareRoot, butOneDividedBy, butxpowerofy},
                {butCos, butSin, butTan},
                {butlog, butrate, butabs, butBinary, butKeyboard}
        };
        // if greater control over button placement is desired remove these
        // for now they are a fun and easy simplification of the messy code
        for (JButton[][] panelSub : mainPanelSubs){
            panel.add(this.getPanelSub(panelSub[0], panelSub[1]));
        }
        for (JButton[] panelSub : sidePanelSubs){
            panel.add(this.getPanelSub(panelSub));
        }

        // panel creation for graphing component panel
        JButton[][][] gMainPanelSubs = new JButton[][][]{
                {{gBut[1], gBut[2], gBut[3]}, {butGraphScatter, butGraphLine}},
                {{gBut[4], gBut[5], gBut[6]}, {butGraphComma, butGraphAdd }},
                {{gBut[7], gBut[8], gBut[9]}, {butGraphGo, butGraphClose}},
        };
        gPanel.add(Box.createHorizontalStrut(100));
        JPanel gTextSub = new JPanel(new FlowLayout());
        gTextSub.add(gText);
        panel.add(gTextSub);
        for (JButton[][] panelSub : gMainPanelSubs){
            gPanel.add(this.getPanelSub(panelSub[0], panelSub[1]));
        }

        // can we get something like this for all the buttons?
        for (JButton button : but){
            button.addActionListener(this);
        }
        butAdd.addActionListener(this);
        butMinus.addActionListener(this);
        butMultiply.addActionListener(this);
        butDivide.addActionListener(this);
        butSquare.addActionListener(this);
        butSquareRoot.addActionListener(this);
        butOneDividedBy.addActionListener(this);
        butCos.addActionListener(this);
        butSin.addActionListener(this);
        butTan.addActionListener(this);
        butln.addActionListener(this);
        butxpowerofy.addActionListener(this);
        butlog.addActionListener(this);
        butrate.addActionListener(this);
        butabs.addActionListener(this);
        butBinary.addActionListener(this);
        butKeyboard.addKeyListener(this);
        butGraph.addActionListener(this);

        butEqual.addActionListener(this);
        butCancel.addActionListener(this);

        frame.add(panel);
        frame.setVisible(true);

        gFrame.add(gPanel);
   }

   private JPanel getPanelSub(JButton[] buttons){
        JPanel panelSub = new JPanel(new FlowLayout());
        for (JButton button : buttons){
            panelSub.add(button);
        }
        return panelSub;
   }

   private JPanel getPanelSub(JButton[] firstSegButtons, JButton[] secondSegButtons){
        int panelSeparatorWidth = 15;
        JPanel panelSub = new JPanel(new FlowLayout());
        for (JButton button : firstSegButtons){
            panelSub.add(button);
        }
        panelSub.add(Box.createHorizontalStrut(panelSeparatorWidth));
        for (JButton button : secondSegButtons){
            panelSub.add(button);
        }
        return panelSub;
    }

   @Override
   public void actionPerformed(ActionEvent e) {
      final Object source = e.getSource();
      Double checkNum = null;

      for (int i = 0; i < 10; i++) {
         if (source == but[i]) {
            text.replaceSelection(buttonValue[i]);
            return;
         }
      }

    
      try {
         checkNum = Double.parseDouble(text.getText());
      } catch(NumberFormatException ignored) {

      }

      if (checkNum != null || source == butCancel) {
         if (source == butAdd) {
            writer(calc.calculateBi(Calculator.BiOperatorModes.add, reader()));
            text.replaceSelection(butAdd.getText());
         }

         if (source == butMinus) {
            writer(calc.calculateBi(Calculator.BiOperatorModes.minus, reader()));
            text.replaceSelection(butMinus.getText());
         }

         if (source == butMultiply) {
            writer(calc.calculateBi(Calculator.BiOperatorModes.multiply, reader()));
            text.replaceSelection(butMultiply.getText());
         }

         if (source == butDivide) {
            writer(calc.calculateBi(Calculator.BiOperatorModes.divide, reader()));
            text.replaceSelection(butDivide.getText());
         }
         
         if (source == butxpowerofy) {
            writer(calc.calculateBi(Calculator.BiOperatorModes.xpowerofy, reader()));
         }

         if (source == butSquare) {
            writer(calc.calculateMono(Calculator.MonoOperatorModes.square, reader()));
         }

         if (source == butSquareRoot)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.squareRoot, reader()));

         if (source == butOneDividedBy)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.oneDividedBy, reader()));

         if (source == butCos)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.cos, reader()));

         if (source == butSin)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.sin, reader()));

         if (source == butTan)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.tan, reader()));

         if (source == butlog)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.log, reader()));

         if (source == butln)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.ln, reader())); 

         if (source == butrate)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.rate, reader()));

         if (source == butabs)
            writer(calc.calculateMono(Calculator.MonoOperatorModes.abs, reader()));

         if (source == butEqual)
            writer(calc.calculateEqual(reader()));

         if (source == butCancel)
            writer(calc.reset());

         if (source == butBinary)
            parseToBinary();

         if (source == butGraph)
            gFrame.setVisible(true);

      }

      text.selectAll();
   }

    @Override
    public void keyPressed(KeyEvent e) {
        // mandatory implements
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // mandatory implements
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        try {
            text.replaceSelection(buttonValue[Character.getNumericValue(keyChar)]);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            // non-numeric keys will exceed the 0-10 range of buttonValue
        }
    }

    private void parseToBinary() {
      try {
         text.setText(Long.toBinaryString(Long.parseLong(text.getText())));
      } catch (NumberFormatException ex) {
         System.err.println("Error while parse to binary." + ex);
      }
   }
   
   public Double reader() {
       return Double.valueOf(text.getText());
   }
   
   public void writer(final Double num) {
       if (Double.isNaN(num)) {
           text.setText("");
       } else {
           // handle exceedingly small numbers (display MIN or 0?)
           text.setText(Double.toString(num));
       }
   }
}
