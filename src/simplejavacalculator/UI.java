/**
 * @name Simple Java Calculator
 * @package ph.calculator
 * @file UI.java
 * @author SORIA Pierre-Henry
 * @email pierrehs@hotmail.com
 * @link http://github.com/pH-7
 * @copyright Copyright Pierre-Henry SORIA, All Rights Reserved.
 * @license Apache (http://www.apache.org/licenses/LICENSE-2.0)
 * @create 2012-03-30
 * @modifiedby Achintha Gunasekara
 * @modifiedby Kydon Chantzaridis
 * @modweb http://www.achinthagunasekara.com
 * @modemail contact@achinthagunasekara.com
 * @modemail kchantza@csd.auth.gr
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
import java.util.HashMap;
import java.util.Map;


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
    private static final String[] commands = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "+", "-", "*", "/", "=", "C",
            "sqrt", "x^2", "x^y", "1/x",
            "sin", "cos", "tan", "log", "ln",
            "x%", "|x|", "bin(x)",
            "Graph", "Key"
    };
    private final Map<String, JButton> buttons = new HashMap<>() {
        {
            for (String command : commands)
                put(command, new JButton(command));
        }
    };
    private final GraphCalculator gCalc;
    private static final String[] gCommands = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "Close", ",", "Go", "Scatter", "Line", "Add",
            "Key"
    };
    private final Map<String, JButton> gButtons = new HashMap<>() {
        {
            for (String command : gCommands)
                put(command, new JButton(command));
        }
    };

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

        font = new Font("Consolas", Font.PLAIN, 18);
        textFont = new Font("Consolas", Font.BOLD, 24);

        text = new JTextArea(1, 30);
        gText = new JTextArea(1, 30);

        // calculator initialization segment
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
        gText.setFont(textFont);
        gText.setEditable(false);

        for (JButton button : buttons.values()) {
            button.setFont(font);
            button.addActionListener(this);
        }
        for (JButton button : gButtons.values()) {
            button.setFont(font);
            button.addActionListener(this);
        }
        buttons.get("Key").addKeyListener(this);
        gButtons.get("Key").addKeyListener(this);

        // panel creation for main calculator panel
        panel.add(Box.createHorizontalStrut(100));
        JPanel textSub = new JPanel(new FlowLayout());
        textSub.add(text);
        panel.add(textSub);
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("1"), buttons.get("2"), buttons.get("3")},
                new JButton[]{buttons.get("+"), buttons.get("-")},
                15
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("4"), buttons.get("5"), buttons.get("6")},
                new JButton[]{buttons.get("*"), buttons.get("/")},
                15
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("7"), buttons.get("8"), buttons.get("9")},
                new JButton[]{buttons.get("="), buttons.get("C")},
                15
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("0"), buttons.get("ln"), buttons.get("Graph")}
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("x^2"), buttons.get("sqrt"), buttons.get("1/x"), buttons.get("x^y")}
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("cos"), buttons.get("sin"), buttons.get("tan")}
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("log"), buttons.get("x%"), buttons.get("|x|"), buttons.get("bin(x)"), buttons.get("Key")}
        ));

        gPanel.add(Box.createHorizontalStrut(100));
        JPanel gTextSub = new JPanel(new FlowLayout());
        gTextSub.add(gText);
        gPanel.add(gTextSub);
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("1"), gButtons.get("2"), gButtons.get("3")},
                new JButton[]{gButtons.get("Scatter"), gButtons.get("Line")},
                15
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("4"), gButtons.get("5"), gButtons.get("6")},
                new JButton[]{gButtons.get(","), gButtons.get("Add")},
                15
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("7"), gButtons.get("8"), gButtons.get("9")},
                new JButton[]{gButtons.get("Go"), gButtons.get("Close"), gButtons.get("Key")},
                15
        ));

        frame.add(panel);
        frame.setVisible(true);

        gFrame.add(gPanel);
        gFrame.setVisible(true);
    }

    private JPanel getPanelSub(JButton[] buttons) {
        JPanel panelSub = new JPanel(new FlowLayout());
        for (JButton button : buttons) {
            panelSub.add(button);
        }
        return panelSub;
    }

    private JPanel getPanelSub(JButton[] buttons1, JButton[] buttons2, int width) {
        JPanel panelSub = new JPanel(new FlowLayout());
        for (JButton button : buttons1) {
            panelSub.add(button);
        }
        panelSub.add(Box.createHorizontalStrut(width));
        for (JButton button : buttons2) {
            panelSub.add(button);
        }
        return panelSub;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Casting down is bad for the environment! Can this be changed?
        final JButton source = (JButton) e.getSource();
        Double checkNum = null;
        try {
            checkNum = Double.parseDouble(text.getText());
        } catch (NumberFormatException ignored) {

        }

        for (int i = 0; i < 10; i++) {
            if (source == buttons.get(String.valueOf(i))) {
                text.replaceSelection(String.valueOf(i));
                return;
            }
            if (source == gButtons.get(String.valueOf(i))) {
                gText.replaceSelection(String.valueOf(i));
                return;
            }
        }

        if (checkNum != null || source == buttons.get("C")) {
            switch (source.getText()) {
                case "+":
                    writer(calc.calculateBi(Calculator.BiOperatorModes.add, reader()));
                    text.replaceSelection(buttons.get("+").getText());
                    break;
                case "-":
                    writer(calc.calculateBi(Calculator.BiOperatorModes.minus, reader()));
                    text.replaceSelection(buttons.get("-").getText());
                    break;
                case "*":
                    writer(calc.calculateBi(Calculator.BiOperatorModes.multiply, reader()));
                    text.replaceSelection(buttons.get("*").getText());
                    break;
                case "/":
                    writer(calc.calculateBi(Calculator.BiOperatorModes.divide, reader()));
                    text.replaceSelection(buttons.get("/").getText());
                    break;
                case "x^y":
                    writer(calc.calculateBi(Calculator.BiOperatorModes.xpowerofy, reader()));
                    break;
                case "x^2":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.square, reader()));
                    break;
                case "sqrt":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.squareRoot, reader()));
                    break;
                case "1/x":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.oneDividedBy, reader()));
                    break;
                case "sin":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.sin, reader()));
                    break;
                case "cos":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.cos, reader()));
                    break;
                case "tan":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.tan, reader()));
                    break;
                case "log":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.log, reader()));
                    break;
                case "ln":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.ln, reader()));
                    break;
                case "x%":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.rate, reader()));
                    break;
                case "|x|":
                    writer(calc.calculateMono(Calculator.MonoOperatorModes.abs, reader()));
                    break;
                case "=":
                    writer(calc.calculateEqual(reader()));
                    break;
                case "C":
                    writer(calc.reset());
                    break;
                case "bin(x)":
                    parseToBinary();
                    break;
                case "Graph":
                    gFrame.setVisible(true);
                    gFrame.setAlwaysOnTop(true);
                    break;
                case "Close":
                    gFrame.setVisible(false);
                    gFrame.setAlwaysOnTop(false);
                    break;
                default:
                    break;

            }
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
        try {
            text.replaceSelection(String.valueOf(e.getKeyChar()));
        } catch (ArrayIndexOutOfBoundsException ignored) {
            // non-numeric keys ignored for now; can add greater functionality, though
        }
    }

    private void parseToBinary() {
        // currently this cannot handle strings with E in them
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
