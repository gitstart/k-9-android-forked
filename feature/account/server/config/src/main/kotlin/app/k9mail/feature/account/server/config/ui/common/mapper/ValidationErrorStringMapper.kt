package app.k9mail.feature.account.server.config.ui.common.mapper

import android.content.res.Resources
import app.k9mail.core.common.domain.usecase.validation.ValidationError
import app.k9mail.feature.account.server.config.R
import app.k9mail.feature.account.server.config.domain.usecase.ValidateEmailAddress.ValidateEmailAddressError
import app.k9mail.feature.account.server.config.domain.usecase.ValidateImapPrefix.ValidateImapPrefixError
import app.k9mail.feature.account.server.config.domain.usecase.ValidatePassword.ValidatePasswordError
import app.k9mail.feature.account.server.config.domain.usecase.ValidatePort.ValidatePortError
import app.k9mail.feature.account.server.config.domain.usecase.ValidateServer.ValidateServerError
import app.k9mail.feature.account.server.config.domain.usecase.ValidateUsername.ValidateUsernameError

fun ValidationError.toResourceString(resources: Resources): String {
    return when (this) {
        is ValidateEmailAddressError -> toEmailAddressErrorString(resources)
        is ValidateServerError -> toServerErrorString(resources)
        is ValidatePortError -> toPortErrorString(resources)
        is ValidateUsernameError -> toUsernameErrorString(resources)
        is ValidatePasswordError -> toPasswordErrorString(resources)
        is ValidateImapPrefixError -> toImapPrefixErrorString(resources)
        else -> throw IllegalArgumentException("Unknown error: $this")
    }
}

private fun ValidateEmailAddressError.toEmailAddressErrorString(resources: Resources): String {
    return when (this) {
        is ValidateEmailAddressError.EmptyEmailAddress -> resources.getString(
            R.string.account_server_config_validation_error_email_address_required,
        )

        is ValidateEmailAddressError.InvalidEmailAddress -> resources.getString(
            R.string.account_server_config_validation_error_email_address_invalid,
        )
    }
}

private fun ValidateServerError.toServerErrorString(resources: Resources): String {
    return when (this) {
        is ValidateServerError.EmptyServer -> resources.getString(
            R.string.account_server_config_validation_error_server_required,
        )
    }
}

private fun ValidatePortError.toPortErrorString(resources: Resources): String {
    return when (this) {
        is ValidatePortError.EmptyPort -> resources.getString(
            R.string.account_server_config_validation_error_port_required,
        )

        is ValidatePortError.InvalidPort -> resources.getString(
            R.string.account_server_config_validation_error_port_invalid,
        )
    }
}

private fun ValidateUsernameError.toUsernameErrorString(resources: Resources): String {
    return when (this) {
        ValidateUsernameError.EmptyUsername -> resources.getString(
            R.string.account_server_config_validation_error_username_required,
        )
    }
}

private fun ValidatePasswordError.toPasswordErrorString(resources: Resources): String {
    return when (this) {
        ValidatePasswordError.EmptyPassword -> resources.getString(
            R.string.account_server_config_validation_error_password_required,
        )
    }
}

private fun ValidateImapPrefixError.toImapPrefixErrorString(resources: Resources): String {
    return when (this) {
        ValidateImapPrefixError.BlankImapPrefix -> resources.getString(
            R.string.account_server_config_validation_error_imap_prefix_blank,
        )
    }
}
