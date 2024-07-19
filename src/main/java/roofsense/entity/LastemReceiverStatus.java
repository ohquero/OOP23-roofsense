package roofsense.entity;

import java.time.ZonedDateTime;

public class LastemReceiverStatus extends Measurement {

    private final Double internalTemperature;

    private final Double supplyVoltage;

    public LastemReceiverStatus(
            final ZonedDateTime timestamp, final String receiverId, final Double internalTemperature,
            final Double supplyVoltage
    ) {
        super(timestamp, receiverId);
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
        return "LastemReceiverStatus{" +
                "timestamp=" + getTimestamp() +
                ", sensorId=" + getSensorId() +
                ", internalTemperature=" + internalTemperature +
                ", supplyVoltage=" + supplyVoltage +
                '}';
    }

}
