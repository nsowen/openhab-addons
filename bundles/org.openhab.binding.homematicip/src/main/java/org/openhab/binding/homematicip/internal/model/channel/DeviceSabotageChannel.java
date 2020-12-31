package org.openhab.binding.homematicip.internal.model.channel;

/**
 * Sabotage channel
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class DeviceSabotageChannel extends FunctionalChannel {

    private boolean sabotage;

    public boolean isSabotage() {
        return sabotage;
    }
}
