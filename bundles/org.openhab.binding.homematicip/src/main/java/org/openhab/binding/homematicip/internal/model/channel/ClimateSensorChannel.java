package org.openhab.binding.homematicip.internal.model.channel;

/**
 * Climate sensor channel
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class ClimateSensorChannel extends FunctionalChannel {

    protected float actualTemperature;
    protected int humidity;
    protected double vaporAmount;

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
