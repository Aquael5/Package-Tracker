package br.nullexcept.package_tracker.log;

import br.nullexcept.mux.utils.Log;

public interface Loggable {
    default void log(Object... content) {
        Log.log(getClass().getSimpleName(),content);
    }
}
