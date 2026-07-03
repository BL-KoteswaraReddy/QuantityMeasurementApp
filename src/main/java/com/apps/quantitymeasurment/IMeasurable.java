package com.apps.quantitymeasurment;

public interface IMeasurable {

    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();

    IMeasurable getUnitInstance(String unitName);

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    boolean supportArithmetic();

    void validateOperationSupport();

    default boolean supportsArithmetic() {
        return true;
    }

    default void validateOperationSupport(String operation) {
        // no-op by default
    }

    /**
     * Identifies which measurement category this unit belongs to
     * (e.g. "LENGTH", "WEIGHT"). Used by the service layer to validate
     * that a QuantityDTO's declared category matches its unit.
     */
    String getMeasurementType();

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