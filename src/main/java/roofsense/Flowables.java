package roofsense;

import io.reactivex.rxjava3.core.Flowable;
import roofsense.model.Measurement;

public final class Flowables {

    private Flowables() {
    }

    public static final class Measurements {

        public static Flowable<Measurement> flowable = Flowable.empty();

        private Measurements() {
        }

        /**
         * Merges the provided flowable with this flowable.
         * <p>
         * The provided flowable will be subscribed to immediately.
         *
         * @param flowable the flowable to merge to this flowable.
         */
        public static void mergeWith(final Flowable<Measurement> flowable) {
            Measurements.flowable = Flowable.merge(Measurements.flowable, flowable);
        }
    }
}
