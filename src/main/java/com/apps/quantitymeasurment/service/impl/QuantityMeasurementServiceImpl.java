package com.apps.quantitymeasurment.service.impl;

import com.apps.quantitymeasurment.IMeasurable;
import com.apps.quantitymeasurment.Quantity;
import com.apps.quantitymeasurment.dto.QuantityMeasurementDTO;
import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurment.entity.QuantityModel;
import com.apps.quantitymeasurment.enums.OperationType;
import com.apps.quantitymeasurment.exception.QuantityMeasurementException;
import com.apps.quantitymeasurment.repository.QuantityMeasurmentRepository;
import com.apps.quantitymeasurment.service.IQuantityMeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService
{

    private final QuantityMeasurmentRepository repository;

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO thisDto, QuantityDTO thatDto)
    {
        try
        {
            log.info("Validating compare request");
            validateSameType(thisDto, thatDto);

            double base1 = toBase(thisDto);
            double base2 = toBase(thatDto);

            log.info(base1+ " koti"+base2);

            boolean result = Double.compare(base1, base2) == 0;
            return saveAndReturn(thisDto, thatDto, OperationType.COMPARE,
                    String.valueOf(result), 0, null, null, false, null);
        }
        catch (Exception e)
        {
            return saveError(thisDto, thatDto, OperationType.COMPARE, e);
        }
    }

    private double toBase(QuantityDTO thisDto) {

        return getUnit(thisDto).convertToBaseUnit(thisDto.getValue());
    }

    private IMeasurable getUnit(QuantityDTO dto) {
        try {
            return IMeasurable.resolve(
                    dto.getUnitName(),
                    dto.getMeasurementType()
            );
        } catch (Exception e) {
            throw new QuantityMeasurementException(
                    "Invalid unit: " + dto.getUnitName()
            );
        }

    }

    private void validateSameType(QuantityDTO thisDto, QuantityDTO thatDto) {
        if(!thisDto.getMeasurementType().equals(thatDto.getMeasurementType()))
        {
            throw  new QuantityMeasurementException("Different measurement types not allowed");
        }
    }

    private QuantityMeasurementDTO saveError(QuantityDTO a, QuantityDTO b, OperationType op, Exception e) {
       return saveAndReturn(a, b, op, null, 0, null, null, true, e.getMessage());
    }

    private QuantityMeasurementDTO saveAndReturn(
            QuantityDTO a,
            QuantityDTO b,
            OperationType op,
            String resultString,
            double resultValue,
            String resultUnit,
            String resultType,
            boolean error,
            String message) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        a.getValue(),
                        a.getUnitName(),
                        a.getMeasurementType(),
                        b.getValue(),
                        b.getUnitName(),
                        b.getMeasurementType(),
                        op.name(),
                        resultValue,
                        resultUnit,
                        resultType
                );

        entity.setError(error);
        entity.setErrorMessage(message);
        entity.setResultString(resultString);

        repository.save(entity);

        return entityToDto(entity);
    }
    private QuantityMeasurementDTO entityToDto(QuantityMeasurementEntity entity) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

        dto.setThisValue(entity.getThisValue());
        dto.setThisUnit(entity.getThisUnit());
        dto.setThisMeasurementType(entity.getThisMeasurementType());

        dto.setThatValue(entity.getThatValue());
        dto.setThatUnit(entity.getThatUnit());
        dto.setThatMeasurementType(entity.getThatMeasurementType());

        dto.setOperation(entity.getOperation());

        dto.setResultValue(entity.getResultValue());
        dto.setResultUnit(entity.getResultUnit());
        dto.setResultMeasurementType(entity.getResultMeasurementType());

        dto.setResultString(entity.getResultString());

        dto.setError(entity.isError());
        dto.setErrorMessage(entity.getErrorMessage());

        return dto;
    }


    private QuantityMeasurementDTO handleError(String operatinType, Exception e) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(operatinType, e);
        return  new QuantityMeasurementDTO(
                entity.getThisValue(),
                entity.getThisUnit(),
                entity.getThisMeasurementType(),
                entity.getThatValue(),
                entity.getThatUnit(),
                entity.getThatMeasurementType(),
                entity.getOperation(),
                entity.getResultString(),
                entity.getResultValue(),
                entity.getResultUnit(),
                entity.getResultMeasurementType(),
                entity.getErrorMessage(),
                entity.isError()
        );

    }

    private QuantityModel<IMeasurable> toModel(QuantityDTO dto1) {
        if(dto1 == null)
        {
            throw new QuantityMeasurementException("Quantity cannot be null");
        }
        IMeasurable unit = IMeasurable.resolve(dto1.getMeasurementType(), dto1.getUnitName());
        return new QuantityModel<>(dto1.getValue(), unit);
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO targetDTO) {
        try{
            validateSameType(thisQuantityDTO, targetDTO);
            IMeasurable source = getUnit(thisQuantityDTO);
            IMeasurable target = getUnit(targetDTO);

            double base = source.convertToBaseUnit(thisQuantityDTO.getValue());
            double result = target.convertFromBaseUnit(base);

            return saveAndReturn(thisQuantityDTO, targetDTO, OperationType.CONVERT,
                    null, result, targetDTO.getUnitName(), targetDTO.getMeasurementType(), false, null);        }
        catch (Exception e)
        {
            return saveError(thisQuantityDTO, targetDTO, OperationType.CONVERT, e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return null;
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return null;
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return null;
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return null;
    }

    @Override
    public List<QuantityMeasurementDTO> getOperaionHistory(String operation) {
        return null;
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurmentsByType(String type) {
        return null;
    }

    @Override
    public long getOperationCount(String operation) {
        return 0;
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return null;
    }
}