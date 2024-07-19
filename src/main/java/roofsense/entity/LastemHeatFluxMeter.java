package roofsense.entity;

public class LastemHeatFluxMeter extends LastemSensor {

    public static final String SENSOR_ID_PREFIX = "FLUX";

    public static String buildId(final String sensorId) {
        return LastemSensor.buildId(SENSOR_ID_PREFIX, sensorId);
    }

}
