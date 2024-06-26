package roofsense.model;

public class LastemLogger extends LastemSensor {

    public static final String LOGGER_ID_PREFIX = "LOGGER";

    public static String buildId(final String id) {
        return LastemSensor.buildId(LOGGER_ID_PREFIX, id);
    }

}
