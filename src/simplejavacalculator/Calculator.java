/**
 * @name Simple Java Calculator
 * @package ph.calculator
 * @file Main.java
 * @author SORIA Pierre-Henry
 * @email pierrehs@hotmail.com
 * @link http://github.com/pH-7
 * @copyright Copyright Pierre-Henry SORIA, All Rights Reserved.
 * @license Apache (http://www.apache.org/licenses/LICENSE-2.0)
 */

package simplejavacalculator;

import static java.lang.Double.NaN;

public class Calculator {

    public enum OperatorModes {
        normal, add, minus, multiply, divide, xpowerofy, //, logbasen, etc.
        square, squareRoot, oneDividedBy, cos, sin, tan, log, rate, abs, ln,
    }

    public enum FunctionModes {
        // for builtin simple mathematic functions
    }

    private Double num1, num2;
    private OperatorModes mode = OperatorModes.normal;

    // I have no idea how this works (there is no documentation nor are there any comments)
    // so I will not fold this into the large switch in calculate()
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

    public Double calculateEqual(Double num) {
        return calculateBi(OperatorModes.normal, num);
    }

    public Double reset() {
        num2 = 0.0;
        num1 = 0.0;
        mode = OperatorModes.normal;

        return NaN;
    }


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
            default:
                throw new Error();
        }
    }
}

