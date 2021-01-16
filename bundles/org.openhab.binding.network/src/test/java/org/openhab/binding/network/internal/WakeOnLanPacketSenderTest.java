/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.network.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.openhab.binding.network.internal.WakeOnLanPacketSender.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.openhab.core.util.HexUtils;

/**
 * Tests cases for {@link WakeOnLanPacketSender}.
 *
 * @author Wouter Born - Initial contribution
 */
@Timeout(value = 10, unit = TimeUnit.SECONDS)
public class WakeOnLanPacketSenderTest {

    private void assertValidMagicPacket(byte[] macBytes, byte[] packet) {
        byte[] prefix = new byte[PREFIX_BYTE_SIZE];
        Arrays.fill(prefix, (byte) 0xff);

        assertThat(Arrays.copyOfRange(packet, 0, PREFIX_BYTE_SIZE), is(prefix));

        for (int i = PREFIX_BYTE_SIZE; i < MAGIC_PACKET_BYTE_SIZE; i += MAC_BYTE_SIZE) {
            assertThat(Arrays.copyOfRange(packet, i, i + MAC_BYTE_SIZE), is(macBytes));
        }
    }

    @Test
    public void sendWithColonSeparatedMacAddress() {
        byte[] actualPacket = new byte[MAGIC_PACKET_BYTE_SIZE];

        WakeOnLanPacketSender sender = new WakeOnLanPacketSender("6f:70:65:6e:48:41",
                bytes -> System.arraycopy(bytes, 0, actualPacket, 0, bytes.length));

        sender.sendPacket();

        assertValidMagicPacket(HexUtils.hexToBytes("6f:70:65:6e:48:41", ":"), actualPacket);
    }

    @Test
    public void sendWithHyphenSeparatedMacAddress() {
        byte[] actualPacket = new byte[MAGIC_PACKET_BYTE_SIZE];

        WakeOnLanPacketSender sender = new WakeOnLanPacketSender("6F-70-65-6E-48-41",
                bytes -> System.arraycopy(bytes, 0, actualPacket, 0, bytes.length));

        sender.sendPacket();

        assertValidMagicPacket(HexUtils.hexToBytes("6F-70-65-6E-48-41", "-"), actualPacket);
    }

    @Test
    public void sendWithNoSeparatedMacAddress() {
        byte[] actualPacket = new byte[MAGIC_PACKET_BYTE_SIZE];

        WakeOnLanPacketSender sender = new WakeOnLanPacketSender("6f70656e4841",
                bytes -> System.arraycopy(bytes, 0, actualPacket, 0, bytes.length));

        sender.sendPacket();

        assertValidMagicPacket(HexUtils.hexToBytes("6f70656e4841"), actualPacket);
    }

    @Test
    public void sendWithEmptyMacAddressThrowsException() {
        assertThrows(IllegalStateException.class, () -> new WakeOnLanPacketSender("").sendPacket());
    }

    @Test
    public void sendWithTooShortMacAddressThrowsException() {
        assertThrows(IllegalStateException.class, () -> new WakeOnLanPacketSender("6f:70:65:6e:48").sendPacket());
    }

    @Test
    public void sendWithTooLongMacAddressThrowsException() {
        assertThrows(IllegalStateException.class, () -> new WakeOnLanPacketSender("6f:70:65:6e:48:41:42").sendPacket());
    }

    @Test
    public void sendWithUnsupportedSeparatorInMacAddressThrowsException() {
        assertThrows(IllegalStateException.class, () -> new WakeOnLanPacketSender("6f=70=65=6e=48=41").sendPacket());
    }
}
