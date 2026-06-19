package com.apps.quantitymeasurment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.rmi.ssl.SslRMIClientSocketFactory;

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
            if (this == object)
                return true;

            if (object == null || getClass() != object.getClass())
                return false;

            Inches inches = (Inches) object;

            return Double.compare(this.value, inches.value) == 0;

        }

        public String toString() {
            return value + " ft ";
        }

    }

    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchEquality();
    }

    private static void demonstrateInchEquality() {
        Inches inch1 = new Inches(1.0);
        Inches inch2 = new Inches(1.0);
        System.out.println("Inchesh equality " + inch1.equals(inch2));
    }

    private static void demonstrateFeetEquality() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);

        System.out.println("Feet equality " + feet1.equals(feet2));

    }
}