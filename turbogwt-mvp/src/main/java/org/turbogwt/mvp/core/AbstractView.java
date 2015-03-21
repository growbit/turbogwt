package org.turbogwt.mvp.core;

import com.google.gwt.user.client.ui.Composite;

/**
 * Abstract class for every View in the TurboGWT-MVP framework.
 * <p>
 * It provides access to its Presenter via the {@link #getPresenter} method.
 *
 * @param <P> the Presenter that is attached to this View, whenever it is displayed
 *
 * @author Danilo Reinert
 */
public abstract class AbstractView<P extends Presenter> extends Composite implements View<P> {

    private P presenter;

    public P getPresenter() {
        if (presenter == null) {
            throw new IllegalStateException("Presenter is null. You forgot to bind it via setPresenter.");
        }
        return presenter;
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }
}
