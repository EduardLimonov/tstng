package testing1;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestDiv {
    Calculator calculator;

    @BeforeClass
    public void initCalculator() {
        calculator = new Calculator();
    }

    @DataProvider
    public static Object[][] longExamples() {
        return new Object[][] {
                {5L, 5L, 1L},
                {100L, 25L, 4L},
                {-20L, 7L, -2L},
                {40L, 9L, 4L},
                {-40L, -9L, 4L},
                {103L, 1L, 103L},
                {0L, 10L, 0L},
                {1L, -5L, 0L}
        };
    }

    @DataProvider
    public static Object[][] doubleExamples() {
        return new Object[][] {
                {0.04, 0.2, 0.2},
                {6.0, 4.0, 1.5},
                {-0.0, 5.0, 0.0},
                {-5.0, 80.0, -0.0625},
                {0.05, 0.001, 50.0},
                {60.0, 1200.0, 0.05},
                {0.0, 0.0, Double.NaN},
                {10.0, 0.0, Double.POSITIVE_INFINITY},
                {-10.0, 0.0, Double.NEGATIVE_INFINITY}
        };
    }

    @DataProvider
    public static Object[][] zeroDoubleExamples() {
        return new Object[][] {
                {1L, 0L},
                {0L, 0L}
        };
    }

    @Test(testName = "Divide  Long",
            dataProvider = "longExamples")
    public void testDivLong(Long a, Long b, Long res) {
        Assert.assertEquals(calculator.mult(a, b), res.longValue());
    }

    @Test(testName = "Divide  Double",
            dataProvider = "doubleExamples")
    public void testDivDouble(Double a, Double b, Double res) {
        Assert.assertEquals(calculator.mult(a, b), res, 0.000000000000000000000001);
    }

    @Test(testName = "Zero Division Long",
            dataProvider = "zeroDoubleExamples",
            expectedExceptions = {NumberFormatException.class})
    public void testDiv0(Long a, Long b) {
        calculator.div(a, b);
    }

}
