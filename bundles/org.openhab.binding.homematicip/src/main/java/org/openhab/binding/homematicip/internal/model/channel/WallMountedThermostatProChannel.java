package org.openhab.binding.homematicip.internal.model.channel;

import org.openhab.binding.homematicip.internal.model.common.ClimateControlDisplay;

/**
 * New class.
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class WallMountedThermostatProChannel extends WallMountedThermostatWithoutDisplayChannel {

    protected ClimateControlDisplay display;
    protected float setPointTemperature;

    public ClimateControlDisplay getDisplay() {
        return display;
    }

    public float getSetPointTemperature() {
        return setPointTemperature;
    }
}
