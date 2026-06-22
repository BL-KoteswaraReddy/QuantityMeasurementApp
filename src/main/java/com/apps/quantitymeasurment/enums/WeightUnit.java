package com.apps.quantitymeasurment.enums;

public enum WeightUnit {

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
        return baseValue/conversionFactor;
    }

}
