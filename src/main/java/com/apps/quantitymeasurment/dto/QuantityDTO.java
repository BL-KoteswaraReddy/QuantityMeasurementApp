package com.apps.quantitymeasurment.dto;

public class QuantityDTO {

    private double value;
    private String measurementType; // "LENGTH", "WEIGHT", "VOLUME", "TEMPERATURE"
    private String unitName;        // "FEET", "KILOGRAM", "LITRE", "CELSIUS", etc.

    public QuantityDTO() {
        // no-arg constructor for frameworks / serialization
    }

    public QuantityDTO(double value, String measurementType, String unitName) {
        this.value = value;
        this.measurementType = measurementType;
        this.unitName = unitName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return value + " " + unitName + " (" + measurementType + ")";
    }
}