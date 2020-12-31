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
package org.openhab.binding.homematicip.internal.model.group;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class EnvironmentGroup extends Group {

    private double actualTemperature = 0.0;
    private double illumination = 0.0;
    private boolean raining;
    private double windSpeed = 0.0;
    private double humidity = 0.0;

    public double getActualTemperature() {
        return actualTemperature;
    }

    public double getIllumination() {
        return illumination;
    }

    public boolean isRaining() {
        return raining;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }
}
