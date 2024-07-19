package roofsense.controller.fetchers;

import io.reactivex.rxjava3.core.Observable;

/**
 * A fetcher is an entity which fetches data from a specific type of source and publishes them in the message queue.
 *
 * @param <T> the type of the data fetched.
 */
public abstract class Fetcher<T> {

    public abstract Observable<T> getObservable();

}
