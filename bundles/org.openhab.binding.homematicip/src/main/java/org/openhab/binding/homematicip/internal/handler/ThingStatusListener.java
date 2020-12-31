/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.homematicip.internal.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.homematicip.internal.model.HomematicIPThing;

/**
 * The {@link ThingStatusListener} is notified when a thing status has changed, removed, added
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-31
 */
@NonNullByDefault
public interface ThingStatusListener<T extends HomematicIPThing> {

    /**
     * This method returns the id of the listener
     *
     * @return
     */
    String getId();

    /**
     * This method is called whenever the state of the given thing has changed.
     *
     * @param thing the complete changed thing
     * @return true if state was accepted
     */
    boolean onStateChanged(T thing);

    /**
     * This method is called whenever a thing is removed.
     */
    void onRemoved();

    /**
     * This method is called whenever a thing is reported as gone.
     */
    void onGone();

    /**
     * This method is called whenever a thing is added.
     *
     * @param thing The thing which is added.
     */
    void onAdded(T thing);

}
