package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.controller.QuantityMeasurementController;
import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurment.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurment.service.IQuantityMeasurementService;
import com.apps.quantitymeasurment.service.impl.QuantityMeasurementServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuantityMeasurmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurmentApplication.class, args);

        // Factory-style wiring: repository -> service -> controller
        IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        QuantityMeasurementController controller = new QuantityMeasurementController(service);

        // ---------- Length ----------
        controller.performEquality(
                new QuantityDTO(1.0, "LENGTH", "FEET"),
                new QuantityDTO(12.0, "LENGTH", "INCHES"));

        controller.performAddition(
                new QuantityDTO(1.0, "LENGTH", "FEET"),
                new QuantityDTO(12.0, "LENGTH", "INCHES"),
                "FEET");

        // ---------- Weight ----------
        controller.performConversion(
                new QuantityDTO(1.0, "WEIGHT", "KILOGRAM"), "GRAM");

        // ---------- Volume ----------
        controller.performDivision(
                new QuantityDTO(10.0, "VOLUME", "LITRE"),
                new QuantityDTO(5.0, "VOLUME", "LITRE"));

        // ---------- Temperature: unsupported operation demo ----------
        controller.performAddition(
                new QuantityDTO(100.0, "TEMPERATURE", "CELSIUS"),
                new QuantityDTO(50.0, "TEMPERATURE", "CELSIUS"),
                null); // will print an error, not crash

        // ---------- Cross-category prevention demo ----------
        controller.performEquality(
                new QuantityDTO(100.0, "LENGTH", "FEET"),
                new QuantityDTO(100.0, "WEIGHT", "KILOGRAM"));
    }
}