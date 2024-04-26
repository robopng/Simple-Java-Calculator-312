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
 * @modifiedby Liam Wilson
 * @modifiedby Celeste Payton
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

import static java.awt.event.KeyEvent.*;

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

    /*
     * The following four variables are user defined, and did not exist prior to
     * project adoption. The intention was to replace lengthy, difficult-to-read button
     * declarations and initializations with a more comprehensive and versatile
     * implementation. With these commands, to add a button one only needs to define
     * its text, position it on the UI, and add a listener consequence. Before, it was
     * required to declare the button here, initialize it in the constructor, add listeners
     * and set a font in method init, position it, and add a listener consequence.
     *
     * This method does have drawbacks, though, as now it becomes necessary to keep track
     * of the specific text used for each button when they are changed or added. This is not
     * a large issue, since most calculator functions use standard text, but it can sometimes
     * be distracting to have to return to the top of the file to double-check a button like
     * +/- before going back down to the bottom to finish adding the listener consequence.
     */
    private static final String[] commands = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "+", "-", "*", "/", "=", ".", "+/-",
            "sqrt", "x^2", "x^y", "1/x",
            "sin", "cos", "tan", "log10", "ln", "logn",
            "x%", "|x|", "bin(x)", "mod",
            "Graph", "Key", "C", "A",
    };
    private static final String[] gCommands = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "Reset", ",", ".", "Go", "Scatter", "Line", "Add", "Series",
            "Key"
    };
    private final Map<String, JButton> buttons = new HashMap<>();
    private final Map<String, JButton> gButtons = new HashMap<>();

    private final Calculator calc;
    /*
     * Apart from cleaning up the codebase and fixing a few issues, the second
     * primary goal of this project was to add a graphing calculator, or some way to graph
     * coordinates. More on our efforts here can be found in file GraphCalculator.
     *
     * Any variable that starts with g, or contains the word graph, did not exist before
     * project adoption.
     */
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

        /*
         * These two for loops are by far the largest contribution to this method, and the most
         * substantial change from the state it was in prior to project adoption. They replace
         * dozens of lines of code.
         */
        for (String command : commands)
            buttons.put(command, new JButton(command));
        for (String command : gCommands)
            gButtons.put(command, new JButton(command));

        // calculator initialization segment
        calc = new Calculator();
        gCalc = new GraphCalculator();
    }

    /**
     * Initialize the UI wrapping calculator objects, display panel components, and begin listening
     * for user input.
     * ///////////////
     * This method was significantly altered from the state it was in before this project was adopted.
     * The primary purpose of this method was (and is) to initialize the panel that contains the
     * JButtons wrapping calculator functionality, and so the main change is explained in method
     * getPanelSub, itself a convenient way of building a panel. Other than that, two for-each loops
     * replace dozens of lines of post-initialization setup.
     */
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
                new JButton[]{buttons.get("log10"), buttons.get("ln"), buttons.get("logn")},
                new JButton[]{buttons.get("Key")},
                60
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("cos"), buttons.get("sin"), buttons.get("tan")},
                new JButton[]{buttons.get("Graph")},
                60
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("x^2"), buttons.get("sqrt"), buttons.get("x^y")},
                new JButton[]{buttons.get("bin(x)")},
                40
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("x%"), buttons.get("|x|"), buttons.get("1/x"), buttons.get("+/-")},
                new JButton[]{buttons.get("mod")},
                21
        ));
        JPanel t = this.getPanelSub(
                new JButton[]{buttons.get("7"), buttons.get("8"), buttons.get("9")},
                new JButton[]{buttons.get("/")},
                15
        );
        t.add(Box.createHorizontalStrut(44));
        panel.add(t);
        JPanel t1 = this.getPanelSub(
                new JButton[]{buttons.get("4"), buttons.get("5"), buttons.get("6")},
                new JButton[]{buttons.get("*")},
                15
        );
        t1.add(Box.createHorizontalStrut(44));
        panel.add(t1);
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("1"), buttons.get("2"), buttons.get("3")},
                new JButton[]{buttons.get("+"), buttons.get("A")},
                15
        ));
        panel.add(this.getPanelSub(
                new JButton[]{buttons.get("."), buttons.get("0"), buttons.get("=")},
                new JButton[]{buttons.get("-"), buttons.get("C")},
                15
        ));

        // panel creation for graphing calculator panel
        gPanel.add(Box.createHorizontalStrut(100));
        JPanel gTextSub = new JPanel(new FlowLayout());
        gTextSub.add(gText);
        gPanel.add(gTextSub);
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("1"), gButtons.get("2"), gButtons.get("3")},
                new JButton[]{gButtons.get("Key")},
                163
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("4"), gButtons.get("5"), gButtons.get("6")},
                new JButton[]{gButtons.get("Scatter"), gButtons.get("Line")},
                45
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("7"), gButtons.get("8"), gButtons.get("9")},
                new JButton[]{gButtons.get("Add"), gButtons.get("Series")},
                65
        ));
        gPanel.add(this.getPanelSub(
                new JButton[]{gButtons.get("."), gButtons.get("0"), gButtons.get(",")},
                new JButton[]{gButtons.get("Go"), gButtons.get("Reset")},
                85
        ));

        frame.add(panel);
        frame.setVisible(true);

        gFrame.add(gPanel);
    }

    /**
     * Given a set of buttons, add those buttons to a new JPanel, with optional spacing.
     * getPanelSub is intended to provide an easy way to format panel initialization
     * for calculator UI components by replacing a commonly repeated pattern of initialization
     * wherein the overall panel of a component was assembled piecemeal by sub-panels, which
     * themselves were created in a repetitive manner.
     * ///////////////////////////////////////////////
     * This is an entirely new method, no part of which existed before project adoption.
     * However, the process for initializing the temporary panel is based off of the pre-existing
     * pattern that used to be in the constructor:
     * subPanel1.add(button...)
     * subPanel1.add(button...)
     * subPanel1.add(spacing...)
     * etc...
     * panel.add(subPanel1)
     * Most of the time, there is no need for any spacing beyond separating parts of a row of
     * buttons (creative concerns notwithstanding), so this method is not overloaded to accommodate
     * any alternate form of construction.
     *
     * @param buttons1 the first set of buttons to add to the panel
     * @param buttons2 the second set of buttons to add to the panel
     * @param width    the width between the last button in the first set and the first button
     *                 in the second set
     * @return the fully assembled JPanel
     */
    private JPanel getPanelSub(JButton[] buttons1, JButton[] buttons2, int width) {
        JPanel panelSub = new JPanel(new FlowLayout());
        for (JButton button : buttons1) {
            panelSub.add(button);
        }
        if (width > 0) panelSub.add(Box.createHorizontalStrut(width));
        for (JButton button : buttons2) {
            panelSub.add(button);
        }
        return panelSub;
    }

    /**
     * Switch a code and process that code's consequence.
     * Codes are string representations of calculator commands, and are static to new UI objects.
     * //////////////////////////////////////////////////////////////////////////////////////////
     * This method is a replacement for a previously defined pattern before project adoption.
     * The switch statement contained below used to be an if chain in method actionPerformed.
     * Also, much of the content of the switch's cases was incompatible with newly defined structures
     * and was changed appropriately.
     *
     * @param code the code to be switched.
     */
    public void check(String code) {
        // pattern used in more complex input situations where criteria need to be met before continuing
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
            case "logn":
                writer(calc.calculateBi(Calculator.OperatorModes.logbasen, reader()));
                break;
            case "log10":
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
            case "mod":
                writer(calc.calculateBi(Calculator.OperatorModes.modulo, reader()));
                break;
            case "=":
                writer(calc.calculateEqual(reader()));
                break;
            case "C":
                text.setText("");
            case "A":
                writer(calc.reset());
                break;
            case "bin(x)":
                parseToBinary();
                break;
            case ".":
                if (!text.getText().contains(".")) {
                    if (gPanel.isShowing()) {
                        gText.replaceSelection(".");
                    }
                    text.replaceSelection(".");
                }
                break;
            case "+/-":
                writer(reader() * -1.0);
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
                new Thread(gCalc::go).start();
                break;
            default:
                break;
        }
    }

    /**
     * Listen for a mouse button click on one of the UI's JButtons.
     * If the button pressed represents a number key, the key is treated as a digit in the curren
     * number (or as the first digit of a new number if there is none currently in the text panel).
     * Otherwise, the text the button represents is passed to method check for special command
     * processing.
     * ///////////
     * This method was significantly altered from the state it was in before this project was adopted.
     * Namely, the if-else chain that used to be here was moved into method check. Also, accommodations
     * were made for number buttons being hit in the graphing calculator component.
     *
     * @param e the event to be processed
     */
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

    /**
     * Listen for a keystroke being lifted when the calculator expects keyboard input.
     * If the key pressed was a number key, the key is treated as a digit in the current number
     * (or the beginning digit of a new number if there is none currently in the text panel).
     * Otherwise, the key is treated as a special command, and the key is passed into method
     * check for processing. Once check has processed the key, it goes through a second switch
     * inside this method to handle special commands from non-alphanumeric keys.
     * /////////////////////////////////////////////////////////////////////////
     * This is an entirely new method, no part of which existed before project adoption.
     * The intention is to allow for certain repetitive clicks to be replaced with fluid typing.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        String c = String.valueOf(e.getKeyChar());
        try {
            // verify that the key was a number key otherwise go to the catch block
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
                case VK_BACK_SPACE:
                    // check if gText or text; make them gText.getText.substr(0, gText.getText.length - 1)
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * NON-STUDENT WRITTEN METHOD
     */
    private void parseToBinary() {
        // currently this cannot handle strings with E in them
        try {
            text.setText(Long.toBinaryString(Long.parseLong(text.getText())));
        } catch (NumberFormatException ex) {
            System.err.println("Error while parse to binary." + ex);
        }
    }

    /**
     * NON-STUDENT WRITTEN METHOD
     */
    public Double reader() {
        return text.getText().isEmpty() ? 0 : Double.parseDouble(text.getText());
    }

    /**
     * NON-STUDENT WRITTEN METHOD
     */
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
