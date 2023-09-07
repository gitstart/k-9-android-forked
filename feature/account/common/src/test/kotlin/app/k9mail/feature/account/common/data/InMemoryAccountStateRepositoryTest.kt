package app.k9mail.feature.account.common.data

import app.k9mail.feature.account.common.domain.entity.AccountOptions
import app.k9mail.feature.account.common.domain.entity.AccountState
import app.k9mail.feature.account.common.domain.entity.AuthorizationState
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fsck.k9.mail.AuthType
import com.fsck.k9.mail.ConnectionSecurity
import com.fsck.k9.mail.ServerSettings
import org.junit.Test

class InMemoryAccountStateRepositoryTest {

    @Test
    fun `should initialize with empty state`() {
        val testSubject = InMemoryAccountStateRepository()

        val result = testSubject.getState()

        assertThat(result).isEqualTo(
            AccountState(
                emailAddress = null,
                incomingServerSettings = null,
                outgoingServerSettings = null,
                authorizationState = null,
                options = null,
            ),
        )
    }

    @Test
    fun `should save state`() {
        val testSubject = InMemoryAccountStateRepository(
            AccountState(
                emailAddress = "emailAddress",
                incomingServerSettings = INCOMING_SERVER_SETTINGS,
                outgoingServerSettings = OUTGOING_SERVER_SETTINGS,
                authorizationState = AuthorizationState("authorizationState"),
                options = OPTIONS,
            ),
        )
        val newState = AccountState(
            emailAddress = "emailAddress2",
            incomingServerSettings = INCOMING_SERVER_SETTINGS.copy(host = "imap2.example.org"),
            outgoingServerSettings = OUTGOING_SERVER_SETTINGS.copy(host = "smtp2.example.org"),
            authorizationState = AuthorizationState("authorizationState2"),
            options = OPTIONS.copy(
                accountName = "accountName2",
                displayName = "displayName2",
                emailSignature = "emailSignature2",
                checkFrequencyInMinutes = 50,
                messageDisplayCount = 60,
                showNotification = false,
            ),
        )

        testSubject.save(newState)

        assertThat(testSubject.getState()).isEqualTo(newState)
    }

    @Test
    fun `should save email address`() {
        val testSubject = InMemoryAccountStateRepository()

        testSubject.saveEmailAddress("emailAddress")

        assertThat(testSubject.getState().emailAddress)
            .isEqualTo("emailAddress")
    }

    @Test
    fun `should save incoming server settings`() {
        val testSubject = InMemoryAccountStateRepository()

        testSubject.saveIncomingServerSettings(INCOMING_SERVER_SETTINGS)

        assertThat(testSubject.getState().incomingServerSettings)
            .isEqualTo(INCOMING_SERVER_SETTINGS)
    }

    @Test
    fun `should save outgoing server settings`() {
        val testSubject = InMemoryAccountStateRepository()

        testSubject.saveOutgoingServerSettings(OUTGOING_SERVER_SETTINGS)

        assertThat(testSubject.getState().outgoingServerSettings)
            .isEqualTo(OUTGOING_SERVER_SETTINGS)
    }

    @Test
    fun `should save authorization state`() {
        val testSubject = InMemoryAccountStateRepository()

        testSubject.saveAuthorizationState(AuthorizationState("authorizationState"))

        assertThat(testSubject.getState().authorizationState)
            .isEqualTo(AuthorizationState("authorizationState"))
    }

    @Test
    fun `should save options`() {
        val testSubject = InMemoryAccountStateRepository()

        testSubject.saveOptions(OPTIONS)

        assertThat(testSubject.getState().options)
            .isEqualTo(OPTIONS)
    }

    @Test
    fun `should clear state`() {
        val testSubject = InMemoryAccountStateRepository(
            AccountState(
                emailAddress = "emailAddress",
                incomingServerSettings = INCOMING_SERVER_SETTINGS,
                outgoingServerSettings = OUTGOING_SERVER_SETTINGS,
                authorizationState = AuthorizationState("authorizationState"),
                options = OPTIONS,
            ),
        )

        testSubject.clear()

        assertThat(testSubject.getState()).isEqualTo(
            AccountState(
                emailAddress = null,
                incomingServerSettings = null,
                outgoingServerSettings = null,
                authorizationState = null,
                options = null,
            ),
        )
    }

    private companion object {
        val INCOMING_SERVER_SETTINGS = ServerSettings(
            "imap",
            "imap.example.org",
            993,
            ConnectionSecurity.SSL_TLS_REQUIRED,
            AuthType.PLAIN,
            "username",
            "password",
            null,
        )

        val OUTGOING_SERVER_SETTINGS = ServerSettings(
            "smtp",
            "smtp.example.org",
            465,
            ConnectionSecurity.SSL_TLS_REQUIRED,
            AuthType.PLAIN,
            "username",
            "password",
            null,
        )

        val OPTIONS = AccountOptions(
            accountName = "accountName",
            displayName = "displayName",
            emailSignature = "emailSignature",
            checkFrequencyInMinutes = 10,
            messageDisplayCount = 20,
            showNotification = true,
        )
    }
}
