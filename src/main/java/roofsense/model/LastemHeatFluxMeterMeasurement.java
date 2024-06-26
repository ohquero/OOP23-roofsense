package roofsense.model;

import java.time.ZonedDateTime;

public class LastemHeatFluxMeterMeasurement extends Measurement {

    private final Double energy;

    public LastemHeatFluxMeterMeasurement(
            final ZonedDateTime timestamp, final String sensorId, final Double energy
    ) {
        super(timestamp, sensorId);
        this.energy = energy;
    }

    public Double getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "LastemHeatFluxMeterMeasurement{" +
                "timestamp=" + getTimestamp() +
                ", sensorId=" + getSensorId() +
                ", energy=" + energy +
                '}';
    }

}
