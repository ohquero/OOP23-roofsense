package roofsense.controller.importers;

import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import roofsense.controller.fetchers.Fetcher;
import roofsense.entity.LastemHeatFluxMeterMeasurement;
import roofsense.entity.LastemLoggerStatus;
import roofsense.entity.LastemReceiverStatus;
import roofsense.entity.LastemTemperatureProbeMeasurement;

import java.io.File;
import java.nio.file.Path;

class LastemMeasurementsImporterTest {

    private static final Fetcher<File> DUMMY_OBSERVABLE = new Fetcher<>() {
        @Override
        public Observable<File> getObservable() {
            return Observable.never();
        }
    };

    private static File getCsvFile(final String suffix) {
        return Path.of(
                ClassLoader.getSystemResource("lastem_measurements_files/221014213100-" + suffix + ".csv")
                        .getPath()
        ).toFile();
    }

    @Test
    void test_parse_fileWithOneLogger() {
        final var importer = new LastemMeasurementsImporter(DUMMY_OBSERVABLE);

        final var measurements = importer.parse(getCsvFile("one_logger"));
        Assertions.assertEquals(80, measurements.size());
        Assertions.assertEquals(10, measurements.stream().filter(m -> m instanceof LastemReceiverStatus).count());
        Assertions.assertEquals(10, measurements.stream().filter(m -> m instanceof LastemLoggerStatus).count());
        Assertions.assertEquals(
                60,
                measurements.stream().filter(m -> m instanceof LastemTemperatureProbeMeasurement).count()
        );
        Assertions.assertEquals(
                10,
                measurements.stream().filter(m -> m instanceof LastemLoggerStatus).count()
        );
        Assertions.assertEquals(
                10,
                measurements.stream().filter(m -> m instanceof LastemReceiverStatus).count()
        );
    }

    @Test
    void test_parse_fileWithTwoLoggers() {
        final var importer = new LastemMeasurementsImporter(DUMMY_OBSERVABLE);

        final var measurements = importer.parse(getCsvFile("two_loggers"));
        Assertions.assertEquals(110, measurements.size());
        Assertions.assertEquals(10, measurements.stream().filter(m -> m instanceof LastemReceiverStatus).count());
        Assertions.assertEquals(20, measurements.stream().filter(m -> m instanceof LastemLoggerStatus).count());
        Assertions.assertEquals(
                70,
                measurements.stream().filter(m -> m instanceof LastemTemperatureProbeMeasurement).count()
        );
        Assertions.assertEquals(
                20,
                measurements.stream().filter(m -> m instanceof LastemLoggerStatus).count()
        );
        Assertions.assertEquals(
                10,
                measurements.stream().filter(m -> m instanceof LastemReceiverStatus).count()
        );
        Assertions.assertEquals(
                10,
                measurements.stream().filter(m -> m instanceof LastemHeatFluxMeterMeasurement).count()
        );
    }

    @Test
    void test_getObservable_fileWithTwoLoggers() {
        final var importer = new LastemMeasurementsImporter(new Fetcher<>() {
            @Override
            public Observable<File> getObservable() {
                return Observable.just(getCsvFile("two_loggers"));
            }
        });

        final var measurements = importer.getObservable().toList().blockingGet();
        Assertions.assertEquals(110, measurements.size());
    }

}