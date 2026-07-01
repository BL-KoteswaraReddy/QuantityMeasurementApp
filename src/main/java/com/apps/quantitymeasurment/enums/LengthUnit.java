package com.apps.quantitymeasurment.enums;
import static com.apps.quantitymeasurment.QuantityMeasurmentApplication.*;

import com.apps.quantitymeasurment.IMeasurable;
import com.apps.quantitymeasurment.QuantityMeasurmentApplication;

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


}
