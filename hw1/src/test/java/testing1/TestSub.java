package testing1;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestSub {
    Calculator calculator;

    @DataProvider
    public static Object[][] longExamples() {
        return new Object[][] {
                {4L, 1L, 3L},
                {5L, -5L, 10L},
                {35L, 42L, -7L},
                {155555L, 155555L, 0L},
                {0L, 0L, 0L}
        };
    }

    @DataProvider
    public static Object[][] doubleExamples() {
        return new Object[][] {
                {5.0, 1.0, 4.0},
                {0.3, 0.2, 0.1},
                {0.1, -0.1, 0.2},
                {20000000000.0, 10000000000.0, 10000000000.0},
                {0.000000003, 0.000000002, 0.000000001},
                {0.000000001, 0.000000001, 0.0},
                {1.0, 0.000001, 0.999999},
                {0.0, 0.0, 0.0}
        };
    }

    @BeforeClass
    public void initCalculator() {
        calculator = new Calculator();
    }

    @Test(testName = "Sub Long",
            dataProvider = "longExamples"
    )
    public void testSubLong(Long a, Long b, Long res) {
        Assert.assertEquals(calculator.sub(a, b), res.longValue());
    }

    @Test(testName = "Sub Double",
            dataProvider = "doubleExamples")
    public void testSubDouble(Double a, Double b, Double res) {
        Assert.assertEquals(calculator.sub(a, b), res, 0.0000000000000001);
    }
}
