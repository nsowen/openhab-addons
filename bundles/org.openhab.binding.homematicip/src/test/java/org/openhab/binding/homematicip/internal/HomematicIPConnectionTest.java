package org.openhab.binding.homematicip.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openhab.binding.homematicip.internal.model.transport.HttpTransport;
import org.openhab.core.io.net.http.WebSocketFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HomematicIPConnectionTest {

    private HttpTransport transport;
    private HomematicIPConnection connection;
    private String uuid;
    private String accessPointId;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException, IOException {
        this.accessPointId = "3014-F711-A000-03DB-E98D-1BB8";
        this.uuid = UUID.randomUUID().toString();
        this.transport = new HttpTransport(null);
        this.connection = new HomematicIPConnection(uuid, accessPointId, transport);
    }

    @Test
    @Disabled
    void testAuthorization() throws IOException, InterruptedException {
        this.connection.authConnectionRequest();
        do {
            System.out.println("Press pairing button now");
            Thread.sleep(5000L);
        } while (!this.connection.authIsRequestAcknowledgedRequest());
        var tokenResponse = this.connection.authRequestToken();
        assertNotNull(tokenResponse.getAuthToken());
        var confirmResponse = this.connection.authConfirmToken(tokenResponse.getAuthToken());
        System.out.println("Token: " + tokenResponse.getAuthToken() + ", client: " + confirmResponse.getClientId());
    }
}
