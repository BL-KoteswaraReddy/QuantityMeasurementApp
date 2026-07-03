package com.apps.quantitymeasurment.service;

import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {
    QuantityMeasurementEntity compare(QuantityDTO dto1, QuantityDTO dto2);
    QuantityMeasurementEntity convert(QuantityDTO dto, String targetUnitName);
    QuantityMeasurementEntity add(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName);
    QuantityMeasurementEntity subtract(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName);
    QuantityMeasurementEntity divide(QuantityDTO dto1, QuantityDTO dto2);
}