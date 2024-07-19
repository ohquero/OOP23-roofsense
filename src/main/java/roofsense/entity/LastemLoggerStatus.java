package roofsense.entity;

import java.time.ZonedDateTime;

public class LastemLoggerStatus extends Measurement {

    private final Double internalTemperature;

    private final Double supplyVoltage;

    public LastemLoggerStatus(
            final ZonedDateTime timestamp, final String loggerId, final Double internalTemperature,
            final Double supplyVoltage
    ) {
        super(timestamp, loggerId);
        this.internalTemperature = internalTemperature;
        this.supplyVoltage = supplyVoltage;
    }

    public Double getInternalTemperature() {
        return internalTemperature;
    }

    public Double getSupplyVoltage() {
        return supplyVoltage;
    }

    @Override
    public String toString() {
        return "LastemLoggerStatus{" +
                "timestamp=" + getTimestamp() +
                ", sensorId=" + getSensorId() +
                ", internalTemperature=" + internalTemperature +
                ", supplyVoltage=" + supplyVoltage +
                '}';
    }

}
