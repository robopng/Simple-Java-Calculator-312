package simplejavacalculatorTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Double.NaN;


import simplejavacalculator.Calculator;

class CalculatorTest {

    @Test
    void calculateBiNormalTest() {
        Calculator calculator = new Calculator();
        calculator.calculateBi(Calculator.OperatorModes.normal, 2.0);
        Assertions.assertEquals(NaN, calculator.calculateBi(Calculator.OperatorModes.normal, 3.0));
    }

    @Test
    void calculateBiAddTest() {
        Calculator calculator = new Calculator();
        calculator.calculateBi(Calculator.OperatorModes.add, 3.0);
        Assertions.assertEquals(5.5, calculator.calculateBi(Calculator.OperatorModes.normal, 2.5));
    }

    @Test
    void calculateBiMinusTest() {
        Calculator calculator = new Calculator();
        calculator.calculateBi(Calculator.OperatorModes.minus, 3.1415);
        Assertions.assertEquals(2.0415, calculator.calculateBi(Calculator.OperatorModes.normal, 1.1));
    }

    @Test
    void calculateBiMultiplyTest() {
        Calculator calculator = new Calculator();
        calculator.calculateBi(Calculator.OperatorModes.multiply, 3.2);
        Assertions.assertEquals(6.4, calculator.calculateBi(Calculator.OperatorModes.normal, 2.0));
    }

    @Test
    void calculateBiDivideTest() {
        Calculator calculator = new Calculator();
        calculator.calculateBi(Calculator.OperatorModes.divide, 6.4);
        Assertions.assertEquals(3.2, calculator.calculateBi(Calculator.OperatorModes.normal, 2.0));
    }

    @Test
    void calculateEqualTest() {
        Calculator calculator = new Calculator();
        calculator.calculateBi(Calculator.OperatorModes.add, 6.4);
        calculator.calculateBi(Calculator.OperatorModes.add, 2.0);
        Assertions.assertEquals(11.4, calculator.calculateEqual(3.0));
    }

    @Test
    void resetTest() {
        Calculator calculator = new Calculator();
        calculator.calculateBi(Calculator.OperatorModes.add, 6.4);
        Assertions.assertEquals(8.4, calculator.calculateBi(Calculator.OperatorModes.add, 2.0));
        Assertions.assertEquals(NaN, calculator.reset());
    }

    @Test
    void calculateSquareTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(9.0, calculator.calculate(Calculator.OperatorModes.square, 3.0));
    }

    @Test
    void calculateSquareRootTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(5.0, calculator.calculate(Calculator.OperatorModes.squareRoot, 25.0));
    }

    @Test
    void calculateOneDividedByTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(0.10, calculator.calculate(Calculator.OperatorModes.oneDividedBy, 10.0));
    }

    @Test
    void calculateSinTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(0.5, calculator.calculate(Calculator.OperatorModes.sin, java.lang.Math.PI / 6), 0.0000000001);
    }

    @Test
    void calculateCosTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(0.5, calculator.calculate(Calculator.OperatorModes.cos, java.lang.Math.PI / 3), 0.0000000001);
    }

    @Test
    void calculateTanTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(1.0, calculator.calculate(Calculator.OperatorModes.tan, java.lang.Math.PI / 4), 0.0000000001);
    }

    @Test
    void calculateLogTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(2.0, calculator.calculate(Calculator.OperatorModes.log, 100.0));
    }

    @Test
    void calculateRateTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(.75, calculator.calculate(Calculator.OperatorModes.rate, 75.0));
    }

    @Test
    void calculateAbsTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(3.0, calculator.calculate(Calculator.OperatorModes.abs, -3.0));
        Assertions.assertEquals(3.0, calculator.calculate(Calculator.OperatorModes.abs, 3.0));
    }

}
