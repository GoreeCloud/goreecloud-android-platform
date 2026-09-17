package com.goreecloud.android.platform.core

@JvmInline
value class AuthorityId(val value: String) {
    init {
        require(value.isNotBlank()) { "Authority ID must not be blank." }
    }
}

data class PlatformVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
) : Comparable<PlatformVersion> {
    init {
        require(major >= 0 && minor >= 0 && patch >= 0) {
            "Version components must be non-negative."
        }
    }

    override fun compareTo(other: PlatformVersion): Int =
        compareValuesBy(this, other, PlatformVersion::major, PlatformVersion::minor, PlatformVersion::patch)

    override fun toString(): String = "$major.$minor.$patch"

    companion object {
        fun parse(value: String): PlatformVersion {
            val parts = value.trim().split('.')
            require(parts.size == 3) { "Expected semantic version in major.minor.patch form." }
            val numbers = parts.map { part ->
                require(part.isNotBlank() && part.all(Char::isDigit)) {
                    "Version components must be decimal integers."
                }
                part.toInt()
            }
            return PlatformVersion(numbers[0], numbers[1], numbers[2])
        }
    }
}

enum class CapabilityState {
    AVAILABLE,
    DEGRADED,
    UNAVAILABLE,
    UNKNOWN,
}

data class CapabilityDescriptor(
    val name: String,
    val version: PlatformVersion,
    val authority: AuthorityId,
    val state: CapabilityState = CapabilityState.UNKNOWN,
) {
    init {
        require(name.isNotBlank()) { "Capability name must not be blank." }
    }
}

data class RequestContext(
    val requestId: String,
    val attributes: Map<String, String> = emptyMap(),
) {
    init {
        require(requestId.isNotBlank()) { "Request ID must not be blank." }
    }
}

data class GoreeCloudError(
    val code: String,
    val message: String,
    val retryable: Boolean = false,
    val authority: AuthorityId? = null,
) {
    init {
        require(code.isNotBlank()) { "Error code must not be blank." }
        require(message.isNotBlank()) { "Error message must not be blank." }
    }
}

sealed interface GoreeCloudResult<out T> {
    data class Success<T>(val value: T) : GoreeCloudResult<T>
    data class Failure(val error: GoreeCloudError) : GoreeCloudResult<Nothing>
}
