package org.openhab.binding.homematicip.internal.model.home;

import java.time.Instant;

import org.openhab.binding.homematicip.internal.model.common.DeviceUpdateState;

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
