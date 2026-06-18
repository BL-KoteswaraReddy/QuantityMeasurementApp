package com.apps.quantitymeasurment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuantityMeasurmentApplication {

    // Inner class representing Feet measurement
    static class Feet {
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

	public static void main(String[] args) {

        Feet firstValue = new Feet(1.0);
        Feet secondValue = new Feet(1.0);

        boolean result = firstValue.equals(secondValue);

        System.out.println("Input: " + firstValue + " and " + secondValue);
        System.out.println("Output: Equal (" + result + ")");
	}

}
