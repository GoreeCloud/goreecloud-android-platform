# Development Guide

## Prerequisites

- JDK 17
- Gradle 8.11.1 or another version verified compatible with AGP 8.10.1
- Android SDK platform 36 for Android modules
- Git

## Common commands

```bash
bash scripts/validate-repository.sh

gradle :platform-core:test \
  :identity-adapter:test \
  :mesh-adapter:test \
  :policy-adapter:test \
  :testing:test

gradle :android-core:lintDebug \
  :android-core:testDebugUnitTest \
  :android-core:assembleDebug \
  :glaze-ui:lintDebug \
  :glaze-ui:testDebugUnitTest \
  :glaze-ui:assembleDebug
```

`gradle platformCheck` runs the repository's combined unit-test lifecycle task.

## Adding a module

A new module should have a distinct responsibility and should not be created solely to mirror an organizational chart. Choose the smallest applicable convention plugin:

- `goreecloud.kotlin.library` for Android-independent Kotlin/JVM contracts and utilities.
- `goreecloud.android.library` for Android libraries without Compose UI.
- `goreecloud.android.compose` for Android Compose libraries.

Every Android library must define its own namespace.

## Adapter requirements

New adapters should:

1. Identify the authoritative producer system.
2. Expose immutable models wherever practical.
3. Preserve unknown/error states rather than silently accepting them.
4. Avoid embedding endpoints, credentials, secrets, or environment-specific identifiers.
5. Carry source/version/freshness information when stale state could create incorrect behavior.
6. Include deterministic unit tests or reusable contract tests.

## Build configuration changes

Toolchain upgrades affect multiple consumers. Update `gradle/libs.versions.toml`, `build-logic`, CI provisioning, compatibility documentation, and affected tests together when a version change is accepted.

## Release discipline

Do not publish artifacts directly from an unreviewed topic branch. Published packages must eventually be traceable to an accepted exact source revision with release metadata, checksums/provenance, and any required signing/attestation.
