# Compatibility Model

**Document version:** v1.1  
**Lifecycle:** Development

## Current baseline

| Dimension | Baseline |
|---|---|
| Java | 17 |
| Kotlin | 2.1.21 |
| Android Gradle Plugin | 8.10.1 |
| CI Gradle | 8.11.1 |
| Compile SDK | 36 |
| Minimum SDK | 29 |
| Compose UI | 1.8.2 |
| Material 3 | 1.3.2 |

These are build baselines for the Development repository. They are not a guarantee that every GoreeCloud Android application already uses the same versions, and the SDK values are not by themselves a verified runtime-support matrix.

## Versioned compatibility contract

The first Development compatibility-contract model is implemented in `platform-core` and documented in [COMPATIBILITY-CONTRACT.md](COMPATIBILITY-CONTRACT.md).

Contract model version `0.1.0` provides explicit semantic-version and Android API requirement types plus three-state evaluation: `COMPATIBLE`, `INCOMPATIBLE`, and `INDETERMINATE`. Missing required facts remain indeterminate rather than being silently treated as compatible.

`android-core` now connects verified local Android runtime facts to that model. `AndroidRuntimeSnapshot` can supply the runtime-reported `SDK_INT` to a `ConsumerEnvironment` and evaluate a contract while the consuming application supplies the exact platform version it actually uses. The helper does not infer platform identity and does not promote preview builds to an unreleased final Android API level.

The runtime bridge provides evidence for the current process only. It does not complete the still-open multi-API/runtime compatibility-validation work required before publishing a supported Android range.

The contract model does not manufacture producer-system protocol versions. Identity, Mesh, Policy, Privacy Shield, Wardveil Security, Everkeep, Manager, Observability, and other producer contract versions remain authoritative in their owning systems.

## Compatibility rules

1. Consuming applications must not be forced to upgrade solely for cosmetic uniformity. A platform baseline change needs a technical or lifecycle reason.
2. Public/shared APIs should be source- and binary-compatible within a declared supported line unless a reviewed breaking release explicitly changes the contract.
3. Android minimum SDK increases require consumer impact review.
4. Kotlin, AGP, Compose, and Gradle upgrades must be tested together because compatibility is coupled.
5. An adapter protocol/version claim must come from the authoritative producer contract, not from this repository guessing a version.
6. Unknown required compatibility facts must remain explicitly indeterminate until they can be verified.
7. A `COMPATIBLE` result only applies to the requirements actually declared in the evaluated contract; omitted dimensions are not implicit support claims.
8. A local runtime fact does not establish an estate-wide or release-wide support guarantee; supported Android ranges require representative automated validation.

## Versioning direction

Before the first Stable publication, artifacts use Development/SNAPSHOT semantics. The repository currently records `goreecloud.version=0.1.0-SNAPSHOT`.

A future published support contract still needs evidence-backed decisions for:

- Module coordinates.
- Stable semantic-versioning and compatibility guarantees.
- Verified supported Android API/runtime range.
- Supported Kotlin/AGP/Compose ranges where materially relevant.
- Deprecation windows.
- Cross-module compatibility guarantees.
- Producer-system contract versions for adapters.
- First- and second-consumer adoption evidence.

The versioned contract machinery and local Android runtime fact bridge are implemented, but Stable publication and the complete support matrix remain separate open gates.
