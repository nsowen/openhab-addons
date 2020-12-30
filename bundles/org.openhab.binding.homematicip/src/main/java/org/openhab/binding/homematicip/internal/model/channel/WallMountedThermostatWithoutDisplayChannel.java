package org.openhab.binding.homematicip.internal.model.channel;

/**
 * Channel for wall thermostats
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class WallMountedThermostatWithoutDisplayChannel extends FunctionalChannel {

    protected float temperatureOffset;
    protected float actualTemperature;
    protected int humidity;
    protected double vaporAmount;

    public float getTemperatureOffset() {
        return temperatureOffset;
    }

    public float getActualTemperature() {
        return actualTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getVaporAmount() {
        return vaporAmount;
    }
}
