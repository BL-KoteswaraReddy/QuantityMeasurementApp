package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.controller.QuantityMeasurementController;
import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.service.IQuantityMeasurementService;
import com.apps.quantitymeasurment.service.impl.QuantityMeasurementServiceImpl;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuantityMeasurmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurmentApplication.class, args);
    }
}