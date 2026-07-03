package com.apps.quantitymeasurment.repository;

import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);

    java.util.List<QuantityMeasurementEntity> getAllMeasurments();


    List<QuantityMeasurementEntity> getAllMeasurements();
}
