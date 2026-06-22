package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;
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

    @Test
    public void testFeetIncheComparision()
    {
        Length feet = new Length(1.0, LengthUnit.FEET);
        Length inche = new Length(12.0, LengthUnit.INCHES);
        assertTrue(feet.equals(inche));
    }

    @Test
    public void testYardEquality_SameValue() {
        Length yard1 = new Length(1.0, LengthUnit.YARDS);
        Length yard2 = new Length(1.0, LengthUnit.YARDS);

        assertTrue(yard1.equals(yard2));
    }

    @Test
    public void testYardEquality_DifferentValue() {
        Length yard1 = new Length(1.0, LengthUnit.YARDS);
        Length yard2 = new Length(2.0, LengthUnit.YARDS);

        assertFalse(yard1.equals(yard2));
    }

    @Test
    public void testYardToFeetComparison() {
        Length yard = new Length(1.0, LengthUnit.YARDS);
        Length feet = new Length(3.0, LengthUnit.FEET);

        assertTrue(yard.equals(feet));
    }

    @Test
    public void testFeetToYardComparison() {
        Length feet = new Length(3.0, LengthUnit.FEET);
        Length yard = new Length(1.0, LengthUnit.YARDS);

        assertTrue(feet.equals(yard));
    }

    @Test
    public void testYardToInchesComparison() {
        Length yard = new Length(1.0, LengthUnit.YARDS);
        Length inches = new Length(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(inches));
    }

    @Test
    public void testInchesToYardComparison() {
        Length inches = new Length(36.0, LengthUnit.INCHES);
        Length yard = new Length(1.0, LengthUnit.YARDS);

        assertTrue(inches.equals(yard));
    }

    @Test
    public void testYardToFeetNotEqual() {
        Length yard = new Length(1.0, LengthUnit.YARDS);
        Length feet = new Length(2.0, LengthUnit.FEET);

        assertFalse(yard.equals(feet));
    }

    @Test
    public void addSameUnitFeetPlusFeet()
    {
        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(2.0, LengthUnit.FEET);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(1.0, LengthUnit.FEET, 2.0, LengthUnit.FEET);
        Length expected = new Length(3.0, LengthUnit.FEET);
        assertTrue(QuantityMeasurmentApplication.demonstrateLengthEquality(actual, expected));
    }

    @Test
    public void addSameUnitInchesPlusInches() {

        Length length1 = new Length(6.0, LengthUnit.INCHES);
        Length length2 = new Length(6.0, LengthUnit.INCHES);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(6.0, LengthUnit.INCHES, 6.0, LengthUnit.INCHES);
        Length expected = new Length(12.0, LengthUnit.INCHES);

        assertTrue(QuantityMeasurmentApplication.demonstrateLengthEquality(actual, expected));
    }

    @Test
    public void addInchesAndFeet() {

        Length length1 = new Length(12.0, LengthUnit.INCHES);
        Length length2 = new Length(1.0, LengthUnit.FEET);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(12.0, LengthUnit.INCHES, 1.0, LengthUnit.FEET);
        Length expected = new Length(24.0, LengthUnit.INCHES);

        assertTrue(QuantityMeasurmentApplication.demonstrateLengthEquality(actual, expected));
    }

    @Test
    public void addYardsAndFeet() {

        Length length1 = new Length(1.0, LengthUnit.YARDS);
        Length length2 = new Length(3.0, LengthUnit.FEET);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(1.0, LengthUnit.YARDS, 3.0, LengthUnit.FEET);
        Length expected = new Length(2.0, LengthUnit.YARDS);

        assertTrue(QuantityMeasurmentApplication.demonstrateLengthEquality(actual, expected));
    }

    @Test
    public void addCentimetersAndInches() {

        Length length1 = new Length(2.54, LengthUnit.CENTIMETERS);
        Length length2 = new Length(1.0, LengthUnit.INCHES);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(2.54, LengthUnit.CENTIMETERS, 1.0, LengthUnit.INCHES);
        Length expected = new Length(5.08, LengthUnit.CENTIMETERS);

        assertTrue(
                Math.abs(actual.getValue() - expected.getValue()) < 0.01
        );
    }

    @Test
    public void additionIsCommutative() {

        Length feet = new Length(1.0, LengthUnit.FEET);
        Length inches = new Length(12.0, LengthUnit.INCHES);

        Length result1 =
                QuantityMeasurmentApplication.demonstrateLengthAddition(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);

        Length result2 =
                QuantityMeasurmentApplication.demonstrateLengthAddition(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);

        assertEquals(
                result1.convertToBaseUnit(),
                result2.convertToBaseUnit(),
                0.0001
        );
    }

    @Test
    public void addWithZeroValue() {

        Length length1 = new Length(5.0, LengthUnit.FEET);
        Length length2 = new Length(0.0, LengthUnit.INCHES);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(5.0 , LengthUnit.FEET, 0.0, LengthUnit.INCHES);
        Length expected = new Length(5.0, LengthUnit.FEET);

        assertTrue(QuantityMeasurmentApplication.demonstrateLengthEquality(actual, expected));
    }

    @Test
    public void addNegativeValues() {

        Length length1 = new Length(5.0, LengthUnit.FEET);
        Length length2 = new Length(-2.0, LengthUnit.FEET);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(5.0, LengthUnit.FEET, -2.0, LengthUnit.FEET);
        Length expected = new Length(3.0, LengthUnit.FEET);

        assertTrue(QuantityMeasurmentApplication.demonstrateLengthEquality(actual, expected));
    }

    @Test
    public void addNullSecondOperand() {

        Length length1 = new Length(1.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> QuantityMeasurmentApplication.demonstrateLengthAddition(1.0, LengthUnit.FEET, Double.NaN, LengthUnit.FEET)
        );
    }

    @Test
    public void addLargeValues() {

        Length length1 = new Length(1_000_000.0, LengthUnit.FEET);
        Length length2 = new Length(1_000_000.0, LengthUnit.FEET);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(1_000_000.0, LengthUnit.FEET, 1_000_000.0, LengthUnit.FEET);
        Length expected = new Length(2_000_000.0, LengthUnit.FEET);

        assertTrue(QuantityMeasurmentApplication.demonstrateLengthEquality(actual, expected));
    }

    @Test
    public void addSmallValues() {

        Length length1 = new Length(0.001, LengthUnit.FEET);
        Length length2 = new Length(0.002, LengthUnit.FEET);

        Length actual = QuantityMeasurmentApplication.demonstrateLengthAddition(0.001, LengthUnit.FEET, 0.002, LengthUnit.FEET);

        assertEquals(0.003, actual.getValue(), 0.0001);
    }


}

