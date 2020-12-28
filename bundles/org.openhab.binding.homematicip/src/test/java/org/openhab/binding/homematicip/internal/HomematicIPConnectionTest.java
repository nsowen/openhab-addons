package org.openhab.binding.homematicip.internal;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openhab.binding.homematicip.internal.model.transport.HttpTransport;
import org.openhab.core.io.net.http.internal.ExtensibleTrustManagerImpl;
import org.openhab.core.io.net.http.internal.WebClientFactoryImpl;

class HomematicIPConnectionTest {

    private HttpTransport transport;
    private HomematicIPConnection connection;
    private String uuid;
    private String accessPointId;
    private ExecutorService scheduler;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {
        this.accessPointId = "3014-F711-A000-03DB-E98D-1BB8";
        this.uuid = UUID.randomUUID().toString();
        var factory = new WebClientFactoryImpl(new ExtensibleTrustManagerImpl()) {
            @Override
            public void activate(Map<String, Object> parameters) {
                super.activate(parameters);
            }
        };
        factory.activate(Collections.emptyMap());
        this.scheduler = Executors.newSingleThreadExecutor();
        this.transport = new HttpTransport(factory, null);
        this.connection = new HomematicIPConnection(uuid, accessPointId, transport);
        this.connection.setAuthToken("C72117F990F2C5B1F6715286F071BE9AA2F70C7644BB480DD7323B3AA72996B4");
        this.connection.initAsync(scheduler).get();
    }

    @Test
    void testHome() throws ExecutionException, InterruptedException {
        // this.connection.getCurrentState(scheduler).get();
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
