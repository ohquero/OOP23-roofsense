package roofsense.model;

import java.time.ZonedDateTime;

public class LastemTemperatureProbeMeasurement extends Measurement {

    private final Double temperature;

    public LastemTemperatureProbeMeasurement(
            final ZonedDateTime timestamp, final String sensorId, final Double temperature
    ) {
        super(timestamp, sensorId);
        this.temperature = temperature;
    }

    public Double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "LastemTemperatureProbeMeasurement{" +
                "timestamp=" + getTimestamp() +
                ", sensorId=" + getSensorId() +
                ", temperature=" + temperature +
                '}';
    }

}
