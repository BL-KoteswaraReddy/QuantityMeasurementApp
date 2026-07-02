package com.apps.quantitymeasurment.enums;

import com.apps.quantitymeasurment.IMeasurable;

public enum VolumeUnit implements IMeasurable {

         LITRE(1.0),                 //base unit
         MILLILITRE(0.001),          //1 ML = 0.001 L
         GALLON(3.78541);           //1 gallon = 3.78541 L

         private final double conversionFactor;

    VolumeUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }


    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value*conversionFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue/conversionFactor;
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
}
