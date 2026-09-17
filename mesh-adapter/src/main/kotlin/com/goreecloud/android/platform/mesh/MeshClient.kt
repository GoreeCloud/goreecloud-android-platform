package com.goreecloud.android.platform.mesh

import com.goreecloud.android.platform.core.AuthorityId
import com.goreecloud.android.platform.core.CapabilityDescriptor
import com.goreecloud.android.platform.core.GoreeCloudResult
import com.goreecloud.android.platform.core.PlatformVersion
import com.goreecloud.android.platform.core.RequestContext

data class MeshCapabilityQuery(
    val name: String,
    val minimumVersion: PlatformVersion? = null,
) {
    init {
        require(name.isNotBlank()) { "Capability query name must not be blank." }
    }
}

data class MeshCapabilityEndpoint(
    val endpointId: String,
    val capability: CapabilityDescriptor,
    val authority: AuthorityId,
    val transport: String,
) {
    init {
        require(endpointId.isNotBlank()) { "Endpoint ID must not be blank." }
        require(transport.isNotBlank()) { "Transport must not be blank." }
    }
}

interface MeshClient {
    suspend fun discover(
        query: MeshCapabilityQuery,
        context: RequestContext,
    ): GoreeCloudResult<List<MeshCapabilityEndpoint>>
}
