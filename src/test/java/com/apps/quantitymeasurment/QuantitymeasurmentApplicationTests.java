package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;
import com.apps.quantitymeasurment.enums.VolumeUnit;
import com.apps.quantitymeasurment.enums.WeightUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    private static final double EPS = 1e-4;

    // ---------- Equality ----------

    @Test
    void testEquality_LitreToLitre_SameValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
    }

    @Test
    void testEquality_LitreToLitre_DifferentValue() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE));
    }

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
    }

    @Test
    void testEquality_MillilitreToLitre_EquivalentValue() {
        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
    }

    @Test
    void testEquality_LitreToGallon_EquivalentValue() {
        assertEquals(new Quantity<>(3.78541, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.GALLON));
    }

    @Test
    void testEquality_GallonToLitre_EquivalentValue() {
        assertEquals(new Quantity<>(1.0, VolumeUnit.GALLON), new Quantity<>(3.78541, VolumeUnit.LITRE));
    }

    @Test
    void testEquality_VolumeVsLength_Incompatible() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_VolumeVsWeight_Incompatible() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1.0, WeightUnit.KILOGRAM));
    }

    @Test
    void testEquality_NullComparison() {
        assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), null);
    }

    @Test
    void testEquality_SameReference() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertEquals(q, q);
    }

    @Test
    void testEquality_NullUnit() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<VolumeUnit>(1.0, null));
    }

    @Test
    void testEquality_TransitiveProperty() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> c = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }

    @Test
    void testEquality_ZeroValue() {
        assertEquals(new Quantity<>(0.0, VolumeUnit.LITRE), new Quantity<>(0.0, VolumeUnit.MILLILITRE));
    }

    @Test
    void testEquality_NegativeVolume() {
        assertEquals(new Quantity<>(-1.0, VolumeUnit.LITRE), new Quantity<>(-1000.0, VolumeUnit.MILLILITRE));
    }

    @Test
    void testEquality_LargeVolumeValue() {
        assertEquals(new Quantity<>(1000000.0, VolumeUnit.MILLILITRE), new Quantity<>(1000.0, VolumeUnit.LITRE));
    }

    @Test
    void testEquality_SmallVolumeValue() {
        assertEquals(new Quantity<>(0.001, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.MILLILITRE));
    }

    // ---------- Conversion ----------

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_MillilitreToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
        assertEquals(1.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
        assertEquals(3.79, result.getValue(), EPS);
    }

    @Test
    void testConversion_LitreToGallon() {
        Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);
        assertEquals(1.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_MillilitreToGallon() {
        Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.GALLON);
        assertEquals(0.26, result.getValue(), 1e-3);
    }

    @Test
    void testConversion_SameUnit() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE).convertTo(VolumeUnit.LITRE);
        assertEquals(5.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_ZeroValue() {
        Quantity<VolumeUnit> result = new Quantity<>(0.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(0.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_NegativeValue() {
        Quantity<VolumeUnit> result = new Quantity<>(-1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(-1000.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_RoundTrip() {
        Quantity<VolumeUnit> result = new Quantity<>(1.5, VolumeUnit.LITRE)
                .convertTo(VolumeUnit.MILLILITRE)
                .convertTo(VolumeUnit.LITRE);
        assertEquals(1.5, result.getValue(), EPS);
    }

    // ---------- Addition ----------

    @Test
    void testAddition_SameUnit_LitrePlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(2.0, VolumeUnit.LITRE));
        assertEquals(3.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_SameUnit_MillilitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(500.0, VolumeUnit.MILLILITRE)
                .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));
        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        assertEquals(2.0, result.getValue(), EPS); // result in LITRE (first operand's unit)
    }

    @Test
    void testAddition_CrossUnit_MillilitrePlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                .add(new Quantity<>(1.0, VolumeUnit.LITRE));
        assertEquals(2000.0, result.getValue(), EPS); // result in MILLILITRE
    }

    @Test
    void testAddition_CrossUnit_GallonPlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON)
                .add(new Quantity<>(3.78541, VolumeUnit.LITRE));
        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Litre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
        assertEquals(2000.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Gallon() {
        Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE)
                .add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);
        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_Commutativity() {
        Quantity<VolumeUnit> ab = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
        Quantity<VolumeUnit> ba = new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                .add(new Quantity<>(1.0, VolumeUnit.LITRE), VolumeUnit.LITRE);
        assertEquals(ab.getValue(), ba.getValue(), EPS);
    }

    @Test
    void testAddition_WithZero() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
                .add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));
        assertEquals(5.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_NegativeValues() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
                .add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));
        assertEquals(3.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_LargeValues() {
        Quantity<VolumeUnit> result = new Quantity<>(1e6, VolumeUnit.LITRE)
                .add(new Quantity<>(1e6, VolumeUnit.LITRE));
        assertEquals(2e6, result.getValue(), EPS);
    }

    @Test
    void testAddition_SmallValues() {
        Quantity<VolumeUnit> result = new Quantity<>(0.001, VolumeUnit.LITRE)
                .add(new Quantity<>(0.002, VolumeUnit.LITRE));
        assertEquals(0.003, result.getValue(), EPS);
    }

    // ---------- VolumeUnit enum itself ----------

    @Test
    void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor());
    }

    @Test
    void testVolumeUnitEnum_MillilitreConstant() {
        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor());
    }

    @Test
    void testVolumeUnitEnum_GallonConstant() {
        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor());
    }

    @Test
    void testConvertToBaseUnit_LitreToLitre() {
        assertEquals(5.0, VolumeUnit.LITRE.convertToBaseUnit(5.0), EPS);
    }

    @Test
    void testConvertToBaseUnit_MillilitreToLitre() {
        assertEquals(1.0, VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0), EPS);
    }

    @Test
    void testConvertToBaseUnit_GallonToLitre() {
        assertEquals(3.78541, VolumeUnit.GALLON.convertToBaseUnit(1.0), EPS);
    }

    @Test
    void testConvertFromBaseUnit_LitreToLitre() {
        assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2.0), EPS);
    }

    @Test
    void testConvertFromBaseUnit_LitreToMillilitre() {
        assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0), EPS);
    }

    @Test
    void testConvertFromBaseUnit_LitreToGallon() {
        assertEquals(1.0, VolumeUnit.GALLON.convertFromBaseUnit(3.78541), EPS);
    }

    // ---------- Hash / collections ----------

    @Test
    void testHashCode_ConsistentWithEquals() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    // ---------- Backward compatibility spot checks (UC1–UC10) ----------

    @Test
    void testBackwardCompatibility_LengthEquality() {
        assertEquals(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
    }

    @Test
    void testBackwardCompatibility_WeightAddition() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
        assertEquals(2.0, result.getValue(), EPS);
    }

