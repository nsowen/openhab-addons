package org.openhab.binding.homematicip.internal.model.channel;

/**
 * New class.
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class AnalogRoomControlChannel extends FunctionalChannel {

    protected float temperatureOffset;
    protected float actualTemperature;
    protected float setPointTemperature;

    public float getTemperatureOffset() {
        return temperatureOffset;
    }

    public float getActualTemperature() {
        return actualTemperature;
    }

    public float getSetPointTemperature() {
        return setPointTemperature;
    }
}
