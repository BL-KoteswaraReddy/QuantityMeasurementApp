package com.apps.quantitymeasurment.entity;

import com.apps.quantitymeasurment.IMeasurable;
import com.apps.quantitymeasurment.Quantity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable
{
        private static final long serialVersionUID = 1L;

        private String operationType;    // "COMPARE", "CONVERT", "ADD", "SUBTRACT", "DIVIDE"
        private double operand1Value;
        private String operand1Unit;
        private double operand2Value;    // unused for single-operand ops (e.g. conversion)
        private String operand2Unit;
        private String resultUnit;
        private double resultValue;
        private boolean booleanResult;   // used for comparison operations
        private boolean hasError;
        private String errorMessage;

        // Constructor: binary arithmetic (add/subtract/divide) success
        public QuantityMeasurementEntity(String operationType,
                                         double operand1Value, String operand1Unit,
                                         double operand2Value, String operand2Unit,
                                         double resultValue, String resultUnit) {
            this.operationType = operationType;
            this.operand1Value = operand1Value;
            this.operand1Unit = operand1Unit;
            this.operand2Value = operand2Value;
            this.operand2Unit = operand2Unit;
            this.resultValue = resultValue;
            this.resultUnit = resultUnit;
            this.hasError = false;
        }

        // Constructor: comparison success
        public QuantityMeasurementEntity(double operand1Value, String operand1Unit,
                                         double operand2Value, String operand2Unit,
                                         boolean booleanResult) {
            this.operationType = "COMPARE";
            this.operand1Value = operand1Value;
            this.operand1Unit = operand1Unit;
            this.operand2Value = operand2Value;
            this.operand2Unit = operand2Unit;
            this.booleanResult = booleanResult;
            this.hasError = false;
        }

        // Constructor: single-operand conversion success
        public QuantityMeasurementEntity(double operand1Value, String operand1Unit,
                                         double resultValue, String resultUnit) {
            this.operationType = "CONVERT";
            this.operand1Value = operand1Value;
            this.operand1Unit = operand1Unit;
            this.resultValue = resultValue;
            this.resultUnit = resultUnit;
            this.hasError = false;
        }

        // Constructor: error case
        public QuantityMeasurementEntity(String operationType, String errorMessage) {
            this.operationType = operationType;
            this.hasError = true;
            this.errorMessage = errorMessage;
        }

        public String getOperationType() { return operationType; }
        public double getOperand1Value() { return operand1Value; }
        public String getOperand1Unit() { return operand1Unit; }
        public double getOperand2Value() { return operand2Value; }
        public String getOperand2Unit() { return operand2Unit; }
        public String getResultUnit() { return resultUnit; }
        public double getResultValue() { return resultValue; }
        public boolean getBooleanResult() { return booleanResult; }
        public boolean hasError() { return hasError; }
        public String getErrorMessage() { return errorMessage; }

        @Override
        public String toString() {
            if (hasError) {
                return "[" + operationType + "] ERROR: " + errorMessage;
            }
            if ("COMPARE".equals(operationType)) {
                return operand1Value + " " + operand1Unit + " == "
                        + operand2Value + " " + operand2Unit + " ? " + booleanResult;
            }
            if ("CONVERT".equals(operationType)) {
                return operand1Value + " " + operand1Unit + " -> " + resultValue + " " + resultUnit;
            }
            return operand1Value + " " + operand1Unit + " " + operationType + " "
                    + operand2Value + " " + operand2Unit + " = " + resultValue + " " + resultUnit;
        }
    }
