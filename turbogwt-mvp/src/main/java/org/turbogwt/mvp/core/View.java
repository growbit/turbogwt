package org.turbogwt.mvp.core;

import com.google.gwt.user.client.ui.IsWidget;

public interface View<P extends Presenter> extends IsWidget {
    P getPresenter();
    void setPresenter(P presenter);
}
