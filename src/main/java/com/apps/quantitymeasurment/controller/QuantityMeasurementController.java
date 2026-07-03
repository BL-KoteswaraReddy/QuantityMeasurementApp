package com.apps.quantitymeasurment.controller;

import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurment.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.service = service;
    }

    // Maps conceptually to: POST /api/quantity/compare
    public void performEquality(QuantityDTO dto1, QuantityDTO dto2) {
        QuantityMeasurementEntity result = service.compare(dto1, dto2);
        displayResult(result);
    }

    // Maps conceptually to: POST /api/quantity/convert
    public void performConversion(QuantityDTO dto, String targetUnitName) {
        QuantityMeasurementEntity result = service.convert(dto, targetUnitName);
        displayResult(result);
    }

    // Maps conceptually to: POST /api/quantity/add
    public void performAddition(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        QuantityMeasurementEntity result = service.add(dto1, dto2, targetUnitName);
        displayResult(result);
    }

    // Maps conceptually to: POST /api/quantity/subtract
    public void performSubtraction(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        QuantityMeasurementEntity result = service.subtract(dto1, dto2, targetUnitName);
        displayResult(result);
    }

    // Maps conceptually to: POST /api/quantity/divide
    public void performDivision(QuantityDTO dto1, QuantityDTO dto2) {
        QuantityMeasurementEntity result = service.divide(dto1, dto2);
        displayResult(result);
    }

    private void displayResult(QuantityMeasurementEntity entity) {
        if (entity.hasError()) {
            System.out.println("ERROR [" + entity.getOperationType() + "]: " + entity.getErrorMessage());
        } else {
            System.out.println(entity);
        }
    }
}