package gov.samhsa.bhits.common.util;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniqueValueGeneratorTest {
    private static final String TEST_VALUE = "TEST_VALUE";
    private static final short LIMIT = 3;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGenerateUniqueValue_Success_First_Trial() {
        // Arrange
        AtomicInteger counter = new AtomicInteger(0);

        // Act
        String value = UniqueValueGenerator.generateUniqueValue(
                () -> TEST_VALUE, v -> {
                    counter.incrementAndGet();
                    return true;
                }, LIMIT);

        // Assert
        assertEquals(TEST_VALUE, value);
        assertEquals(1, counter.get());
    }

    @Test
    public void testGenerateUniqueValue_Success_Last_Trial() {
        // Arrange
        AtomicInteger counter = new AtomicInteger(0);

        // Act
        String value = UniqueValueGenerator.generateUniqueValue(
                () -> TEST_VALUE, v -> counter.incrementAndGet() >= LIMIT,
                LIMIT);

        // Assert
        assertEquals(TEST_VALUE, value);
        assertEquals(LIMIT, counter.get());
    }

    @Test
    public void testGenerateUniqueValue_Throws_UniqueValueGeneratorException_Last_Trial1() {
        // Arrange
        AtomicInteger counter = new AtomicInteger(0);
        thrown.expect(UniqueValueGeneratorException.class);

        // Act
        String value = null;
        try {
            value = UniqueValueGenerator.generateUniqueValue(() -> TEST_VALUE,
                    v -> {
                        counter.incrementAndGet();
                        return false;
                    }, LIMIT);
        } catch (Exception e) {
            // Assert
            assertEquals(null, value);
            assertEquals(LIMIT, counter.get());
            throw e;
        }
    }

    @Test
    public void testGenerateUniqueValue_Throws_UniqueValueGeneratorException_Last_Trial2() {
        // Arrange
        AtomicInteger counter = new AtomicInteger(0);
        thrown.expect(UniqueValueGeneratorException.class);

        // Act
        String value = null;
        try {
            value = UniqueValueGenerator.generateUniqueValue(() -> TEST_VALUE,
                    v -> counter.incrementAndGet() > LIMIT, LIMIT);
        } catch (Exception e) {
            // Assert
            assertEquals(null, value);
            assertEquals(LIMIT, counter.get());
            throw e;
        }
    }
}
