package roofsense.controller.importers;

import io.reactivex.rxjava3.core.Observable;
import roofsense.controller.fetchers.Fetcher;
import roofsense.entity.Measurement;

/**
 * An importer is an entity which fetches data from source (using a {@link Fetcher}) and parses them into
 * {@link Measurement}s.
 */
public abstract class Importer {

    public abstract Observable<Measurement> getObservable();

}
