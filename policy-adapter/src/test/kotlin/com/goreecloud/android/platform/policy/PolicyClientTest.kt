package com.goreecloud.android.platform.policy

import com.goreecloud.android.platform.core.RequestContext
import kotlin.test.Test
import kotlin.test.assertFailsWith

class PolicyClientTest {
    @Test
    fun requestRequiresActionAndResource() {
        assertFailsWith<IllegalArgumentException> {
            PolicyEvaluationRequest(
                resource = "",
                action = "read",
                context = RequestContext("request-1"),
            )
        }
    }
}
