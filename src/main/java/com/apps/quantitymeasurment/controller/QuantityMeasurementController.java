package com.apps.quantitymeasurment.controller;

import com.apps.quantitymeasurment.dto.QuantityDTO;
import com.apps.quantitymeasurment.dto.QuantityInputDTO;
import com.apps.quantitymeasurment.dto.QuantityMeasurementDTO;
import com.apps.quantitymeasurment.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.QualifiedNameable;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/v1/measurements")
@RequiredArgsConstructor
public class QuantityMeasurementController {

    //Logger for logging information and errors in the controller
    private static final Logger logger = Logger.getLogger(QuantityMeasurementController.class.getName());

    public final IQuantityMeasurementService service;

    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> performComparision(@Valid @RequestBody QuantityInputDTO input) {
                return ResponseEntity.ok(service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> performAdd(@Valid @RequestBody QuantityInputDTO input) {
         return ResponseEntity.ok(service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }
//
//    @PostMapping("/subtract")
//    public ResponseEntity<QuantityMeasurementDTO> performSubtraction() {
//
//    }
//
//    @PostMapping("/subtract-with-target-unit")
//    public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit() {
//
//    }

}