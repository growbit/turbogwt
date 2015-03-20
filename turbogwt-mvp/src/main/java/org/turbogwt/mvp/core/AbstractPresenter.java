/*
 * Copyright 2015 Grow Bit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.turbogwt.mvp.core;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class AbstractPresenter<T extends View> extends AbstractActivity implements Presenter {

    private final T view;
    private final PlaceController placeController;
    private AcceptsOneWidget panel;
    private EventBus eventBus;

    protected AbstractPresenter(T view, PlaceController placeController) {
        this.view = view;
        this.placeController = placeController;
    }

    /**
     * Called when the Activity (Presenter) is about to be displayed.
     * <p>
     * Here, the Presenter must set up its View for the user.
     * <p>
     * At this point, the event bus is set and is accessible from {@link AbstractPresenter#getEventBus}.
     * <p>
     * When the View is ready, Presenter must display it by calling {@link AbstractPresenter#display()}.
     * <p>
     * Notice that the Presenter may display the View only after receiving a response from an async request.
     * <p>
     * Any handlers attached to the provided event bus will be de-registered when the activity is stopped, so activities
     * will rarely need to hold on to the {@link com.google.gwt.event.shared.HandlerRegistration HandlerRegistration}
     * instances returned by {@link com.google.gwt.event.shared.EventBus#addHandler}.
     */
    public abstract void onStart();

    @Override
    @SuppressWarnings("unchecked")
    public final void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
        this.eventBus = eventBus;
        this.panel = panel;
        view.setPresenter(this);
        onStart();
    }

    /**
     * Called when {@link #onStart} has not yet replied displayed the View, but the user has lost interest.
     * <p>
     * It's a good practice to call super.onCancel() when overriding this method.
     */
    @Override
    public void onCancel() {
        clear();
    }

    /**
     * Called when the View has been undisplayed.
     * All event handlers registered in this Activity (Presenter) will have been removed before this method is called.
     * <p>
     * It's a good practice to call super.onStop() when overriding this method.
     */
    @Override
    public void onStop() {
        clear();
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    protected T getView() {
        return view;
    }

    protected PlaceController getPlaceController() {
        return placeController;
    }

    /**
     * Retrieve the event bus instance associated to a specific place request event.
     * <p>
     * It is available only when the Activity is starting, i.e.,
     * after the {@link AbstractPresenter#onStart()) method is triggered.
     *
     * @return the event bus
     */
    protected EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Presents the view to the user and completes the start process of the Activity.
     * Must be called in the {@link AbstractPresenter#onStart()) method.
     */
    protected void display() {
        if (panel == null) {
            throw new IllegalStateException("The view is not starting yet." +
                    " This method must be called only in the #onStart method.");
        }
        panel.setWidget(view);
    }

    /**
     * Detaches this Presenter from the View and invalidates #getEventBus and #display methods.
     */
    @SuppressWarnings("unchecked")
    private void clear() {
        view.setPresenter(null);
        panel = null;
        eventBus = null;
    }
}
