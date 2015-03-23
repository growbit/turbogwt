package org.turbogwt.mvp;

import com.google.gwt.place.shared.Place;

/**
 * Super type of every Presenter in the TurboGWT-MVP framework.
 *
 * @author Danilo Reinert
 */
public interface Presenter {

    /**
     * Navigates to another place in the app.
     *
     * @param place the place to navigate to
     */
    void goTo(Place place);
}
