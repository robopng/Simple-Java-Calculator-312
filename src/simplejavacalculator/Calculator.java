/**
 * @name Simple Java Calculator
 * @package ph.calculator
 * @file Main.java
 * @author SORIA Pierre-Henry
 * @email pierrehs@hotmail.com
 * @link http://github.com/pH-7
 * @copyright Copyright Pierre-Henry SORIA, All Rights Reserved.
 * @license Apache (http://www.apache.org/licenses/LICENSE-2.0)
 * @modifiedby Liam Wilson
 * @modifiedby Celeste Payton
 */

package simplejavacalculator;

import static java.lang.Double.NaN;

/**
 * A calculator with functionality for various operating modes and equations.
 * //////////////////////////////////////////////////////////////////////////
 * This class was significantly altered from the state it was in before this project was adopted.
 */
public class Calculator {

    public enum OperatorModes {
        normal, add, minus, multiply, divide, xpowerofy,
        square, squareRoot, oneDividedBy, cos, sin, tan, log, rate, abs, ln,
        modulo, logbasen  // these modes did not exist before project adoption
    }

    /*
     * An example of a planned feature that was unsuccessful.
     */
    public enum FunctionModes {
        // for builtin simple mathematic functions
    }

    private Double num1, num2;
    private OperatorModes mode = OperatorModes.normal;

    /**
     * NON-STUDENT WRITTEN METHOD
     */
    public Double calculateBi(OperatorModes newMode, Double num) {
        if (mode.equals(OperatorModes.normal)) {
            num2 = 0.0;
            num1 = num;
            mode = newMode;
            return NaN;
        } else {
            num2 = num;
            num1 = calculate(mode, num);
            mode = newMode;
            return num1;
        }
    }

    /**
     * NON-STUDENT WRITTEN METHOD
     */
    public Double calculateEqual(Double num) {
        return calculateBi(OperatorModes.normal, num);
    }

    /**
     * NON-STUDENT WRITTEN METHOD
     */
    public Double reset() {
        num2 = 0.0;
        num1 = 0.0;
        mode = OperatorModes.normal;

        return NaN;
    }

    /**
     * Given an input operator mode and number, calculate the result of that operator.
     * This method can either be called with a mono operator mode (log(x), |x|, etc.) or a
     * bi-operator mode (x+y, x*y, etc.) If called with a mono operator mode, then that
     * operator will be applied to the number and the result returned. Otherwise, it will
     * be assumed that method calculateBi called this method, and the parameter num will be
     * ignored - instead, the operator mode will be applied to the calculator instance's
     * stored number variables and that result returned.
     * /////////////////////////////////////////////////
     * This method was significantly altered from the state it was in before this project was adopted.
     * Before, every operator mode was handled in an if-chain. Also, some new modes were added.
     *
     * @param newMode the operator mode to be applied, as a value from this calculator's
     *                OperatorModes enum.
     * @param num the number on which the operator mode will be applied.
     * @return the result of applying the given operator mode on the given number or, if
     *         the operator mode is a bi operator mode, the result of applying that mode
     *         to the calculator's two stored number variables.
     */
    public Double calculate(OperatorModes newMode, Double num) {

        switch(newMode){
            case square:
                return num*num;
            case squareRoot:
                return Math.sqrt(num);
            case oneDividedBy:
                return 1/num;
            case cos:
                return Math.cos(num);
            case sin:
                return Math.sin(num);
            case tan:
                if (num == 0 || num % 180 == 0)
                    return 0.0;
                if (num % 90 == 0)  // unreachable if == 180 so don't worry
                    return 0.0;
                return Math.tan(num);
            case log:
                return Math.log10(num);
            case ln:
                return Math.log(num);
            case rate:
                return num / 100;
            case abs:
                return Math.abs(num);
            // bi operator modes
            case add:
                return num1 + num2;
            case minus:
                return num1 - num2;
            case multiply:
                return num1 * num2;
            case divide:
                return num2 > 0 ? NaN : num1 / num2;
            case xpowerofy:
                return Math.pow(num1, num2);
            // these operator modes did not exist before project adoption
            case modulo:
                return num1 % num2;
            case logbasen:
                return Math.log(num1) / Math.log(num2);  // log_b(x) = ln(x) / ln(b)
            default:
                throw new Error();
        }
    }
}

