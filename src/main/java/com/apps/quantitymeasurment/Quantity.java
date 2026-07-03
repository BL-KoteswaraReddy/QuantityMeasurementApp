package com.apps.quantitymeasurment;

import javax.print.Doc;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;


public class Quantity<U extends IMeasurable> {


    private final double value;
    private final U unit;

    // ... inside class Quantity<U extends IMeasurable> ...

    /**
     * Enum-based dispatch for arithmetic operations. Each constant carries
     * its own computation logic via a {@link DoubleBinaryOperator} lambda,
     * operating on already-base-unit-normalized values.
     */

    private enum ArithmeticOperation
    {
        ADD((a, b) -> a+b),
        SUBTRACT((a, b) -> a-b),
        DIVIDE((a, b) -> {
            if(b== 0.0)
                throw new ArithmeticException("Cannot divide by zero quantity ");

            return a/b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        double compute(double a, double b)
        {
            return operator.applyAsDouble(a, b);
        }
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired )
    {
        if(other == null)
        {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if(this.unit.getClass() != other.unit.getClass())
        {
            throw new IllegalArgumentException("Cannot operate on different measurement categories: "
            +this.unit.getClass().getSimpleName()
                    +" vs "+other.unit.getClass().getSimpleName());
        }

        if(!Double.isFinite(this.value) || !Double.isFinite(other.value))
        {
            throw new IllegalArgumentException("Invalid value in operand");
        }

        if(targetUnitRequired && targetUnit == null)
        {
            throw new IllegalArgumentException("Target Unit cannot be null");
        }

    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation)
    {
        this.unit.validateOperationSupport(operation.name());   // ← NEW LINE
        double thisInBase = this.convertToBaseUnit();
        double otherInBase = other.convertToBaseUnit();
        return operation.compute(thisInBase, otherInBase);
    }

    private double roundToTwoDecimals(double value)
    {
        return Math.round(value*100.0)/100.0;
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double resultValue = roundToTwoDecimals(targetUnit.convertFromBaseUnit(baseResult));
        return new Quantity<>(resultValue, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double resultValue = roundToTwoDecimals(targetUnit.convertFromBaseUnit(baseResult));
        return new Quantity<>(resultValue, targetUnit);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

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
}