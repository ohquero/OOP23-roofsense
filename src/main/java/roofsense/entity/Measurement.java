package roofsense.entity;

import java.time.ZonedDateTime;
import java.util.Objects;

public abstract class Measurement {

    private final ZonedDateTime timestamp;

    private final String sensorId;

    public Measurement(final ZonedDateTime timestamp, final String sensorId) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getSensorId() {
        return sensorId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Measurement that = (Measurement) o;
        return Objects.equals(getTimestamp(), that.getTimestamp()) && Objects.equals(getSensorId(), that.getSensorId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getSensorId());
    }

}
