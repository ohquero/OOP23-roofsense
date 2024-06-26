package roofsense.importers;

import io.reactivex.rxjava3.core.Observable;
import roofsense.importers.fetchers.Fetcher;
import roofsense.model.Measurement;

/**
 * An importer is an entity which fetches data from source (using a {@link Fetcher}) and parses them into
 * {@link Measurement}s.
 */
public abstract class Importer {

    public abstract Observable<Measurement> getObservable();

}
