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
package org.openhab.binding.homematicip.internal.model.common;

/**
 * Homematic IP Weather conditions
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public enum WeatherCondition {
    CLEAR,
    LIGHT_CLOUDY,
    CLOUDY,
    CLOUDY_WITH_RAIN,
    CLOUDY_WITH_SNOW_RAIN,
    HEAVILY_CLOUDY,
    HEAVILY_CLOUDY_WITH_RAIN,
    HEAVILY_CLOUDY_WITH_STRONG_RAIN,
    HEAVILY_CLOUDY_WITH_SNOW,
    HEAVILY_CLOUDY_WITH_SNOW_RAIN,
    HEAVILY_CLOUDY_WITH_THUNDER,
    HEAVILY_CLOUDY_WITH_RAIN_AND_THUNDER,
    FOGGY,
    STRONG_WIND,
    UNKNOWN
}
