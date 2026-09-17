package com.goreecloud.android.platform.testing

import com.goreecloud.android.platform.core.GoreeCloudResult
import com.goreecloud.android.platform.core.RequestContext
import com.goreecloud.android.platform.identity.IdentityClient
import com.goreecloud.android.platform.identity.IdentitySessionSnapshot
import com.goreecloud.android.platform.mesh.MeshCapabilityEndpoint
import com.goreecloud.android.platform.mesh.MeshCapabilityQuery
import com.goreecloud.android.platform.mesh.MeshClient
import com.goreecloud.android.platform.policy.PolicyClient
import com.goreecloud.android.platform.policy.PolicyEvaluation
import com.goreecloud.android.platform.policy.PolicyEvaluationRequest

class FakeIdentityClient(
    private val handler: suspend (RequestContext) -> GoreeCloudResult<IdentitySessionSnapshot>,
) : IdentityClient {
    override suspend fun currentSession(
        context: RequestContext,
    ): GoreeCloudResult<IdentitySessionSnapshot> = handler(context)
}

class FakeMeshClient(
    private val handler: suspend (
        MeshCapabilityQuery,
        RequestContext,
    ) -> GoreeCloudResult<List<MeshCapabilityEndpoint>>,
) : MeshClient {
    override suspend fun discover(
        query: MeshCapabilityQuery,
        context: RequestContext,
    ): GoreeCloudResult<List<MeshCapabilityEndpoint>> = handler(query, context)
}

class FakePolicyClient(
    private val handler: suspend (PolicyEvaluationRequest) -> GoreeCloudResult<PolicyEvaluation>,
) : PolicyClient {
    override suspend fun evaluate(
        request: PolicyEvaluationRequest,
    ): GoreeCloudResult<PolicyEvaluation> = handler(request)
}
