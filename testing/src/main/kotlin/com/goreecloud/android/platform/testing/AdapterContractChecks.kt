package com.goreecloud.android.platform.testing

import com.goreecloud.android.platform.core.AuthorityId
import com.goreecloud.android.platform.core.GoreeCloudResult
import com.goreecloud.android.platform.identity.IdentitySessionSnapshot
import com.goreecloud.android.platform.mesh.MeshCapabilityEndpoint
import com.goreecloud.android.platform.mesh.MeshCapabilityQuery
import com.goreecloud.android.platform.policy.PolicyEvaluation

data class ContractViolation(
    val code: String,
    val message: String,
) {
    init {
        require(code.isNotBlank()) { "Contract violation code must not be blank." }
        require(message.isNotBlank()) { "Contract violation message must not be blank." }
    }
}

data class ContractCheckReport(
    val violations: List<ContractViolation> = emptyList(),
) {
    val passed: Boolean
        get() = violations.isEmpty()

    fun requirePassed() {
        check(passed) {
            violations.joinToString(
                prefix = "Adapter contract violations: ",
                separator = "; ",
            ) { violation -> "${violation.code}: ${violation.message}" }
        }
    }
}

/**
 * Reusable structural checks for adapter results returned to GoreeCloud Android consumers.
 *
 * These checks intentionally validate only invariants owned by this repository. They do not replace
 * producer-system conformance suites and do not turn a transport success into proof that Identity,
 * Mesh, Policy, or another producer is operationally conformant.
 */
object AdapterContractChecks {
    fun identitySession(
        result: GoreeCloudResult<IdentitySessionSnapshot>,
        expectedAuthority: AuthorityId = AuthorityId("goreecloud-identity"),
    ): ContractCheckReport {
        val snapshot = when (result) {
            is GoreeCloudResult.Failure -> return ContractCheckReport()
            is GoreeCloudResult.Success -> result.value
        }

        val violations = buildList {
            if (snapshot.authority != expectedAuthority) {
                add(
                    ContractViolation(
                        code = "identity.authority_mismatch",
                        message = "Identity snapshot authority ${snapshot.authority.value} does not match expected authority ${expectedAuthority.value}.",
                    ),
                )
            }
        }
        return ContractCheckReport(violations)
    }

    fun meshDiscovery(
        query: MeshCapabilityQuery,
        result: GoreeCloudResult<List<MeshCapabilityEndpoint>>,
    ): ContractCheckReport {
        val endpoints = when (result) {
            is GoreeCloudResult.Failure -> return ContractCheckReport()
            is GoreeCloudResult.Success -> result.value
        }

        val violations = buildList {
            endpoints.forEachIndexed { index, endpoint ->
                if (endpoint.capability.name != query.name) {
                    add(
                        ContractViolation(
                            code = "mesh.capability_name_mismatch",
                            message = "Endpoint $index returned capability ${endpoint.capability.name} for query ${query.name}.",
                        ),
                    )
                }

                val minimumVersion = query.minimumVersion
                if (minimumVersion != null && endpoint.capability.version < minimumVersion) {
                    add(
                        ContractViolation(
                            code = "mesh.minimum_version_not_met",
                            message = "Endpoint $index returned version ${endpoint.capability.version} below requested minimum $minimumVersion.",
                        ),
                    )
                }
            }
        }
        return ContractCheckReport(violations)
    }

    fun policyEvaluation(
        result: GoreeCloudResult<PolicyEvaluation>,
        expectedAuthority: AuthorityId = AuthorityId("goreecloud-policy"),
    ): ContractCheckReport {
        val evaluation = when (result) {
            is GoreeCloudResult.Failure -> return ContractCheckReport()
            is GoreeCloudResult.Success -> result.value
        }

        val violations = buildList {
            if (evaluation.authority != expectedAuthority) {
                add(
                    ContractViolation(
                        code = "policy.authority_mismatch",
                        message = "Policy evaluation authority ${evaluation.authority.value} does not match expected authority ${expectedAuthority.value}.",
                    ),
                )
            }
        }
        return ContractCheckReport(violations)
    }
}
