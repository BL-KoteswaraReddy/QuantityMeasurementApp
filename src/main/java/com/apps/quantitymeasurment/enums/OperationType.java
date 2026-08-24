package com.apps.quantitymeasurment.enums;

public enum OperationType {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    COMPARE,
    CONVERT;

    //optional: Add display names
    public String getDisplayName(){
        return this.name().toLowerCase();
    }

}