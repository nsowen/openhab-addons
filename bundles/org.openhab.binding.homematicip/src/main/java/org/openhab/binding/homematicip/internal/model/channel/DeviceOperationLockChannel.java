package org.openhab.binding.homematicip.internal.model.channel;

/**
 * Channel that enables/disables operation lock
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class DeviceOperationLockChannel extends FunctionalChannel {

    private boolean operationLockActive;

    public boolean isOperationLockActive() {
        return operationLockActive;
    }
}
