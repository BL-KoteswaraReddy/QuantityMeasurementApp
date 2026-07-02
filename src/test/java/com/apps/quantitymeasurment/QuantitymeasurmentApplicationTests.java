package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;
import com.apps.quantitymeasurment.enums.VolumeUnit;
import com.apps.quantitymeasurment.enums.WeightUnit;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurment.enums.TemperatureUnit;


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

    // ===================== UC13: Enum-level arithmetic tests =====================

    @Test
    void testEnumConstant_ADD_CorrectlyAdds() {
        // Package-private access trick: test through public add(), since
        // ArithmeticOperation is private — direct enum access isn't possible
        // from outside the class. This indirectly verifies ADD.compute(7,3)=10.
        Quantity<LengthUnit> result = new Quantity<>(7.0, LengthUnit.FEET)
                .add(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(10.0, result.getValue(), EPS);
    }

    @Test
    void testEnumConstant_SUBTRACT_CorrectlySubtracts() {
        Quantity<LengthUnit> result = new Quantity<>(7.0, LengthUnit.FEET)
                .subtract(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(4.0, result.getValue(), EPS);
    }

    @Test
    void testEnumConstant_DIVIDE_CorrectlyDivides() {
        double result = new Quantity<>(7.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(3.5, result, EPS);
    }

    @Test
    void testArithmeticOperation_DivideByZero_EnumThrows() {
        assertThrows(ArithmeticException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    @Test
    void testValidation_NullOperand_ConsistentAcrossOperations() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

        IllegalArgumentException addEx = assertThrows(IllegalArgumentException.class, () -> q.add(null));
        IllegalArgumentException subEx = assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
        IllegalArgumentException divEx = assertThrows(IllegalArgumentException.class, () -> q.divide(null));

        assertEquals(addEx.getMessage(), subEx.getMessage());
        assertEquals(subEx.getMessage(), divEx.getMessage());
    }

    @Test
    void testValidation_CrossCategory_ConsistentAcrossOperations() {
        Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity weight = new Quantity<>(5.0, WeightUnit.KILOGRAM); // raw type to bypass compiler

        assertThrows(IllegalArgumentException.class, () -> length.add(weight));
        assertThrows(IllegalArgumentException.class, () -> length.subtract(weight));
        assertThrows(IllegalArgumentException.class, () -> length.divide(weight));
    }

    @Test
    void testValidation_NullTargetUnit_AddSubtractReject() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> a.add(b, null));
        assertThrows(IllegalArgumentException.class, () -> a.subtract(b, null));
    }

    @Test
    void testRounding_AddSubtract_TwoDecimalPlaces() {
        Quantity<LengthUnit> sum = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(1.0, LengthUnit.INCHES));
        double rounded = Math.round(sum.getValue() * 100.0) / 100.0;
        assertEquals(rounded, sum.getValue(), 1e-9);
    }

    @Test
    void testRounding_Divide_NoRounding() {
        // 1/3 has infinite decimal expansion; verify it's NOT truncated to 2 places
        double result = new Quantity<>(1.0, LengthUnit.FEET)
                .divide(new Quantity<>(3.0, LengthUnit.FEET));
        assertNotEquals(Math.round(result * 100.0) / 100.0, result, 1e-9);
    }

    @Test
    void testArithmetic_Chain_Operations() {
        Quantity<LengthUnit> q1 = new Quantity<>(20.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q3 = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> q4 = new Quantity<>(2.0, LengthUnit.FEET);

        Quantity<LengthUnit> afterAddSub = q1.add(q2).subtract(q3);
        double finalRatio = afterAddSub.divide(q4);

        assertEquals(22.0, afterAddSub.getValue(), EPS);
        assertEquals(11.0, finalRatio, EPS);
    }

    @Test
    void testAllOperations_AcrossAllCategories() {
        assertEquals(15.0, new Quantity<>(10.0, LengthUnit.FEET).add(new Quantity<>(5.0, LengthUnit.FEET)).getValue(), EPS);
        assertEquals(5.0, new Quantity<>(10.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM)).getValue(), EPS);
        assertEquals(2.0, new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE)), EPS);
    }


// ===================== UC14: Temperature =====================

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertEquals(new Quantity<>(0.0, TemperatureUnit.CELSIUS), new Quantity<>(0.0, TemperatureUnit.CELSIUS));
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertEquals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT), new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        assertEquals(new Quantity<>(0.0, TemperatureUnit.CELSIUS), new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        assertEquals(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_CelsiusToKelvin() {
        assertEquals(new Quantity<>(0.0, TemperatureUnit.CELSIUS), new Quantity<>(273.15, TemperatureUnit.KELVIN));
    }

    @Test
    void testTemperatureEquality_Negative40EqualPoint() {
        assertEquals(new Quantity<>(-40.0, TemperatureUnit.CELSIUS), new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> a = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> b = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(a.equals(b), b.equals(a));
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> t = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
        assertEquals(t, t);
    }

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        assertEquals(122.0, new Quantity<>(50.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);
        assertEquals(-4.0, new Quantity<>(-20.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        assertEquals(50.0, new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {
        Quantity<TemperatureUnit> result = new Quantity<>(37.5, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(37.5, result.getValue(), 1e-2);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> result = new Quantity<>(25.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.CELSIUS);
        assertEquals(25.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_ZeroValue() {
        Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(32.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_AbsoluteZero() {
        Quantity<TemperatureUnit> kelvin = new Quantity<>(-273.15, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.KELVIN);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(-273.15, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(0.0, kelvin.getValue(), 1e-2);
        assertEquals(-459.67, fahrenheit.getValue(), 1e-2);
    }

    @Test
    void testTemperatureConversion_LargeValues() {
        Quantity<TemperatureUnit> result = new Quantity<>(1000.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(1832.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS).add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS).subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS).divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_ErrorMessage() {
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS).add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
        assertTrue(ex.getMessage().toLowerCase().contains("add"));
    }

    @Test
    void testTemperatureVsLengthIncompatibility() {
        assertNotEquals(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(100.0, LengthUnit.FEET));
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        assertNotEquals(new Quantity<>(50.0, TemperatureUnit.CELSIUS), new Quantity<>(50.0, WeightUnit.KILOGRAM));
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        assertNotEquals(new Quantity<>(25.0, TemperatureUnit.CELSIUS), new Quantity<>(25.0, VolumeUnit.LITRE));
    }

    @Test
    void testOperationSupportMethods_TemperatureUnit() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_NonTemperatureUnits_DefaultTrue() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
    }

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<TemperatureUnit>(100.0, null));
    }

    @Test
    void testTemperatureDifferentValuesInequality() {
        assertNotEquals(new Quantity<>(50.0, TemperatureUnit.CELSIUS), new Quantity<>(100.0, TemperatureUnit.CELSIUS));
    }

    @Test
    void testTemperatureValidateOperationSupport_MethodBehavior() {
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("ADD"));
    }

    @Test
    void testTemperatureEnumImplementsIMeasurable() {
        assertTrue(IMeasurable.class.isAssignableFrom(TemperatureUnit.class));
    }

    @Test
    void testTemperatureIntegrationWithGenericQuantity() {
        Quantity<TemperatureUnit> t = new Quantity<>(20.0, TemperatureUnit.CELSIUS);
        assertEquals(TemperatureUnit.CELSIUS, t.getUnit());
        assertEquals(20.0, t.getValue(), EPS);
    }




}