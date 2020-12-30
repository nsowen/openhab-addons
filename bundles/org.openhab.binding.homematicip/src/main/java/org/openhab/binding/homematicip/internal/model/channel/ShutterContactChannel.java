package org.openhab.binding.homematicip.internal.model.channel;

import org.openhab.binding.homematicip.internal.model.common.WindowState;

/**
 * Shutter contact details
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class ShutterContactChannel extends FunctionalChannel {

    private WindowState windowState;
    private long eventDelay;

    public WindowState getWindowState() {
        return windowState;
    }

    public long getEventDelay() {
        return eventDelay;
    }
}
