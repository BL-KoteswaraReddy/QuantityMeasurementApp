package com.apps.quantitymeasurment.enums;

import com.apps.quantitymeasurment.IMeasurable;
import com.apps.quantitymeasurment.SupportsArithmetic;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    // Celsius is the base unit — conversion to/from itself is the identity function
    CELSIUS(
            celsius -> celsius,
            celsius -> celsius
    ),
    FAHRENHEIT(
            fahrenheit -> (fahrenheit - 32.0) * 5.0 / 9.0,   // °F -> °C
            celsius -> (celsius * 9.0 / 5.0) + 32.0          // °C -> °F
    ),
    KELVIN(
            kelvin -> kelvin - 273.15,                       // K -> °C
            celsius -> celsius + 273.15                      // °C -> K
    );

    private final Function<Double, Double> toBaseUnit;
    private final Function<Double, Double> fromBaseUnit;
    private final SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(Function<Double, Double> toBaseUnit, Function<Double, Double> fromBaseUnit) {
        this.toBaseUnit = toBaseUnit;
        this.fromBaseUnit = fromBaseUnit;
    }

    @Override
    public double getConversionFactor() {
        // Temperature conversions are offset-based (non-linear), so a
        // single multiplicative factor doesn't apply here. 1.0 is a
        // neutral placeholder — convertToBaseUnit/convertFromBaseUnit
        // below do the actual formula-based work, ignoring this value.
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toBaseUnit.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromBaseUnit.apply(baseValue);
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {

        if(!supportsArithmetic.isSupported())
        {
            throw new UnsupportedOperationException(
                    "Temperature does not support " + operation.toLowerCase()
                            + " — absolute temperatures cannot be combined "
                            + "arithmetically. Only equality comparison and "
                            + "unit conversion are supported for TemperatureUnit.");
        }
        }



    @Override
    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }


    public IMeasurable getUnitInstance(String unitName) {
       for(TemperatureUnit unit: TemperatureUnit.values())
       {
           if(unit.toString().equalsIgnoreCase(unitName)){
               return unit;
           }
       }
        throw new IllegalArgumentException("Invalid Length unit: "+unitName);
    }

}