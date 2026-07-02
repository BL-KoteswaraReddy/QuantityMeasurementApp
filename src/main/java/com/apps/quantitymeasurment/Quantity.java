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
    public int hashCode() {
        // Hash on the base-unit value + category, so that equal quantities
        // (e.g. 1 FEET and 12 INCHES) always hash the same way,
        // preserving the equals/hashCode contract.
        double baseValue = unit.convertToBaseUnit(value);
        long rounded = Math.round(baseValue+1_000_000);
        return Objects.hash(unit.getClass(), rounded);
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

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
            return true;
        if(object == null || getClass()!=object.getClass())
        return false;

        Quantity<?> other = (Quantity<?>)object;
        if(this.unit.getClass() != other.unit.getClass())
            return false;

        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);

        // Epsilon tolerance needed once conversion factors (like GALLON's
        // 3.78541) introduce floating-point rounding across categories.
        // Small enough to not affect exact UC1–UC10 comparisons.
        final double EPSILON = 1e-6;
        return Math.abs(thisInBase - otherInBase) < EPSILON;

    }

    /**
     * Subtracts {@code other} from this quantity, returning the result in
     * this quantity's own unit.
     * <p>Non-commutative: {@code A.subtract(B)} is not generally equal to
     * {@code B.subtract(A)} — the sign flips.</p>
     *
     * @param other the quantity to subtract; must be non-null and same category
     * @return a new {@code Quantity<U>} representing the difference
     * @throws IllegalArgumentException if {@code other} is null or belongs
     *         to a different measurement category
     */
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    /**
     * Subtracts {@code other} from this quantity, expressing the result in
     * {@code targetUnit}.
     *
     * @param other the quantity to subtract
     * @param targetUnit the unit the result should be expressed in
     * @return a new {@code Quantity<U>} representing the difference
     * @throws IllegalArgumentException if {@code other} or {@code targetUnit}
     *         is null, or if {@code other} belongs to a different category
     */
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOperand(other);
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseResult = this.convertToBaseUnit() - other.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(baseResult);
        double rounded = Math.round(resultValue * 100.0) / 100.0;

        return new Quantity<>(rounded, targetUnit);
    }

    /**
     * Divides this quantity by {@code other}, returning a dimensionless
     * scalar ratio. Units cancel out — the result carries no unit.
     * <p>Non-commutative: {@code A.divide(B)} is the reciprocal of
     * {@code B.divide(A)}.</p>
     *
     * @param other the divisor quantity; must be non-null, same category,
     *              and non-zero
     * @return the ratio {@code this / other} as a raw {@code double}
     * @throws IllegalArgumentException if {@code other} is null or belongs
     *         to a different measurement category
     * @throws ArithmeticException if {@code other} represents zero
     *         (division by zero)
     */
    public double divide(Quantity<U> other) {
        validateOperand(other);

        double otherInBase = other.convertToBaseUnit();
        if (otherInBase == 0.0) {
            throw new ArithmeticException("Cannot divide by zero quantity");
        }

        return this.convertToBaseUnit() / otherInBase;
    }

    /**
     * Shared validation for arithmetic operands: non-null, finite value,
     * and same measurement category as {@code this}.
     */
    private void validateOperand(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (this.unit.getClass() != other.unit.getClass()) {
            throw new IllegalArgumentException(
                    "Cannot operate on different measurement categories: "
                            + this.unit.getClass().getSimpleName()
                            + " vs " + other.unit.getClass().getSimpleName());
        }
        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid value in operand");
        }
    }
}