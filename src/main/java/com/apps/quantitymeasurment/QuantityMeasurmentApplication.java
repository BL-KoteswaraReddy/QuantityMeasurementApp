package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuantityMeasurmentApplication {

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


        System.out.println("Feet to inches "+Length.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));
        System.out.println("Yards to Feet "+Length.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));
        System.out.println("Centimeters to Inches "+Length.convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));
        System.out.println("Feet to inches "+Length.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES));


    }

    private static boolean demonstrateLengthComparision(double value1, LengthUnit lengthUnitOne, double value2, LengthUnit lengthUnitTwo)
    {
             Length length1 = new Length(value1, lengthUnitOne);
             Length length2 = new Length(value2, lengthUnitTwo);

             boolean result = demonstrateLengthEquality(length1, length2);

             System.out.println(value1+" "+lengthUnitOne+" and "+value2 + " "+lengthUnitTwo+" ? " +result);
            return result;
    }

    private static boolean demonstrateLengthEquality(Length length1, Length length2) {
        return length2.equals(length2);
    }

}