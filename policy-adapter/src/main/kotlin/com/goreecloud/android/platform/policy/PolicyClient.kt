package com.goreecloud.android.platform.policy

import com.goreecloud.android.platform.core.AuthorityId
import com.goreecloud.android.platform.core.GoreeCloudResult
import com.goreecloud.android.platform.core.PlatformVersion
import com.goreecloud.android.platform.core.RequestContext

enum class PolicyDecision {
    ALLOW,
    DENY,
    CONDITIONAL,
    DEFER,
    INDETERMINATE,
    ERROR,
}

data class PolicyEvaluationRequest(
    val resource: String,
    val action: String,
    val context: RequestContext,
    val subjectRef: String? = null,
) {
    init {
        require(resource.isNotBlank()) { "Policy resource must not be blank." }
        require(action.isNotBlank()) { "Policy action must not be blank." }
    }
}

data class PolicyEvaluation(
    val decision: PolicyDecision,
    val policyId: String,
    val policyVersion: PlatformVersion,
    val authority: AuthorityId = AuthorityId("goreecloud-policy"),
    val reasonCode: String? = null,
    val obligations: Map<String, String> = emptyMap(),
) {
    init {
        require(policyId.isNotBlank()) { "Policy ID must not be blank." }
    }
}

interface PolicyClient {
    suspend fun evaluate(request: PolicyEvaluationRequest): GoreeCloudResult<PolicyEvaluation>
}
