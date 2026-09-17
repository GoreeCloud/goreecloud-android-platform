package com.goreecloud.android.platform.mesh

import kotlin.test.Test
import kotlin.test.assertFailsWith

class MeshClientTest {
    @Test
    fun capabilityQueryRequiresName() {
        assertFailsWith<IllegalArgumentException> {
            MeshCapabilityQuery(name = "")
        }
    }
}
