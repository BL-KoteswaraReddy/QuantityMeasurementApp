package com.apps.quantitymeasurment.service;

import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.dto.QuantityMeasurementDTO;
import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;

import java.util.List;


public interface IQuantityMeasurementService {
    QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
     QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
    QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    List<QuantityMeasurementDTO> getOperaionHistory(String operation);
    List<QuantityMeasurementDTO> getMeasurmentsByType(String type);
    long getOperationCount(String operation);
    List<QuantityMeasurementDTO> getErrorHistory();


}