// ===================== SUBTRACTION =====================

    @Test
    void testSubtraction_SameUnit_FeetMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(5.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_SameUnit_LitreMinusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(10.0, VolumeUnit.LITRE)
                .subtract(new Quantity<>(3.0, VolumeUnit.LITRE));
        assertEquals(7.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(9.5, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_CrossUnit_InchesMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(120.0, LengthUnit.INCHES)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(60.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Feet() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertEquals(9.5, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Inches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertEquals(114.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
                .subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertEquals(3000.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_ResultingInNegative() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(-5.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_ResultingInZero() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(120.0, LengthUnit.INCHES));
        assertEquals(0.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_WithZeroOperand() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(0.0, LengthUnit.INCHES));
        assertEquals(5.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_WithNegativeValues() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(-2.0, LengthUnit.FEET));
        assertEquals(7.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_NonCommutative() {
        double forward = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET)).getValue();
        double reverse = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET)).getValue();
        assertEquals(5.0, forward, EPS);
        assertEquals(-5.0, reverse, EPS);
    }

    @Test
    void testSubtraction_WithLargeValues() {
        Quantity<WeightUnit> result = new Quantity<>(1e6, WeightUnit.KILOGRAM)
                .subtract(new Quantity<>(5e5, WeightUnit.KILOGRAM));
        assertEquals(5e5, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_WithSmallValues() {
        Quantity<LengthUnit> result = new Quantity<>(0.001, LengthUnit.FEET)
                .subtract(new Quantity<>(0.0005, LengthUnit.FEET));
        assertEquals(0.0005, result.getValue(), 1e-3);
    }

    @Test
    void testSubtraction_NullOperand() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET).subtract(null));
    }

    @Test
    void testSubtraction_NullTargetUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(5.0, LengthUnit.FEET), null));
    }

    @Test
    void testSubtraction_CrossCategory() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract((Quantity) new Quantity<>(5.0, WeightUnit.KILOGRAM)));
    }

    @Test
    void testSubtraction_AllMeasurementCategories() {
        assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET)).getValue(), EPS);
        assertEquals(5.0, new Quantity<>(10.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM)).getValue(), EPS);
        assertEquals(5.0, new Quantity<>(10.0, VolumeUnit.LITRE).subtract(new Quantity<>(5.0, VolumeUnit.LITRE)).getValue(), EPS);
    }

    @Test
    void testSubtraction_ChainedOperations() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(2.0, LengthUnit.FEET))
                .subtract(new Quantity<>(1.0, LengthUnit.FEET));
        assertEquals(7.0, result.getValue(), EPS);
    }

    @Test
    void testSubtraction_Immutability() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        original.subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(10.0, original.getValue(), EPS); // unchanged
    }

    @Test
    void testSubtraction_PrecisionAndRounding() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(1.0, LengthUnit.INCHES));
        // rounded to 2 decimal places
        assertEquals(Math.round(result.getValue() * 100.0) / 100.0, result.getValue(), 1e-9);
    }

