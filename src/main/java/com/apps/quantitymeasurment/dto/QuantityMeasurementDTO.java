package com.apps.quantitymeasurment.dto;

import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class QuantityMeasurementDTO {
    public double thisValue;
    public String thisUnit;
    public String thisMeasurementType;
    public double thatValue;
    public String thatUnit;
    public String thatMeasurementType;
    public String operation;
    public String resultString;
    public double resultValue;
    public String resultUnit;
    public String resultMeasurementType;
    public String errorMessage;

    //renaming field from isError -> error
    //Lombok generates isError() for boolean isError - Jackson strips "is"
    //and maps it to JSON key "error". On deserialization it can't find
    //"error" -> "isError" and crashes. Renaming to plain "error" fixes this.
    //
     //Fix 2. Add @JsonProperty("error") to explicitly tell jackson the JSON
    // key name - no more ambiguity between field name and getter name.
    @JsonProperty("error")
    public boolean error;

    public QuantityMeasurementDTO(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, String resultString, double resultValue, String resultUnit, String resultMeasurementType, String errorMessage, boolean error) {
    }


    public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity)
    {
      return null;
    }

    public QuantityMeasurementEntity toEntity()
    {
        return null;
    }

    public static List<QuantityMeasurementDTO> fromList()
    {
        return null;
    }

    public static List<QuantityMeasurementEntity> toEntityList()
    {
        return null;
    }
}
