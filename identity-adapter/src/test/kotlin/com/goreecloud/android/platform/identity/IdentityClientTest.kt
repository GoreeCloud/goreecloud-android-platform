package com.goreecloud.android.platform.identity

import kotlin.test.Test
import kotlin.test.assertFailsWith

class IdentityClientTest {
    @Test
    fun unauthenticatedSessionCannotContainSubject() {
        assertFailsWith<IllegalArgumentException> {
            IdentitySessionSnapshot(
                authenticated = false,
                subject = IdentitySubjectRef("subject-1"),
            )
        }
    }
}
