package calc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdvancedCalculatorTest {
    private AdvancedCalculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new AdvancedCalculator();
    }
    
    @Test
    void testBasicOperations() {
        assertEquals(5.0, calculator.add(2.0, 3.0), 0.001);
        assertEquals(1.0, calculator.subtract(4.0, 3.0), 0.001);
        assertEquals(6.0, calculator.multiply(2.0, 3.0), 0.001);
        assertEquals(2.0, calculator.divide(6.0, 3.0), 0.001);
    }
    
    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(5.0, 0.0));
    }
    
    @Test
    void testScientificOperations() {
        assertEquals(25.0, calculator.power(5.0, 2.0), 0.001);
        assertEquals(5.0, calculator.squareRoot(25.0), 0.001);
        assertEquals(120.0, calculator.factorial(5), 0.001);
        assertEquals(2.0, calculator.logarithm(100.0), 0.001);
    }
    
    @Test
    void testTrigonometricOperations() {
        assertEquals(0.5, calculator.sin(30.0), 0.001);
        assertEquals(0.866, calculator.cos(30.0), 0.001);
        assertEquals(1.0, calculator.tan(45.0), 0.001);
        assertEquals(30.0, calculator.arcsin(0.5), 0.001);
    }
    
    @Test
    void testMemoryOperations() {
        calculator.memoryStore(42.0);
        assertEquals(42.0, calculator.memoryRecall(), 0.001);
        
        calculator.memoryAdd(8.0);
        assertEquals(50.0, calculator.memoryRecall(), 0.001);
        
        calculator.memoryClear();
        assertEquals(0.0, calculator.memoryRecall(), 0.001);
    }
    
    @Test
    void testHistory() {
        calculator.add(2, 3);
        calculator.multiply(4, 5);
        
        assertEquals(2, calculator.getHistory().size());
        assertTrue(calculator.getHistory().get(0).contains("2.0 + 3.0 = 5.0"));
    }
}