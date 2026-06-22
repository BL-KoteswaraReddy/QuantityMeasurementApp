package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class QuantityMeasurmentApplication {

    public static Length demonstrateLengthAddition(double v, LengthUnit lengthUnit, double v1, LengthUnit lengthUnit1) {
            Length result = new Length(v, lengthUnit).add(new Length(v1,lengthUnit1));
            return result;
    }

    public static Length demonstrateLengthAddition(double value1, LengthUnit unit1, double value2, LengthUnit unit2, LengthUnit targetUnit)
    {
        if(targetUnit == null)
            throw  new IllegalArgumentException("Target unit cannot be null");
        return new Length(value1, unit1).add(new Length(value2, unit2), targetUnit);
    }


    // Inner class representing Feet measurement
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            // Same reference check
            if (this == obj) {
                return true;
            }

            // Null check and type check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Safe casting
            Feet other = (Feet) obj;

            // Compare double values safely
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public String toString() {
            return value + " ft";
        }
    }

    public static class Inches {

        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;

            if (object == null || getClass() != object.getClass()) return false;

            Inches inches = (Inches) object;

            return Double.compare(this.value, inches.value) == 0;

        }

        public String toString() {
            return value + " ft ";
        }

    }

    public static void main(String[] args) {

        //Demonstrate Feet and Inches Camparision
        demonstrateLengthComparision(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);

        //Demonstrate Yards and inches comparision
        demonstrateLengthComparision(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES);

        //Demonstrate Centimeters and Inches comparison
        demonstrateLengthComparision(100.0, LengthUnit.CENTIMETERS, 39.3701, LengthUnit.INCHES);

        //Domonstrate Feet and Yard comparison
        demonstrateLengthComparision(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);

        //Demonstrate Centimeteres and Feet comparision
        demonstrateLengthComparision(30.48, LengthUnit.CENTIMETERS, 1.0, LengthUnit.FEET);

        System.out.println(
                demonstrateLengthAddition(
                        1.0,
                        LengthUnit.FEET,
                        12.0,
                        LengthUnit.INCHES,
                        LengthUnit.FEET));

        System.out.println(
                demonstrateLengthAddition(
                        1.0,
                        LengthUnit.FEET,
                        12.0,
                        LengthUnit.INCHES,
                        LengthUnit.INCHES));

        System.out.println(
                demonstrateLengthAddition(
                        1.0,
                        LengthUnit.FEET,
                        12.0,
                        LengthUnit.INCHES,
                        LengthUnit.YARDS));

        System.out.println(
                demonstrateLengthAddition(
                        1.0,
                        LengthUnit.YARDS,
                        3.0,
                        LengthUnit.FEET,
                        LengthUnit.YARDS));

        System.out.println(
                demonstrateLengthAddition(
                        36.0,
                        LengthUnit.INCHES,
                        1.0,
                        LengthUnit.YARDS,
                        LengthUnit.FEET));

        System.out.println(
                demonstrateLengthAddition(
                        2.54,
                        LengthUnit.CENTIMETERS,
                        1.0,
                        LengthUnit.INCHES,
                        LengthUnit.CENTIMETERS));

        System.out.println(
                demonstrateLengthAddition(
                        5.0,
                        LengthUnit.FEET,
                        0.0,
                        LengthUnit.INCHES,
                        LengthUnit.YARDS));

        System.out.println(
                demonstrateLengthAddition(
                        5.0,
                        LengthUnit.FEET,
                        -2.0,
                        LengthUnit.FEET,
                        LengthUnit.INCHES));

    }

    private static boolean demonstrateLengthComparision(double value1, LengthUnit lengthUnitOne, double value2, LengthUnit lengthUnitTwo)
    {
             Length length1 = new Length(value1, lengthUnitOne);
             Length length2 = new Length(value2, lengthUnitTwo);

             boolean result = demonstrateLengthEquality(length1, length2);

             System.out.println(value1+" "+lengthUnitOne+" and "+value2 + " "+lengthUnitTwo+" ? " +result);
            return result;
    }

    static boolean demonstrateLengthEquality(Length length1, Length length2) {
        return length2.equals(length2);
    }


}