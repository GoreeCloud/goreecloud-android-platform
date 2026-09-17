package com.goreecloud.android.platform.runtime

import com.goreecloud.android.platform.core.AndroidApiRequirement
import com.goreecloud.android.platform.core.AndroidPlatformCompatibilityContract
import com.goreecloud.android.platform.core.CompatibilityVerdict
import com.goreecloud.android.platform.core.PlatformVersion
import com.goreecloud.android.platform.core.VersionRequirement
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AndroidRuntimeSnapshotTest {
    @Test
    fun mapsRuntimeApiAndCallerSuppliedPlatformVersion() {
        val platformVersion = PlatformVersion(0, 1, 0)
        val environment = AndroidRuntimeSnapshot(
            sdkInt = 35,
            previewSdkInt = 0,
        ).asConsumerEnvironment(platformVersion)

        assertEquals(platformVersion, environment.platformVersion)
        assertEquals(35, environment.androidApiLevel)
    }

    @Test
    fun preservesUnknownPlatformVersionWhileProvidingRuntimeApi() {
        val environment = AndroidRuntimeSnapshot(
            sdkInt = 35,
            previewSdkInt = 0,
        ).asConsumerEnvironment()

        assertNull(environment.platformVersion)
        assertEquals(35, environment.androidApiLevel)
    }

    @Test
    fun evaluatesContractUsingRuntimeApiFact() {
        val contract = AndroidPlatformCompatibilityContract(
            contractVersion = PlatformVersion(0, 1, 0),
            platformRequirement = VersionRequirement(
                minimumInclusive = PlatformVersion(0, 1, 0),
                maximumExclusive = PlatformVersion(0, 2, 0),
            ),
            androidApiRequirement = AndroidApiRequirement(
                minimumInclusive = 29,
                maximumInclusive = 35,
            ),
        )

        val assessment = AndroidRuntimeSnapshot(
            sdkInt = 35,
            previewSdkInt = 0,
        ).evaluateCompatibility(
            contract = contract,
            platformVersion = PlatformVersion(0, 1, 0),
        )

        assertEquals(CompatibilityVerdict.COMPATIBLE, assessment.verdict)
    }

    @Test
    fun previewBuildKeepsReportedSdkIntWithoutPromotingApiLevel() {
        val snapshot = AndroidRuntimeSnapshot(
            sdkInt = 35,
            previewSdkInt = 2,
        )

        assertTrue(snapshot.isPreviewBuild)
        assertEquals(35, snapshot.asConsumerEnvironment().androidApiLevel)
        assertTrue(snapshot.isAtLeast(35))
        assertFalse(snapshot.isAtLeast(36))
    }

    @Test
    fun rejectsInvalidRuntimeFacts() {
        assertFailsWith<IllegalArgumentException> {
            AndroidRuntimeSnapshot(sdkInt = 0, previewSdkInt = 0)
        }
        assertFailsWith<IllegalArgumentException> {
            AndroidRuntimeSnapshot(sdkInt = 35, previewSdkInt = -1)
        }
    }
}
