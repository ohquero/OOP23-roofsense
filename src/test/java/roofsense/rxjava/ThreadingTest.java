package roofsense.rxjava;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadingTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadingTest.class);
    private static final long OBSERVERS_COMPUTATION_DELAY_MILLIS = 3000L;

    /**
     * This test demonstrates how a cold Observable with two vanilla Observer works.
     * When the first Observer subscribes, the Observable starts emitting items. Until the Observable completes the
     * code won't go on, so the second Observer will be subscribed only after the first Observer completes.
     * Once the second Observer subscribes, the Observable will start emitting items again, but different ones since
     * the Observable was already completed.
     */
    @Test
    public void vanillaObservables_vanillaObserver_test() {
        var observable = getObservable();

        var observer0 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 0 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 0 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 0 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 0 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 0 - Received onComplete signal")
        );

        assertTrue(observer0.isDisposed());

        var observer1 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 1 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 1 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 1 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 1 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 1 - Received onComplete signal")
        );

        assertTrue(observer1.isDisposed());
    }

    /**
     * This test that when using the {@link Observable#subscribeOn(Scheduler)} operator a new thread is created for
     * each Observer.
     * Per each Observer also an Observable is created, so whole computation is done in parallel.
     */
    @Test
    public void subscribeOnObservable_VanillaObserver_test() {
        var observable = getObservable().subscribeOn(Schedulers.newThread());

        var observer0 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 0 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 0 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 0 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 0 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 0 - Received onComplete signal")
        );

        assertFalse(observer0.isDisposed());

        var observer1 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 1 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 1 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 1 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 1 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 1 - Received onComplete signal")
        );

        assertFalse(observer1.isDisposed());

        // Wait until observer0 and observer1 are disposed
        while (!observer0.isDisposed() || !observer1.isDisposed()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This test that when using the {@link Observable#observeOn(Scheduler)} operator a new thread is created for
     * each Observer.
     * But with this operator the Observable is still shared between the observers and is executed in the same thread
     * for both the Observers, meaning that until it completes the code won't go on, resulting that the second
     * Observer will be subscribed only after the first Observer completes.
     */
    @Test
    public void vanillaObservable_observeOnObserver_test() {
        var observable = getObservable().observeOn(Schedulers.newThread());

        var observer0 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 0 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 0 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 0 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 0 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 0 - Received onComplete signal")
        );

        assertFalse(observer0.isDisposed());

        var observer1 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 1 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 1 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 1 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 1 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 1 - Received onComplete signal")
        );

        assertFalse(observer1.isDisposed());

        // Wait until observer0 and observer1 are disposed
        while (!observer0.isDisposed() || !observer1.isDisposed()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This test that when using the {@link Observable#subscribeOn(Scheduler)} operator a new thread is created for
     * each Observer.
     * Per each Observer also an Observable is created, so whole computation is done in parallel.
     */
    @Test
    public void subscribeOnObservable_observeOnObserver_test() {
        var observable = getObservable().subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());

        var observer0 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 0 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 0 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 0 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 0 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 0 - Received onComplete signal")
        );

        assertFalse(observer0.isDisposed());

        var observer1 = observable.subscribe(
                o -> {
                    LOGGER.info("OBSERVER 1 - Received onNext signal with payload \"" + o + "\"");
                    LOGGER.info("OBSERVER 1 - Doing heavy computation...");
                    Thread.sleep(OBSERVERS_COMPUTATION_DELAY_MILLIS);
                    LOGGER.info("OBSERVER 1 - Done with heavy computation");
                },
                e -> LOGGER.error("OBSERVER 1 - Received onError signal with payload \"" + e + "\""),
                () -> LOGGER.info("OBSERVER 1 - Received onComplete signal")
        );

        assertFalse(observer1.isDisposed());

        // Wait until observer0 and observer1 are disposed
        while (!observer0.isDisposed() || !observer1.isDisposed()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Observable<Integer> getObservable() {
        return Observable.fromCallable(() -> {
            LOGGER.info("EMITTER - Going to a value which requires a lot of computation...");
            Thread.sleep(1000);
            var randomInt = (int) (Math.random() * 100);
            LOGGER.info("EMITTER - Emitting \"" + randomInt + "\"");
            return randomInt;
        });
    }
}
