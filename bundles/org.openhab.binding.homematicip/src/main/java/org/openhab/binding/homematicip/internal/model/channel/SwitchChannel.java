package org.openhab.binding.homematicip.internal.model.channel;

import org.openhab.binding.homematicip.internal.model.common.ProfileMode;

/**
 * Switch channel
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class SwitchChannel extends FunctionalChannel {

    private boolean on;
    private ProfileMode profileMode;
    private ProfileMode userDesiredProfileMode;

    public boolean isOn() {
        return on;
    }

    public ProfileMode getProfileMode() {
        return profileMode;
    }

    public ProfileMode getUserDesiredProfileMode() {
        return userDesiredProfileMode;
    }
}
