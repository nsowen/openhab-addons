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
package org.openhab.binding.homematicip.internal.model.home;

import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.common.WeatherCondition;
import org.openhab.binding.homematicip.internal.model.common.WeatherDayTime;

/**
 * Weather info from within Home state
 *
 * @author Nils Sowen - Initial contribution
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

    public float getTemperature() {
        return temperature;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public WeatherDayTime getWeatherDayTime() {
        return weatherDayTime;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public double getVaporAmount() {
        return vaporAmount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Weather.class.getSimpleName() + "[", "]").add("temperature=" + temperature)
                .add("weatherCondition=" + weatherCondition).add("weatherDayTime=" + weatherDayTime)
                .add("minTemperature=" + minTemperature).add("maxTemperature=" + maxTemperature)
                .add("humidity=" + humidity).add("windSpeed=" + windSpeed).add("windDirection=" + windDirection)
                .add("vaporAmount=" + vaporAmount).toString();
    }
}
