package com.apps.quantitymeasurment.service.impl;

import com.apps.quantitymeasurment.IMeasurable;
import com.apps.quantitymeasurment.Quantity;
import com.apps.quantitymeasurment.exception.QuantityMeasurementException;
import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurment.entity.QuantityModel;
import com.apps.quantitymeasurment.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurment.service.IQuantityMeasurementService;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    // Constructor injection — repository is required, never null, and
    // never reassigned after construction.
    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    @Override
    public QuantityMeasurementEntity compare(QuantityDTO dto1, QuantityDTO dto2) {
        try {
            QuantityModel<IMeasurable> m1 = toModel(dto1);
            QuantityModel<IMeasurable> m2 = toModel(dto2);

            Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
            Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

            boolean result = q1.equals(q2);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    dto1.getValue(), dto1.getUnitName(),
                    dto2.getValue(), dto2.getUnitName(), result);
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            return handleError("COMPARE", e);
        }
    }

    @Override
    public QuantityMeasurementEntity convert(QuantityDTO dto, String targetUnitName) {
        try {
            QuantityModel<IMeasurable> model = toModel(dto);
            IMeasurable targetUnit = IMeasurable.resolve(dto.getMeasurementType(), targetUnitName);

            Quantity<IMeasurable> q = new Quantity<>(model.getValue(), model.getUnit());
            Quantity<IMeasurable> result = q.convertTo(targetUnit);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    dto.getValue(), dto.getUnitName(),
                    result.getValue(), result.getUnit().getUnitName());
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            return handleError("CONVERT", e);
        }
    }

    @Override
    public QuantityMeasurementEntity add(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        return performBinaryOperation("ADD", dto1, dto2, targetUnitName);
    }

    @Override
    public QuantityMeasurementEntity subtract(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        return performBinaryOperation("SUBTRACT", dto1, dto2, targetUnitName);
    }

    @Override
    public QuantityMeasurementEntity divide(QuantityDTO dto1, QuantityDTO dto2) {
        try {
            QuantityModel<IMeasurable> m1 = toModel(dto1);
            QuantityModel<IMeasurable> m2 = toModel(dto2);

            Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
            Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

            double result = q1.divide(q2);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    "DIVIDE", dto1.getValue(), dto1.getUnitName(),
                    dto2.getValue(), dto2.getUnitName(),
                    result, "ratio");
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            return handleError("DIVIDE", e);
        }
    }

    // ---------------- private helpers ----------------

    private QuantityMeasurementEntity performBinaryOperation(
            String operationType, QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        try {
            QuantityModel<IMeasurable> m1 = toModel(dto1);
            QuantityModel<IMeasurable> m2 = toModel(dto2);

            IMeasurable targetUnit = (targetUnitName != null)
                    ? IMeasurable.resolve(dto1.getMeasurementType(), targetUnitName)
                    : m1.getUnit();

            Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
            Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

            Quantity<IMeasurable> result = "ADD".equals(operationType)
                    ? q1.add(q2, targetUnit)
                    : q1.subtract(q2, targetUnit);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    operationType,
                    dto1.getValue(), dto1.getUnitName(),
                    dto2.getValue(), dto2.getUnitName(),
                    result.getValue(), result.getUnit().getUnitName());
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            return handleError(operationType, e);
        }
    }

    private QuantityModel<IMeasurable> toModel(QuantityDTO dto) {
        if (dto == null) {
            throw new QuantityMeasurementException("QuantityDTO cannot be null");
        }
        IMeasurable unit = IMeasurable.resolve(dto.getMeasurementType(), dto.getUnitName());
        return new QuantityModel<>(dto.getValue(), unit);
    }

    private QuantityMeasurementEntity handleError(String operationType, Exception e) {
        QuantityMeasurementEntity errorEntity =
                new QuantityMeasurementEntity(operationType, e.getMessage());
        repository.save(errorEntity);
        return errorEntity;
    }
}