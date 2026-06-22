package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.WeightUnit;

public class Weight {
    private double value;
    private WeightUnit unit;

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    public Weight(double value, WeightUnit unit) {

        if(!Double.isFinite(value))
        {
            throw new IllegalArgumentException("Invalid weight value");
        }

        if(unit == null)
        {
            throw new IllegalArgumentException("Weight cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    public double convertToBaseUnit(){
        return unit.convertToBaseUnit(value);
    }

    public Weight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = unit.convertToBaseUnit(value);

        double convertedValue =
                targetUnit.convertFromBaseUnit(baseValue);

        return new Weight(convertedValue, targetUnit);
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
            return true;

        if(object == null || getClass()!=object.getClass())
            return false;

        Weight other = (Weight)object;

     //   return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;

        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisInBase - otherInBase) < 0.01;
    }

    // UC6 style addition
    public Weight add(Weight other) {
        if (other == null) {
            throw new IllegalArgumentException("Weight cannot be null");
        }

        double totalInKg =
                this.convertToBaseUnit()
                        + other.convertToBaseUnit();

        double resultValue =
                unit.convertFromBaseUnit(totalInKg);

        return new Weight(resultValue, unit);
    }


    // UC7 style addition
    public Weight add(Weight other, WeightUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Weight cannot be null");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double totalInKg =
                this.convertToBaseUnit()
                        + other.convertToBaseUnit();

        double resultValue =
                targetUnit.convertFromBaseUnit(totalInKg);

        return new Weight(resultValue, targetUnit);
    }
    @Override
    public String toString()
    {
        return unit+ " " +value;
    }

}
