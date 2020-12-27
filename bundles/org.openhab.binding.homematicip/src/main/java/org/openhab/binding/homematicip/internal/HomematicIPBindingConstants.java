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
package org.openhab.binding.homematicip.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link HomematicIPBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Nils Sowen - Initial contribution
 */
@NonNullByDefault
public class HomematicIPBindingConstants {

    private static final String BINDING_ID = "homematicip";

    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");
    public static final String CONFIG_DESCRIPTION_URI_CHANNEL = "channel-type:homematic:config";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "sample");

    // List of all Channel ids
    public static final String CHANNEL_1 = "channel1";

    public static final String PROPERTY_VENDOR_NAME = "eQ-3 AG";
}
