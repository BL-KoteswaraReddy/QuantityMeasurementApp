package com.apps.quantitymeasurment;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // Convert this quantity's value into the category's base unit
    public double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Quantity<?> other = (Quantity<?>) object;

        // Cross-category safety: a foot must never equal a kilogram,
        // even though both are ultimately just "double + unit".
        if (this.unit.getClass() != other.unit.getClass()) {
            return false;
        }

        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);

        return Double.compare(thisInBase, otherInBase) == 0;
    }

    @Override
    public int hashCode() {
        // Hash on the base-unit value + category, so that equal quantities
        // (e.g. 1 FEET and 12 INCHES) always hash the same way,
        // preserving the equals/hashCode contract.
        return Objects.hash(unit.getClass(), unit.convertToBaseUnit(value));
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        // Rounded to 2 decimal places to keep output clean and match
        // the expected UC10 sample outputs (e.g. Quantity(12.0, INCHES))
        double rounded = Math.round(convertedValue * 100.0) / 100.0;

        return new Quantity<>(rounded, targetUnit);
    }

    // Result expressed in this quantity's own unit
    public Quantity<U> add(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        double totalInBase = this.convertToBaseUnit() + other.convertToBaseUnit();
        double resultValue = this.unit.convertFromBaseUnit(totalInBase);

        return new Quantity<>(resultValue, this.unit);
    }

    // Result expressed in an explicitly chosen unit
    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double totalInBase = this.convertToBaseUnit() + other.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(totalInBase);

        return new Quantity<>(resultValue, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}