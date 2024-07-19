package roofsense.controller.importers;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import roofsense.controller.fetchers.Fetcher;
import roofsense.entity.*;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

public class LastemMeasurementsImporter extends Importer {

    public static final String COLUMN_NAME_SEPARATOR = "_";

    public static final String LOGGER_SUPPLY_VOLTAGE_COLUM_NAME_PREFIX = "TENS";

    private static final String LOGGER_INTERNAL_TEMPERATURE_COLUMN_NAME_PREFIX = "T_INT";

    private static final String SURFACE_TEMPERATURE_PROBE_COLUMN_NAME_PREFIX = "T_SUP";

    private static final String AIR_TEMPERATURE_PROBE_COLUMN_NAME_PREFIX = "T_ARIA";

    private static final String FLUX_PROBE_COLUMN_NAME_PREFIX = "FLUX";

    private final Observable<Measurement> observable;

    public LastemMeasurementsImporter(final Fetcher<File> fetcher) {
        this.observable = fetcher.getObservable()
                .subscribeOn(Schedulers.io())
                .map(this::parse)
                .flatMapIterable(measurements -> measurements);
    }

    private static String getSensorId(final String columnName) {
        return columnName.substring(columnName.lastIndexOf(COLUMN_NAME_SEPARATOR) + 1);
    }

    @Override
    public Observable<Measurement> getObservable() {
        return observable;
    }

    protected final Collection<Measurement> parse(final File fetchedFile) {
        final var table = Table.read().usingOptions(
                CsvReadOptions.builder(fetchedFile)
                        .dateTimeFormat(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss", Locale.ITALY))
                        .build()
        );

        // Extracting measurements
        return table.stream().flatMap(row -> {
            final var timestamp = ZonedDateTime.of(row.getDateTime("Timestamp"), ZoneId.of("Europe/Rome"));

            final var measurements = new ArrayList<Measurement>();

            measurements.add(
                    new LastemReceiverStatus(
                            timestamp,
                            LastemReceiver.buildId("0"),
                            row.getDouble("TempINTerna"),
                            row.getDouble("TENSAlim")
                    )
            );

            // Extracting loggers ids
            final var loggersIds = row.columnNames().stream()
                    .filter(columnName -> columnName.startsWith(LOGGER_INTERNAL_TEMPERATURE_COLUMN_NAME_PREFIX + COLUMN_NAME_SEPARATOR))
                    .map(columnName -> columnName.substring(LOGGER_INTERNAL_TEMPERATURE_COLUMN_NAME_PREFIX.length() + 1))
                    .toList();

            // Collecting measurements of sensors attached to each logger
            loggersIds.forEach(loggerId -> {
                measurements.add(
                        new LastemLoggerStatus(
                                timestamp,
                                LastemLogger.buildId(loggerId),
                                row.getDouble(LOGGER_SUPPLY_VOLTAGE_COLUM_NAME_PREFIX + COLUMN_NAME_SEPARATOR + loggerId),
                                row.getDouble(LOGGER_INTERNAL_TEMPERATURE_COLUMN_NAME_PREFIX + COLUMN_NAME_SEPARATOR + loggerId)
                        )
                );

                row.columnNames().stream()
                        .filter(columnName -> columnName.startsWith(SURFACE_TEMPERATURE_PROBE_COLUMN_NAME_PREFIX + COLUMN_NAME_SEPARATOR + loggerId))
                        .forEach(columnName -> measurements.add(
                                new LastemTemperatureProbeMeasurement(
                                        timestamp,
                                        LastemTemperatureProbe.buildId(
                                                loggerId,
                                                getSensorId(columnName),
                                                LastemTemperatureProbe.Positioning.SURFACE
                                        ),
                                        row.getDouble(columnName)
                                )
                        ));

                row.columnNames().stream()
                        .filter(columnName -> columnName.startsWith(AIR_TEMPERATURE_PROBE_COLUMN_NAME_PREFIX + COLUMN_NAME_SEPARATOR + loggerId))
                        .forEach(columnName -> measurements.add(
                                new LastemTemperatureProbeMeasurement(
                                        timestamp,
                                        LastemTemperatureProbe.buildId(
                                                loggerId,
                                                getSensorId(columnName),
                                                LastemTemperatureProbe.Positioning.AIR
                                        ),
                                        row.getDouble(columnName)
                                )
                        ));

                row.columnNames().stream()
                        .filter(columnName -> columnName.startsWith(FLUX_PROBE_COLUMN_NAME_PREFIX + COLUMN_NAME_SEPARATOR + loggerId))
                        .forEach(columnName -> measurements.add(
                                new LastemHeatFluxMeterMeasurement(
                                        timestamp,
                                        LastemHeatFluxMeter.buildId(getSensorId(columnName)),
                                        row.getDouble(columnName)
                                )
                        ));
            });

            return measurements.stream();
        }).collect(Collectors.toSet());
    }

}
