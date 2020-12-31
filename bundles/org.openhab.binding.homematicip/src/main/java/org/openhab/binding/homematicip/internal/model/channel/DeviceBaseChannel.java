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
package org.openhab.binding.homematicip.internal.model.channel;

import java.util.Map;

import org.openhab.binding.homematicip.internal.model.common.OptionalFeatureType;

/**
 * Device-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class DeviceBaseChannel extends FunctionalChannel {

    protected boolean unreach;
    protected boolean lowBat;
    protected boolean routerModuleSupported;
    protected boolean routerModuleEnabled;
    protected float rssiDeviceValue;
    protected float rssiPeerValue;
    protected boolean dutyCycle;
    protected boolean configPending;
    protected Map<OptionalFeatureType, Boolean> supportedOptionalFeatures;
}
