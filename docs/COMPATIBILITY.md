# Compatibility Model

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

These are build baselines for the Development repository. They are not a guarantee that every GoreeCloud Android application already uses the same versions.

## Compatibility rules

1. Consuming applications must not be forced to upgrade solely for cosmetic uniformity. A platform baseline change needs a technical or lifecycle reason.
2. Public/shared APIs should be source- and binary-compatible within a declared supported line unless a reviewed breaking release explicitly changes the contract.
3. Android minimum SDK increases require consumer impact review.
4. Kotlin, AGP, Compose, and Gradle upgrades must be tested together because compatibility is coupled.
5. An adapter protocol/version claim must come from the authoritative producer contract, not from this repository guessing a version.
6. Unknown producer versions should fail explicitly or negotiate through documented compatibility rules.

## Versioning direction

Before the first Stable publication, artifacts use Development/SNAPSHOT semantics. The first published compatibility contract should define:

- Module coordinates.
- Semantic-versioning rules.
- Supported Android API range.
- Supported Kotlin/AGP/Compose ranges where materially relevant.
- Deprecation windows.
- Cross-module compatibility guarantees.
- Producer-system contract versions for adapters.

Until that contract is accepted, this document records the build baseline only.
