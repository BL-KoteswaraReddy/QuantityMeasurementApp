package com.apps.quantitymeasurment.enums;

import com.apps.quantitymeasurment.IMeasurable;


public enum LengthUnit implements IMeasurable {

    FEET(12.0),
        INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(0.393701);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    //convert current value to the base unit(inches)
    public double convertToBaseUnit(double value)
    {
        return value*conversionFactor;
    }

    //convert from baseunit
    public double convertFromBaseUnit(double baseValue)
    {
        return baseValue/conversionFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public IMeasurable getUnitInstance(String unitName) {
        for(LengthUnit unit: LengthUnit.values())
        {
            if(unit.getUnitName().equalsIgnoreCase(unitName))
                return unit;
        }
        throw new IllegalArgumentException("Invalid Length unit: "+unitName);
    }

    @Override
    public boolean supportArithmetic() {
        return IMeasurable.super.supportsArithmetic();
    }

    @Override
    public void validateOperationSupport() {
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }

    @Override
    public void validateOperationSupport(String operation) {

    }

}