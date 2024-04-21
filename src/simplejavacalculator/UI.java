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

import static java.awt.event.KeyEvent.*;

import java.awt.event.KeyListener;
import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UI implements ActionListener, KeyListener {

    private final JFrame frame;
    private final JFrame gFrame;

    private final JPanel panel;
    private final JPanel gPanel;

    private final JTextArea text;
    private final JTextArea gText;

    private static final String[] commands = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "+", "-", "*", "/", "=", "C",
            "sqrt", "x^2", "x^y", "1/x",
            "sin", "cos", "tan", "log", "ln",
            "x%", "|x|", "bin(x)",
            "Graph", "Key"
    };
    private static final String[] gCommands = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "Reset", ",", "Go", "Scatter", "Line", "Add", "Series",
            "Key"
    };
    private final Map<String, JButton> buttons = new HashMap<>();
    private final Map<String, JButton> gButtons = new HashMap<>();

    private final Calculator calc;
    private final GraphCalculator gCalc;

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

        for (String command : commands)
            buttons.put(command, new JButton(command));
        for (String command : gCommands)
            gButtons.put(command, new JButton(command));

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
        gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                45
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("4"), gButtons.get("5"), gButtons.get("6")},
                new JButton[]{gButtons.get(","), gButtons.get("Add"), gButtons.get("Series")},
                15
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("7"), gButtons.get("8"), gButtons.get("9")},
                new JButton[]{gButtons.get("Go"), gButtons.get("Reset"), gButtons.get("Key")},
                15
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("0")},
                new JButton[]{},
                232
        ));

        frame.add(panel);
        frame.setVisible(true);

        gFrame.add(gPanel);
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

    public void check(String code) {
        // used in more complex input situations where criteria need to be met before continuing
        Pattern pattern;
        switch (code) {
            case "+":
                writer(calc.calculateBi(Calculator.OperatorModes.add, reader()));
                text.replaceSelection(buttons.get("+").getText());
                break;
            case "-":
                writer(calc.calculateBi(Calculator.OperatorModes.minus, reader()));
                text.replaceSelection(buttons.get("-").getText());
                break;
            case "*":
                writer(calc.calculateBi(Calculator.OperatorModes.multiply, reader()));
                text.replaceSelection(buttons.get("*").getText());
                break;
            case "/":
                writer(calc.calculateBi(Calculator.OperatorModes.divide, reader()));
                text.replaceSelection(buttons.get("/").getText());
                break;
            case "x^y":
                writer(calc.calculateBi(Calculator.OperatorModes.xpowerofy, reader()));
                break;
            case "x^2":
                writer(calc.calculate(Calculator.OperatorModes.square, reader()));
                break;
            case "sqrt":
                writer(calc.calculate(Calculator.OperatorModes.squareRoot, reader()));
                break;
            case "1/x":
                writer(calc.calculate(Calculator.OperatorModes.oneDividedBy, reader()));
                break;
            case "sin":
                writer(calc.calculate(Calculator.OperatorModes.sin, reader()));
                break;
            case "cos":
                writer(calc.calculate(Calculator.OperatorModes.cos, reader()));
                break;
            case "tan":
                writer(calc.calculate(Calculator.OperatorModes.tan, reader()));
                break;
            case "log":
                writer(calc.calculate(Calculator.OperatorModes.log, reader()));
                break;
            case "ln":
                writer(calc.calculate(Calculator.OperatorModes.ln, reader()));
                break;
            case "x%":
                writer(calc.calculate(Calculator.OperatorModes.rate, reader()));
                break;
            case "|x|":
                writer(calc.calculate(Calculator.OperatorModes.abs, reader()));
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
            // graphing calculator conditions
            case "Graph":
                gFrame.setVisible(true);
                gFrame.setAlwaysOnTop(true);
                break;
            case "Reset":
                gCalc.reset();
                break;
            case ",":
                pattern = Pattern.compile("\\d+");
                if (pattern.matcher(gText.getText()).find())
                    gText.replaceSelection(",");
                break;
            case "Add":
                // verify that there is a pair of coordinates in the screen and then load
                pattern = Pattern.compile("\\d+,\\d+");
                if (pattern.matcher(gText.getText()).find()) {
                    gCalc.load(gText.getText());
                    writer(Double.NaN);
                }
                break;
            case "Line":
                gCalc.setLine();
                break;
            case "Scatter":
                gCalc.setScatter();
                break;
            case "Series":
                gCalc.commitSeries();
                break;
            case "Go":
                Thread t = new Thread(gCalc::go);
                t.start();
                break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Casting down is bad for the environment! Can this be changed?
        if (!(e.getSource() instanceof JButton source)) return;

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

        check(source.getText());

        text.selectAll();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // mandatory implements
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // mandatory include
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String c = String.valueOf(e.getKeyChar());
        try {
            // verify that the key was a number key
            Double.parseDouble(c);
            if (gPanel.isShowing())
                gText.replaceSelection(c);
            else
                text.replaceSelection(c);
        } catch (Exception ignored) {
            // making program run both switches is better than reusing cases in this one
            check(c);
            // this is a very small switch, obviously, but I'm leaving it like this for
            // future expansion with more special command keys
            switch (e.getKeyCode()) {
                case VK_ENTER:
                    if (gText.getText().isEmpty()) check("Go");
                    else check("Add");
                    break;
                default:
                    break;
            }
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
        return text.getText().isEmpty() ? 0 : Double.parseDouble(text.getText());
    }

    public void writer(final Double num) {
        if (Double.isNaN(num)) {
            text.setText("");
            gText.setText("");
        } else {
            // handle exceedingly small numbers (display MIN or 0?)
            text.setText(Double.toString(num));
        }
    }
}
