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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import java.util.ArrayList;

/**
 * Abstract class for every Presenter of the TurboGWT-MVP framework.
 * It's also an Activity integrating itself to the GWT A&P framework.
 * <p>
 * As an Activity, it's always willing to stop.
 * As a Presenter it provides access to its View as well as the app's EventBus and PlaceController.
 * Additionally it can hold HandlerRegistration instances (via {@link #addHandlerRegistration}) to cancel them when the
 * Activity is stopped/cancelled.
 *
 * @param <T> The View which this Presenter is attached
 *
 * @author Danilo Reinert
 */
public abstract class AbstractPresenter<T extends View> implements Activity, Presenter {

    private final T view;
    private final PlaceController placeController;
    private final ArrayList<HandlerRegistration> handlers;
    private AcceptsOneWidget panel;
    private EventBus eventBus;

    protected AbstractPresenter(T view, PlaceController placeController) {
        this.view = view;
        this.placeController = placeController;
        this.handlers = new ArrayList<HandlerRegistration>();
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

    @Override
    public void goTo(Place place) {
        placeController.goTo(place);
    }

    /**
     * Called when the user is trying to navigate away from this activity.
     * <p>
     * If it returns a non-null string, then the user is prompted with this message (via a native alert) to confirm or
     * cancel the navigation operation.
     * <p>
     * It's often used in form-like views, when the user must save the changes before moving out.
     *
     * @return A message to display to the user, e.g. to warn of unsaved work, or null to say nothing
     */
    @Override
    public String mayStop() {
        return null;
    }

    /**
     * Called when {@link #onStart} has not yet replied displayed the View, but the user has lost interest.
     * <p>
     * It's a good practice to call super.onCancel() when overriding this method to avoid leaks in the app.
     */
    @Override
    public void onCancel() {
        clear();
    }

    /**
     * Called when the View has been undisplayed.
     * All event handlers registered in this Activity (Presenter) will have been removed before this method is called.
     * <p>
     * It's a good practice to call super.onStop() when overriding this method to avoid leaks in the app.
     */
    @Override
    public void onStop() {
        clear();
    }

    /**
     * Hold a {@link HandlerRegistration} in this Activity, so when the Activity is stopped/cancelled the registrations
     * are cancelled automatically.
     *
     * @param handlerRegistration the handler registration to be cancelled when the Activity is gone.
     */
    protected void addHandlerRegistration(HandlerRegistration handlerRegistration) {
        handlers.add(handlerRegistration);
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

    protected PlaceController getPlaceController() {
        return placeController;
    }

    protected T getView() {
        return view;
    }

    /**
     * Cancels all HandlerRegistrations added via #addHandlerRegistration, detaches this Presenter from the View and
     * invalidates #getEventBus and #display methods.
     */
    @SuppressWarnings("unchecked")
    private void clear() {
        for (HandlerRegistration handler : handlers) {
            handler.removeHandler();
        }
        handlers.clear();
        view.setPresenter(null);
        panel = null;
        eventBus = null;
    }
}
