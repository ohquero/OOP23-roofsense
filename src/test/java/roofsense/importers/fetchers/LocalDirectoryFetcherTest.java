package roofsense.importers.fetchers;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalDirectoryFetcherTest {

    @Test
    void test_nullFolder() {
        assertThrows(NullPointerException.class, () -> new LocalDirectoryFetcher(null));
    }

    @Test
    void test_nonExistentFolder() {
        assertThrows(UncheckedIOException.class, () -> new LocalDirectoryFetcher(Path.of("nonExistentFolder")));
    }

    @Test
    void test_unreadableFolder() {
        assertThrows(
                UncheckedIOException.class,
                () -> new LocalDirectoryFetcher(Path.of("/root"))
        );
    }

    @Test
    void test_fileInsteadOfFolder() {
        assertThrows(
                UncheckedIOException.class,
                () -> new LocalDirectoryFetcher(Path.of("/etc/localtime"))
        );
    }

    @Test
    void test_getObservable_emptyFolder() throws InterruptedException, IOException {
        // Creating empty in-memory directory
        final var filesDir = Files.createTempDirectory(null);

        // Creating a fetcher connected to the measurements files directory
        // All the files emitted by the fetcher will be stored in a list
        final var filesList = new LinkedList<>();
        final var observable = (new LocalDirectoryFetcher(filesDir)).getObservable();
        final var disposable = observable.subscribeOn(Schedulers.io()).subscribe(filesList::add);
        Thread.sleep(1000);
        disposable.dispose();

        // Assert
        assertEquals(0, filesList.size());
    }

    @Test
    void test_getObservable_filledFolder() throws InterruptedException {
        // Creating in-memory directory where test files will be copied
        final var filesDir = Path.of(ClassLoader.getSystemResource("lastem_measurements_files").getPath());

        // Creating a fetcher connected to the measurements files directory
        // All the files emitted by the fetcher will be stored in a list
        final var filesList = new LinkedList<>();
        final var observable = (new LocalDirectoryFetcher(filesDir)).getObservable();
        final var disposable = observable.subscribeOn(Schedulers.io()).subscribe(filesList::add);
        Thread.sleep(1000);

        // Unsubscribing from the observable
        disposable.dispose();

        // Assert
        assertEquals(Objects.requireNonNull(filesDir.toFile().listFiles()).length, filesList.size());
    }

    @Test
    void test_getObservable_folderFilledDuringFetcherExecution() throws IOException, InterruptedException {
        // Creating empty in-memory directory
        final var filesDir = Files.createTempDirectory(null);

        // Creating a fetcher connected to the measurements files directory
        // All the files emitted by the fetcher will be stored in a list
        final var emittedFilesList = new LinkedList<>();
        final var observable = (new LocalDirectoryFetcher(filesDir)).getObservable();
        final var disposable = observable.subscribeOn(Schedulers.io()).subscribe(emittedFilesList::add);
        Thread.sleep(1000);

        // Creating new files in the directory
        final Path[] files = {
                Files.createTempFile(filesDir, "1", ".txt"),
                Files.createTempFile(filesDir, "2", ".csv"),
                Files.createTempFile(filesDir, "3", ".png")
        };
        Thread.sleep(1000);

        // Unsubscribing from the observable
        disposable.dispose();

        // Assert
        assertEquals(files.length, emittedFilesList.size());
    }

}