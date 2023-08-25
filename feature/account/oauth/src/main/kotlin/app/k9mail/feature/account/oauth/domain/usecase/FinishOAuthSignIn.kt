package app.k9mail.feature.account.oauth.domain.usecase

import android.content.Intent
import app.k9mail.feature.account.oauth.domain.DomainContract
import app.k9mail.feature.account.oauth.domain.DomainContract.UseCase
import app.k9mail.feature.account.oauth.domain.entity.AuthorizationResult

class FinishOAuthSignIn(
    private val repository: DomainContract.AuthorizationRepository,
) : UseCase.FinishOAuthSignIn {
    override suspend fun execute(intent: Intent): AuthorizationResult {
        val response = repository.getAuthorizationResponse(intent)
        val exception = repository.getAuthorizationException(intent)

        return if (response != null) {
            repository.getExchangeToken(response)
        } else if (exception != null) {
            AuthorizationResult.Failure(exception)
        } else {
            AuthorizationResult.Canceled
        }
    }
}
