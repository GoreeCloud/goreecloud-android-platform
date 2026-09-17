package com.goreecloud.android.platform.testing

import com.goreecloud.android.platform.core.GoreeCloudResult
import com.goreecloud.android.platform.core.RequestContext
import com.goreecloud.android.platform.identity.IdentitySessionSnapshot
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.test.Test
import kotlin.test.assertEquals

class FakeClientsTest {
    @Test
    fun fakeIdentityClientUsesConfiguredHandler() {
        val client = FakeIdentityClient {
            GoreeCloudResult.Success(IdentitySessionSnapshot(authenticated = false))
        }

        val result = runSuspend {
            client.currentSession(RequestContext("test-request"))
        }

        assertEquals(
            false,
            (result as GoreeCloudResult.Success).value.authenticated,
        )
    }

    private fun <T> runSuspend(block: suspend () -> T): T {
        var outcome: Result<T>? = null
        block.startCoroutine(object : Continuation<T> {
            override val context = EmptyCoroutineContext
            override fun resumeWith(result: Result<T>) {
                outcome = result
            }
        })
        return checkNotNull(outcome).getOrThrow()
    }
}
