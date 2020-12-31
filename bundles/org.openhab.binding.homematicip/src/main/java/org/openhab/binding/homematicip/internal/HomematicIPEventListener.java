package org.openhab.binding.homematicip.internal;

import org.openhab.binding.homematicip.internal.model.event.StateChange;

/**
 * Receives event sent from the Homematic IP Cloud
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-31
 */
public interface HomematicIPEventListener {

    void onReceive(StateChange stateChange);
}
