package roofsense.entity;

public enum LastemTemperatureProbe {
    ;

    public static String buildId(final String loggerId, final String probeId, final Positioning positioning) {
        return LastemSensor.buildId(positioning.typeId, loggerId + "-" + probeId);
    }

    public enum Positioning {
        SURFACE("T_SUP"),
        AIR("T_ARIA");

        private final String typeId;

        Positioning(final String typeId) {
            this.typeId = typeId;
        }
    }

}
