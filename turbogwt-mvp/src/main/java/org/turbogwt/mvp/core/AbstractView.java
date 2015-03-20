package org.turbogwt.mvp.core;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractView<P extends Presenter> extends Composite implements View<P> {

    private P presenter;

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter() {
        if (presenter == null) {
            throw new IllegalStateException("Presenter is null. You forgot to bind it via setPresenter.");
        }
        return presenter;
    }
}
