package com.goreecloud.android.platform.identity

import com.goreecloud.android.platform.core.AuthorityId
import com.goreecloud.android.platform.core.GoreeCloudResult
import com.goreecloud.android.platform.core.RequestContext

@JvmInline
value class IdentitySubjectRef(val value: String) {
    init {
        require(value.isNotBlank()) { "Subject reference must not be blank." }
    }
}

data class IdentitySessionSnapshot(
    val authenticated: Boolean,
    val subject: IdentitySubjectRef? = null,
    val assurance: String? = null,
    val observedAtEpochMillis: Long? = null,
    val authority: AuthorityId = AuthorityId("goreecloud-identity"),
) {
    init {
        require(authenticated || subject == null) {
            "An unauthenticated snapshot must not claim a subject."
        }
    }
}

interface IdentityClient {
    suspend fun currentSession(context: RequestContext): GoreeCloudResult<IdentitySessionSnapshot>
}
