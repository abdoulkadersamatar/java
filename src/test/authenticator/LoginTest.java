package test.authenticator;

import authenticator.MapAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    private MapAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator = new MapAuthenticator();
    }

    @Test
    void testSuccessfulAuthentication() {
        assertTrue(authenticator.authenticate("admin", "admin"));
    }

    @Test
    void testFailedAuthentication() {
        assertFalse(authenticator.authenticate("admin", "wrongpassword"));
    }

    @Test
    void testEmptyCredentials() {
        assertFalse(authenticator.authenticate("", ""));
        assertFalse(authenticator.authenticate(null, null));
    }
}
