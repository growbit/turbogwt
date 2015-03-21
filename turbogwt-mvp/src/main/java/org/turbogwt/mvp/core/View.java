package org.turbogwt.mvp.core;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Super type of every View in the TurboGWT-MVP framework.
 *
 * @param <P> The Presenter of this View
 *
 * @author Danilo Reinert
 */
public interface View<P extends Presenter> extends IsWidget {

    /**
     * Returns the Presenter of this View.
     *
     * @throws java.lang.IllegalStateException if the Presenter is not attached yet (i.e. is null).
     *
     * @return the Presenter attached to this view
     */
    P getPresenter();

    /**
     * Attaches a Presenter of this View.
     * <p>
     * It's called by the Presenter before displaying this View.
     *
     * @param presenter the Presenter of this View.
     */
    void setPresenter(P presenter);
}
