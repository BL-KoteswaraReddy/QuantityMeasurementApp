package com.apps.quantitymeasurment.dto;

import com.apps.quantitymeasurment.enums.LengthUnit;
import com.apps.quantitymeasurment.enums.TemperatureUnit;
import com.apps.quantitymeasurment.enums.VolumeUnit;
import com.apps.quantitymeasurment.enums.WeightUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.logging.Logger;

@Data
@RequiredArgsConstructor
public class QuantityDTO {

    //Logger for debugging purpose
    private static final Logger logger = Logger.getLogger(QuantityDTO.class.getName());


    @NotNull(message = "Value cannot be null")
    @Schema(example = "1.0")
    private double value;

    @NotNull(message = "Unit cannot be null")
    @com.fasterxml.jackson.annotation.JsonProperty("unit")
    @Schema(example = "FEET", allowableValues = {
            "FEET", "INCHES", "YEARS", "CENTIMETERS", "LITRE", "MILLILITRE", "GALLON", "MILLIGRAM", "GRAM", "KILOGRAM", "POUND", "TONNE",
            "CELSIUS", "FAHRENHEIT"
    })
    private String unitName;        // "FEET", "KILOGRAM", "LITRE", "CELSIUS", etc.

    @NotNull(message = "Measurement type cannot be null")
    @Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
            message = "Measurement type must be one of: LengthUnit, VolumeUnit, " + "WeightUnit, TemperatureUnit")
    private String measurementType;

    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnit() {
        logger.info("Validating unit :" + unitName + "for measurement type: " + measurementType);
        try {
            switch (measurementType) {
                case "LengthUnit":
                    LengthUnit.valueOf(unitName);
                    break;
                case "VolumeUnit":
                    VolumeUnit.valueOf(unitName);
                    break;
                case "WeightUnit":
                    WeightUnit.valueOf(unitName);
                case "TemperatureUnit":
                    TemperatureUnit.valueOf(unitName);
                    break;
                default:
                    return false;

            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}