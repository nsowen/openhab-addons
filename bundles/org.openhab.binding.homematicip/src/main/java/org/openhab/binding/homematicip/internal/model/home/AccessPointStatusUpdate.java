package org.openhab.binding.homematicip.internal.model.home;

import org.openhab.binding.homematicip.internal.model.common.DeviceUpdateState;

import java.time.Instant;

/**
 * Access Point Status Update
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class AccessPointStatusUpdate {

    private Instant successfulUpdateTimestamp;
    private Instant updateStateChangedTimestamp;
    private DeviceUpdateState accessPointUpdateState;

}
