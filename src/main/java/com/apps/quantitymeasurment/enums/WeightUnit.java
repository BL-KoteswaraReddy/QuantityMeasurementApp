package com.apps.quantitymeasurment.enums;

import com.apps.quantitymeasurment.IMeasurable;

public enum WeightUnit implements IMeasurable {

    //conversion factor to the base unit(grams)
    KILOGRAM(1000.0),
    GRAM(1.0),
    POUND(453.592),
    MILLIGRAM(0.001),
    TONNE(1000000.0);


    private final double conversionFactor;

    WeightUnit(double conversionFactor) {

        this.conversionFactor = conversionFactor;
    }

    //return conversinFactor;
    public double getConversionFactor()
    {
        return conversionFactor;
    }

    //convert to base unit
    public double convertToBaseUnit(double value)
    {
       return value*conversionFactor;
    }

    //convert from base unit
    public double convertFromBaseUnit(double baseValue)
    {
        double result =  baseValue/conversionFactor;
        return roundOffTwoDecimal(result);
    }

    private double roundOffTwoDecimal(double value) {
        return Math.round(value*100.0/100.0);
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }

    @Override
    public void validateOperationSupport(String operation) {

    }


    @Override
    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    public static IMeasurable getUnitInstance(String unitName) {
        for(LengthUnit unit: LengthUnit.values())
        {
            if(unit.getUnitName().equalsIgnoreCase(unitName))
                return unit;
        }
        throw new IllegalArgumentException("Invalid Length unit: "+unitName);
    }

    public boolean supportArithmetic() {
        return false;
    }

}
