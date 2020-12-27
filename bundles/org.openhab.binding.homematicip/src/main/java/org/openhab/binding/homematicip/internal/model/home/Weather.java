/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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
package org.openhab.binding.homematicip.internal.model.home;

import org.openhab.binding.homematicip.internal.model.common.WeatherCondition;
import org.openhab.binding.homematicip.internal.model.common.WeatherDayTime;

/**
 * Weather info from within Home state
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class Weather {
    private float temperature;
    private WeatherCondition weatherCondition;
    private WeatherDayTime weatherDayTime;
    private float minTemperature;
    private float maxTemperature;
    private int humidity;
    private double windSpeed;
    private int windDirection;
    private double vaporAmount;

}
