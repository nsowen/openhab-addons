/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
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
package org.openhab.binding.satel.internal.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.io.transport.serial.PortInUseException;
import org.eclipse.smarthome.io.transport.serial.SerialPort;
import org.eclipse.smarthome.io.transport.serial.SerialPortEvent;
import org.eclipse.smarthome.io.transport.serial.SerialPortEventListener;
import org.eclipse.smarthome.io.transport.serial.SerialPortIdentifier;
import org.eclipse.smarthome.io.transport.serial.SerialPortManager;
import org.eclipse.smarthome.io.transport.serial.UnsupportedCommOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents Satel INT-RS module. Implements methods required to connect and
 * communicate with that module over serial protocol.
 *
 * @author Krzysztof Goworek - Initial contribution
 */
@NonNullByDefault
public class IntRSModule extends SatelModule {

    private final Logger logger = LoggerFactory.getLogger(IntRSModule.class);

    private final String port;

    private final SerialPortManager serialPortManager;

    /**
     * Creates new instance with port and timeout set to specified values.
     *
     * @param port
     *                              serial port the module is connected to
     * @param serialPortManager
     *                              serial port manager object
     * @param timeout
     *                              timeout value in milliseconds for connect/read/write
     *                              operations
     */
    public IntRSModule(String port, SerialPortManager serialPortManager, int timeout) {
        super(timeout);

        this.port = port;
        this.serialPortManager = serialPortManager;
    }

    @Override
    protected CommunicationChannel connect() throws ConnectionFailureException {
        logger.info("Connecting to INT-RS module at {}", this.port);

        try {
            SerialPortIdentifier portIdentifier = serialPortManager.getIdentifier(this.port);
            if (portIdentifier == null) {
                throw new ConnectionFailureException(String.format("Port %s does not exist", this.port));
            }
            SerialPort serialPort = portIdentifier.open("org.openhab.binding.satel", 2000);
            serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.enableReceiveTimeout(this.getTimeout());
            // RXTX serial port library causes high CPU load
            // Start event listener, which will just sleep and slow down event
            // loop
            serialPort.addEventListener(new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent ev) {
                    try {
                        logger.trace("RXTX library CPU load workaround, sleep forever");
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                    }
                }
            });
            serialPort.notifyOnDataAvailable(true);

            logger.info("INT-RS module connected successfuly");
            return new SerialCommunicationChannel(serialPort);
        } catch (PortInUseException e) {
            throw new ConnectionFailureException(String.format("Port %s in use", this.port), e);
        } catch (UnsupportedCommOperationException e) {
            throw new ConnectionFailureException(String.format("Unsupported comm operation on port %s", this.port), e);
        } catch (TooManyListenersException e) {
            throw new ConnectionFailureException(String.format("Too many listeners on port %s", this.port), e);
        }
    }

    private class SerialCommunicationChannel implements CommunicationChannel {

        private SerialPort serialPort;

        public SerialCommunicationChannel(SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        @Override
        @Nullable
        public InputStream getInputStream() throws IOException {
            return this.serialPort.getInputStream();
        }

        @Override
        @Nullable
        public OutputStream getOutputStream() throws IOException {
            return this.serialPort.getOutputStream();
        }

        @Override
        public void disconnect() {
            logger.debug("Closing connection to INT-RS module");
            try {
                this.serialPort.close();
                logger.info("Connection to INT-RS module has been closed");
            } catch (Exception e) {
                logger.error("An error occurred during closing serial port", e);
            }
        }
    }
}
