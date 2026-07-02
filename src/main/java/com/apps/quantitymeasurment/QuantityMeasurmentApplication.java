package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;
import com.apps.quantitymeasurment.enums.VolumeUnit;
import com.apps.quantitymeasurment.enums.WeightUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuantityMeasurmentApplication {

    // One generic method replaces demonstrateLengthEquality + demonstrateWeightEquality
    public static <U extends IMeasurable> boolean demonstrateEquality(
            Quantity<U> q1, Quantity<U> q2) {
        boolean result = q1.equals(q2);
        System.out.println(q1 + " and " + q2 + " equal? " + result);
        return result;
    }

    // One generic method replaces demonstrateLengthConversion + demonstrateWeightConversion
    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
            Quantity<U> quantity, U targetUnit) {
        Quantity<U> result = quantity.convertTo(targetUnit);
        System.out.println(quantity + " converted to " + targetUnit.getUnitName() + " = " + result);
        return result;
    }

    // One generic method replaces demonstrateLengthAddition + demonstrateWeightAddition
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1, Quantity<U> q2) {
        Quantity<U> result = q1.add(q2);
        System.out.println(q1 + " + " + q2 + " = " + result);
        return result;
    }

    // Overload for explicit target unit
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        Quantity<U> result = q1.add(q2, targetUnit);
        System.out.println(q1 + " + " + q2 + " (in " + targetUnit.getUnitName() + ") = " + result);
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurmentApplication.class, args);

        // ---------- Length demonstrations (UC1–UC8 behavior preserved) ----------
        demonstrateEquality(
                new Quantity<>(1.0, LengthUnit.FEET),
                new Quantity<>(12.0, LengthUnit.INCHES));

        demonstrateConversion(
                new Quantity<>(1.0, LengthUnit.FEET),
                LengthUnit.INCHES);

        demonstrateAddition(
                new Quantity<>(1.0, LengthUnit.FEET),
                new Quantity<>(12.0, LengthUnit.INCHES),
                LengthUnit.FEET);

        // ---------- Weight demonstrations (UC9 behavior preserved) ----------
        demonstrateEquality(
                new Quantity<>(1.0, WeightUnit.KILOGRAM),
                new Quantity<>(1000.0, WeightUnit.GRAM));

        demonstrateConversion(
                new Quantity<>(1.0, WeightUnit.KILOGRAM),
                WeightUnit.GRAM);

        demonstrateAddition(
                new Quantity<>(1.0, WeightUnit.KILOGRAM),
                new Quantity<>(1000.0, WeightUnit.GRAM),
                WeightUnit.KILOGRAM);

        // ---------- Cross-category safety demo ----------
        boolean crossCategory = new Quantity<>(1.0, LengthUnit.FEET)
                .equals(new Quantity<>(1.0, WeightUnit.KILOGRAM));
        System.out.println("1 FEET equals 1 KILOGRAM? " + crossCategory); // false

        // Note: the following would NOT compile — that's the point.
        // demonstrateEquality(new Quantity<>(1.0, LengthUnit.FEET),
        //                      new Quantity<>(1.0, WeightUnit.KILOGRAM));


        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        System.out.println("1 L == 1000 mL? " + v1.equals(v2));
        System.out.println("1 L == 1 GALLON? " + v1.equals(v3));
        System.out.println("1 L -> mL: " + v1.convertTo(VolumeUnit.MILLILITRE));
        System.out.println("1 GALLON -> L: " + v3.convertTo(VolumeUnit.LITRE));
        System.out.println("1 L + 1000 mL = " + v1.add(v2));
        System.out.println("1 L + 1 GALLON in GALLON = " + v1.add(v3, VolumeUnit.GALLON));
    }
}