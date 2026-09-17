package com.goreecloud.android.platform.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PlatformContractsTest {
    @Test
    fun semanticVersionParsesAndCompares() {
        val current = PlatformVersion.parse("1.4.1")
        val next = PlatformVersion.parse("1.5.0")

        assertEquals("1.4.1", current.toString())
        assertTrue(current < next)
    }

    @Test
    fun semanticVersionRejectsIncompleteValues() {
        assertFailsWith<IllegalArgumentException> {
            PlatformVersion.parse("1.4")
        }
    }

    @Test
    fun requestContextRequiresRequestId() {
        assertFailsWith<IllegalArgumentException> {
            RequestContext(requestId = "")
        }
    }
}
