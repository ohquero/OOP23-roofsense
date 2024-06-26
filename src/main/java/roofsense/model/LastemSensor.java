package roofsense.model;

public class LastemSensor {

    public static String buildId(final String sensorTypeId, final String sensorId) {
        return "LASTEM-" + sensorTypeId + "-" + sensorId;
    }

}