// ===================== DIVISION =====================

    @Test
    void testDivision_SameUnit_FeetDividedByFeet() {
        assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET)), EPS);
    }

    @Test
    void testDivision_SameUnit_LitreDividedByLitre() {
        assertEquals(2.0, new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE)), EPS);
    }

    @Test
    void testDivision_CrossUnit_FeetDividedByInches() {
        assertEquals(1.0, new Quantity<>(24.0, LengthUnit.INCHES).divide(new Quantity<>(2.0, LengthUnit.FEET)), EPS);
    }

    @Test
    void testDivision_CrossUnit_KilogramDividedByGram() {
        assertEquals(1.0, new Quantity<>(2.0, WeightUnit.KILOGRAM).divide(new Quantity<>(2000.0, WeightUnit.GRAM)), EPS);
    }

    @Test
    void testDivision_RatioGreaterThanOne() {
        assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET)), EPS);
    }

    @Test
    void testDivision_RatioLessThanOne() {
        assertEquals(0.5, new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET)), EPS);
    }

    @Test
    void testDivision_RatioEqualToOne() {
        assertEquals(1.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET)), EPS);
    }

    @Test
    void testDivision_NonCommutative() {
        double forward = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET));
        double reverse = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(2.0, forward, EPS);
        assertEquals(0.5, reverse, EPS);
    }

    @Test
    void testDivision_ByZero() {
        assertThrows(ArithmeticException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    @Test
    void testDivision_WithLargeRatio() {
        assertEquals(1e6, new Quantity<>(1e6, WeightUnit.KILOGRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM)), 1.0);
    }

    @Test
    void testDivision_WithSmallRatio() {
        assertEquals(1e-6, new Quantity<>(1.0, WeightUnit.KILOGRAM).divide(new Quantity<>(1e6, WeightUnit.KILOGRAM)), 1e-9);
    }

    @Test
    void testDivision_NullOperand() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET).divide(null));
    }

    @Test
    void testDivision_CrossCategory() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET)
                        .divide((Quantity) new Quantity<>(5.0, WeightUnit.KILOGRAM)));
    }

    @Test
    void testDivision_AllMeasurementCategories() {
        assertEquals(2.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET)), EPS);
        assertEquals(2.0, new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)), EPS);
        assertEquals(2.0, new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE)), EPS);
    }

    @Test
    void testDivision_Associativity() {
        double abThenC = (new Quantity<>(100.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET))) / 2.0;
        double aThenBc = 100.0 / (10.0 / 2.0);
        assertNotEquals(abThenC, aThenBc, 0.001); // demonstrates non-associativity
    }

    @Test
    void testDivision_Immutability() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        original.divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(10.0, original.getValue(), EPS); // unchanged
    }

// ===================== INTEGRATION =====================

    @Test
    void testSubtractionAndDivision_Integration() {
        Quantity<LengthUnit> diff = new Quantity<>(20.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
        double ratio = diff.divide(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(2.0, ratio, EPS);
    }

    @Test
    void testSubtractionAddition_Inverse() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> roundTrip = original
                .add(new Quantity<>(5.0, LengthUnit.FEET))
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(original.getValue(), roundTrip.getValue(), EPS);
    }
}