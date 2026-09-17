package com.goreecloud.android.platform.testing

import com.goreecloud.android.platform.core.AuthorityId
import com.goreecloud.android.platform.core.CapabilityDescriptor
import com.goreecloud.android.platform.core.GoreeCloudError
import com.goreecloud.android.platform.core.GoreeCloudResult
import com.goreecloud.android.platform.core.PlatformVersion
import com.goreecloud.android.platform.identity.IdentitySessionSnapshot
import com.goreecloud.android.platform.mesh.MeshCapabilityEndpoint
import com.goreecloud.android.platform.mesh.MeshCapabilityQuery
import com.goreecloud.android.platform.policy.PolicyDecision
import com.goreecloud.android.platform.policy.PolicyEvaluation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AdapterContractChecksTest {
    @Test
    fun identityAuthorityMismatchIsReported() {
        val result = GoreeCloudResult.Success(
            IdentitySessionSnapshot(
                authenticated = false,
                authority = AuthorityId("unexpected-identity-authority"),
            ),
        )

        val report = AdapterContractChecks.identitySession(result)

        assertFalse(report.passed)
        assertEquals("identity.authority_mismatch", report.violations.single().code)
    }

    @Test
    fun meshDiscoveryChecksQueryNameAndMinimumVersion() {
        val query = MeshCapabilityQuery(
            name = "calendar.sync",
            minimumVersion = PlatformVersion(1, 2, 0),
        )
        val endpoint = MeshCapabilityEndpoint(
            endpointId = "endpoint-1",
            capability = CapabilityDescriptor(
                name = "calendar.export",
                version = PlatformVersion(1, 1, 0),
                authority = AuthorityId("calendar"),
            ),
            authority = AuthorityId("goreecloud-mesh"),
            transport = "local-test",
        )

        val report = AdapterContractChecks.meshDiscovery(
            query = query,
            result = GoreeCloudResult.Success(listOf(endpoint)),
        )

        assertFalse(report.passed)
        assertEquals(
            setOf("mesh.capability_name_mismatch", "mesh.minimum_version_not_met"),
            report.violations.map { it.code }.toSet(),
        )
    }

    @Test
    fun policyAuthorityMismatchIsReported() {
        val evaluation = PolicyEvaluation(
            decision = PolicyDecision.INDETERMINATE,
            policyId = "test-policy",
            policyVersion = PlatformVersion(1, 0, 0),
            authority = AuthorityId("unexpected-policy-authority"),
        )

        val report = AdapterContractChecks.policyEvaluation(
            GoreeCloudResult.Success(evaluation),
        )

        assertFalse(report.passed)
        assertEquals("policy.authority_mismatch", report.violations.single().code)
    }

    @Test
    fun failuresAreValidAdapterOutcomesForStructuralChecks() {
        val failure = GoreeCloudResult.Failure(
            GoreeCloudError(
                code = "unavailable",
                message = "Producer unavailable in deterministic test fixture.",
                retryable = true,
            ),
        )

        assertTrue(AdapterContractChecks.identitySession(failure).passed)
        assertTrue(
            AdapterContractChecks.meshDiscovery(
                query = MeshCapabilityQuery("test.capability"),
                result = failure,
            ).passed,
        )
        assertTrue(AdapterContractChecks.policyEvaluation(failure).passed)
    }

    @Test
    fun passingReportCanBeRequiredAndViolationReportFails() {
        ContractCheckReport().requirePassed()

        val report = ContractCheckReport(
            listOf(ContractViolation("test.violation", "Expected test violation.")),
        )

        assertFailsWith<IllegalStateException> {
            report.requirePassed()
        }
    }
}
