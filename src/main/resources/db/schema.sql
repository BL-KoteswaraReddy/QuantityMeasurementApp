CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type VARCHAR(20) NOT NULL,
    measurement_type VARCHAR(20),
    operand1_value DOUBLE,
    operand1_unit VARCHAR(30),
    operand2_value DOUBLE,
    operand2_unit VARCHAR(30),
    target_unit VARCHAR(30),
    result_value DOUBLE,
    result_unit VARCHAR(30),
    is_boolean_result BOOLEAN DEFAULT FALSE,
    boolean_result BOOLEAN,
    has_error BOOLEAN DEFAULT FALSE,
    error_message VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_operation_type ON quantity_measurement_entity(operation_type);
CREATE INDEX IF NOT EXISTS idx_measurement_type ON quantity_measurement_entity(measurement_type);
CREATE INDEX IF NOT EXISTS idx_created_at ON quantity_measurement_entity(created_at);
