package com.apps.quantitymeasurment.entity;

import com.apps.quantitymeasurment.IMeasurable;
import com.apps.quantitymeasurment.Quantity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuantityMeasurementEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "this_value", nullable = false)
    public double thisValue;
    @Column(name = "this_unit", nullable = false)
    public String thisUnit;
    @Column(name = "this_measurement_type", nullable = false)
    public String thisMeasurementType;
    @Column(name = "that_value", nullable = false)
    public double thatValue;
    @Column(name = "that_unit", nullable = false)
    public String thatUnit;
    @Column(name="that_measurement_type", nullable = false)
    public String thatMeasurementType;

    //e.g "COMPARE", "CONVERT", "ADD", "SUBTRACT", "DIVIDE"
    @Column(name = "operation", nullable = false)
    public String operation;

    @Column(name = "result_value")
    public  double resultValue;

    @Column(name = "result_unit")
    public String resultUnit;

    @Column(name = "result_measurement_type")
    public String resultMeasurementType;

    //for comparision result like equal or not equal
    @Column(name = "result_string")
    public String resultString;

    @Column(name = "is_error")
    public boolean isError;

    @Column(name = "error_message")
    public String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    public QuantityMeasurementEntity(String operatinType, Exception e) {
        this.operation = operatinType;
        this.isError = true;
        this.errorMessage = e.getMessage();
    }

    public QuantityMeasurementEntity(@NotNull(message = "Value cannot be null") double value, @NotNull(message = "Unit cannot be null") String unitName, @NotNull(message = "Measurement type cannot be null") @Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
            message = "Measurement type must be one of: LengthUnit, VolumeUnit, " + "WeightUnit, TemperatureUnit") String measurementType, @NotNull(message = "Value cannot be null") double value1, @NotNull(message = "Unit cannot be null") String unitName1, @NotNull(message = "Measurement type cannot be null") @Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
            message = "Measurement type must be one of: LengthUnit, VolumeUnit, " + "WeightUnit, TemperatureUnit") String measurementType1, String name, double resultValue, String resultUnit, String resultType) {

    }

    @PrePersist
    protected void onCreate(){

    }

    @PreUpdate
    private void onUpdate()
    {
        updatedAt = LocalDateTime.now();
    }

}