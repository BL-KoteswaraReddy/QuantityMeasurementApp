package com.apps.quantitymeasurment;

public interface IMeasurable {

    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();

    static IMeasurable getUnitInstance(String unitName, Class<?> enumClass){
        for(Object constant: enumClass.getEnumConstants())
        {
            IMeasurable unit = (IMeasurable)constant;
            if(unit.getUnitName().equalsIgnoreCase(unitName))
            {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid unit: "+unitName);
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {

    }

    /**
     * Identifies which measurement category this unit belongs to
     * (e.g. "LENGTH", "WEIGHT"). Used by the service layer to validate
     * that a QuantityDTO's declared category matches its unit.
     */
   default String getMeasurementType(){
       return this.getMeasurementType().getClass().getSimpleName();
   }

    /**
     * Resolves an IMeasurable instance from its category and unit name.
     * Used to convert incoming QuantityDTO string fields (e.g.
     * "LENGTH", "FEET") into the real enum constant the service needs.
     *
     * @throws IllegalArgumentException if the category/unit is unknown
     */
    static IMeasurable resolve(String measurementType, String unitName) {
        if (measurementType == null || unitName == null) {
            throw new IllegalArgumentException("Measurement type and unit name cannot be null");
        }
        switch (measurementType.toUpperCase()) {
            case "LENGTH":
                return com.apps.quantitymeasurment.enums.LengthUnit.valueOf(unitName.toUpperCase());
            case "WEIGHT":
                return com.apps.quantitymeasurment.enums.WeightUnit.valueOf(unitName.toUpperCase());
            case "VOLUME":
                return com.apps.quantitymeasurment.enums.VolumeUnit.valueOf(unitName.toUpperCase());
            case "TEMPERATURE":
                return com.apps.quantitymeasurment.enums.TemperatureUnit.valueOf(unitName.toUpperCase());
            default:
                throw new IllegalArgumentException("Unknown measurement type: " + measurementType);
        }
    }
}