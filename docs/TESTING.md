# GoreeCloud Android Platform — Testing Guidance

**Document version:** v0.1.0  
**Status:** Development

## Purpose

The `testing` module provides deterministic helpers that GoreeCloud Android applications can use in their own test suites without duplicating basic adapter-contract validation.

The module does not certify producer systems and does not replace Identity, Mesh, Policy, privacy, security, recovery, management, observability, or application-specific acceptance testing.

## Test doubles

The module provides configurable fake clients for:

- GoreeCloud Identity
- GoreeCloud Mesh
- GoreeCloud Policy

Each fake delegates to a caller-provided suspend handler so a consumer can model success, failure, degraded, unavailable, and edge-case behavior without production credentials or endpoints.

## Reusable contract checks

`AdapterContractChecks` validates structural invariants owned by this repository:

- `identitySession(...)` verifies that a successful Identity snapshot reports the expected authoritative producer identity.
- `meshDiscovery(...)` verifies that successful discovery results match the requested capability name and any requested minimum version.
- `policyEvaluation(...)` verifies that a successful Policy evaluation reports the expected authoritative producer identity.

A `GoreeCloudResult.Failure` is treated as a valid transport/result shape by these structural checks. A failure may still be the expected subject of a consumer test; the helper does not reinterpret an unavailable producer as a contract violation.

Each check returns `ContractCheckReport` with zero or more `ContractViolation` records. Consumers may inspect the report directly or call `requirePassed()` when a failing structural contract should fail the surrounding test.

## Example

```kotlin
val report = AdapterContractChecks.meshDiscovery(
    query = MeshCapabilityQuery(
        name = "example.capability",
        minimumVersion = PlatformVersion(1, 0, 0),
    ),
    result = resultFromClient,
)

report.requirePassed()
```

## Authority boundary

The reusable checks intentionally validate only invariants represented by the shared Android adapter types. They do not claim that:

- a producer is healthy or production-ready;
- a producer protocol version is approved;
- a policy decision is substantively correct;
- an Identity session is authorized for an application action;
- a discovered endpoint is trusted merely because its data structure is valid; or
- a consuming application has completed representative-device, accessibility, privacy, security, recovery, or release acceptance.

Those conclusions require evidence from the owning systems and the consuming application's own validation process.

## Consumer adoption

The first real GoreeCloud Android application pilot and a second independent consumer remain required before the shared testing abstractions can be treated as broadly validated across the application portfolio.
