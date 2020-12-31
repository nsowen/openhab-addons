package org.openhab.binding.homematicip.internal.model.channel;

import org.openhab.binding.homematicip.internal.model.common.ValveState;

/**
 * Channel for heating thermostats
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class HeatingThermostatChannel extends FunctionalChannel {

    private float temperatureOffset;
    private float valvePosition;
    private ValveState valveState;
    private float setPointTemperature;
    private float valveActualTemperature;
    private boolean automaticValveAdaptionNeeded;

    public float getTemperatureOffset() {
        return temperatureOffset;
    }

    public float getValvePosition() {
        return valvePosition;
    }

    public ValveState getValveState() {
        return valveState;
    }

    public float getSetPointTemperature() {
        return setPointTemperature;
    }

    public float getValveActualTemperature() {
        return valveActualTemperature;
    }

    public boolean isAutomaticValveAdaptionNeeded() {
        return automaticValveAdaptionNeeded;
    }
}
