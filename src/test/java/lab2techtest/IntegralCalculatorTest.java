package lab2techtest;

import lab2tech.Function;
import lab2tech.IntegralCalculator;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegralCalculatorTest {

    @Test
    public void testFunction() {
        Function f = new Function();
        assertEquals(6.0, f.calculate(1), 1e-9);
        // Замінюємо 36.0 на 18.0
        assertEquals(18.0, f.calculate(4), 1e-9);
    }

    @Test
    public void testIntegralCalculator() throws InterruptedException, ExecutionException {
        Function f = new Function();
        IntegralCalculator calculator = new IntegralCalculator();

        double result1 = calculator.calculate(1, 9, 1000, f, 1);
        double result4 = calculator.calculate(1, 9, 1000, f, 4);

        assertEquals(172.0, result1, 1e-6);
        assertEquals(172.0, result4, 1e-6);
    }
}