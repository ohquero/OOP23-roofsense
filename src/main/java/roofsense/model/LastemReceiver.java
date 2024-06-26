package roofsense.model;

public class LastemReceiver extends LastemSensor {

    private static final String RECEIVER_ID_PREFIX = "RECEIVER";

    public static String buildId(final String id) {
        return LastemSensor.buildId(RECEIVER_ID_PREFIX, id);
    }

}
