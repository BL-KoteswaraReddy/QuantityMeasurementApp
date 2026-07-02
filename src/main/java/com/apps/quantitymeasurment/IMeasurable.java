package com.apps.quantitymeasurment;

public interface IMeasurable {
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();

    SupportsArithmetic supportArithmetic  = () -> true;


    default boolean supportArithmetic()
    {
        return supportArithmetic.isSupported();
    }

    default void validateOperationSupport()
    {

    }

    boolean supportsArithmetic();

    void validateOperationSupport(String operation);


}