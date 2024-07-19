package roofsense.controller.fetchers;

import io.reactivex.rxjava3.core.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.Objects;

public final class LocalDirectoryFetcher extends Fetcher<File> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDirectoryFetcher.class);

    private final Path directoryPath;

    public LocalDirectoryFetcher(final Path directoryPath) {
        Objects.requireNonNull(directoryPath, "The directory path cannot be null.");
        if (!Files.exists(directoryPath)) {
            throw new UncheckedIOException(new IOException("The directory \"" + directoryPath + "\" does not exist."));
        }
        if (!Files.isDirectory(directoryPath)) {
            throw new UncheckedIOException(new IOException("The path \"" + directoryPath + "\" is not a directory."));
        }
        if (!Files.isReadable(directoryPath)) {
            throw new UncheckedIOException(new IOException("The directory \"" + directoryPath + "\" is not readable."));
        }

        this.directoryPath = directoryPath;
    }

    /**
     * Creates an Observable that emits files in the fetched directory. Formerly it emits the files already present in
     * the directory and then emits new files as they are created.
     *
     * @return the Observable that emits files in the fetched directory.
     */
    @Override
    public Observable<File> getObservable() {
        // Registering the directory to the WatchService
        final WatchService directoryWatcher;
        try {
            directoryWatcher = FileSystems.getDefault().newWatchService();
            directoryPath.register(directoryWatcher, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        // Creating Observable that emits files in the directory
        return Observable.create(emitter -> {
            // Emitting all the files currently in the directory
            try (final var pathStream = Files.list(directoryPath)) {
                pathStream.filter(Files::isRegularFile).forEach(file -> emitter.onNext(file.toFile()));
            } catch (final IOException e) {
                emitter.onError(e);
            }

            // Emitting new files as they are created until the emitter is cancelled
            while (!emitter.isDisposed()) {
                final WatchKey key;

                // Waiting for the next filesystem event
                try {
                    key = directoryWatcher.take();
                } catch (final InterruptedException exception) {
                    // If the Observable is disposed while waiting for the next filesystem event, the observable must
                    // be terminated
                    break;
                }

                // Pushing the new file to the Observable
                for (final WatchEvent<?> event : key.pollEvents()) {
                    final WatchEvent.Kind<?> kind = event.kind();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    @SuppressWarnings("unchecked") final WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    emitter.onNext(directoryPath.resolve(ev.context()).toFile());
                }

                // Resetting the key because it is not valid anymore after the events have been processed
                if (!key.reset()) {
                    // If the key is no longer valid, the directory is no longer accessible
                    emitter.onError(
                            new IOException("The directory \"" + directoryPath + "\" is no longer accessible."));
                }
            }
            LOGGER.info("Directory fetcher for {} has been disposed.", directoryPath);
        });
    }


}
