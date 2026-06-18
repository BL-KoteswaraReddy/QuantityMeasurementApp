package com.apps.quantitymeasurment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuantitymeasurmentApplicationTests {

    @Test
    void givenSameFeetValue_WhenCompared_ShouldReturnTrue() {
        QuantityMeasurmentApplication.Feet feet1 =
                new QuantityMeasurmentApplication.Feet(1.0);
        QuantityMeasurmentApplication.Feet feet2 =
                new QuantityMeasurmentApplication.Feet(1.0);

        assertTrue(feet1.equals(feet2));
    }

    @Test
    void givenDifferentFeetValue_WhenCompared_ShouldReturnFalse() {
        QuantityMeasurmentApplication.Feet feet1 =
                new QuantityMeasurmentApplication.Feet(1.0);
        QuantityMeasurmentApplication.Feet feet2 =
                new QuantityMeasurmentApplication.Feet(2.0);

        assertFalse(feet1.equals(feet2));
    }

    @Test
    void givenSameObject_WhenCompared_ShouldReturnTrue() {
        QuantityMeasurmentApplication.Feet feet =
                new QuantityMeasurmentApplication.Feet(1.0);

        assertTrue(feet.equals(feet));
    }

    @Test
    void givenNull_WhenCompared_ShouldReturnFalse() {
        QuantityMeasurmentApplication.Feet feet =
                new QuantityMeasurmentApplication.Feet(1.0);

        assertFalse(feet.equals(null));
    }

    @Test
    void givenDifferentType_WhenCompared_ShouldReturnFalse() {
        QuantityMeasurmentApplication.Feet feet =
                new QuantityMeasurmentApplication.Feet(1.0);

        assertFalse(feet.equals("1.0"));
    }

}
