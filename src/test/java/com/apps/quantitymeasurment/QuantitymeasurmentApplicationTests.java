package com.apps.quantitymeasurment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Inherited;

import static com.apps.quantitymeasurment.QuantityMeasurmentApplication.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuantitymeasurmentApplicationTests {

    @Test
    void givenSameFeetValue_WhenCompared_ShouldReturnTrue() {
        Feet feet1 =
                new Feet(1.0);
        Feet feet2 =
                new Feet(1.0);

        assertTrue(feet1.equals(feet2));
    }

    @Test
    void givenDifferentFeetValue_WhenCompared_ShouldReturnFalse() {
        Feet feet1 =
                new Feet(1.0);
        Feet feet2 =
                new Feet(2.0);

        assertFalse(feet1.equals(feet2));
    }

    @Test
    void givenSameObject_WhenCompared_ShouldReturnTrue() {
        Feet feet =
                new Feet(1.0);

        assertTrue(feet.equals(feet));
    }

    @Test
    void givenNull_WhenCompared_ShouldReturnFalse() {
        Feet feet =
                new Feet(1.0);

        assertFalse(feet.equals(null));
    }

    @Test
    void givenDifferentType_WhenCompared_ShouldReturnFalse() {
        Feet feet =
                new Feet(1.0);

        assertFalse(feet.equals("1.0"));
    }


    @Test
    public void testFeetEquality_SameValue()
    {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);
        assertTrue(feet1.equals(feet2),"both feets are equal");
    }


    @Test
    public void testInchEquality_SameValue()
    {
        Inches inch1 = new Inches(1.0);
        Inches  inch2 = new Inches(1.0);
        assertTrue(inch1.equals(inch2), "both inches values are same");

    }

    @Test
    public void testInhcesEquality_Nullcomparision()
    {
        Inches inche1 = new Inches(1.0);

        assertFalse(inche1.equals(null),"NUll pointer is checking");

    }

    @Test
    public void TestInchesEquality_DifferentClass()
    {
        Inches inche1 = new Inches(1.0);
        assertFalse(inche1.equals("1.0"));
    }

    @Test
    public void testInchesEquality_SameReference()
    {
        Inches inche1 = new Inches(1.0);
        assertTrue(inche1.equals(inche1));
    }

}
