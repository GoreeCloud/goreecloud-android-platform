# GoreeCloud Android Platform — Specification

## Document status

- **Repository:** `GoreeCloud/goreecloud-android-platform`
- **Lifecycle:** Development
- **Repository role:** Shared Android platform-development foundation
- **Primary languages:** Kotlin and Gradle Kotlin DSL
- **Primary runtime:** Android, with pure Kotlin/JVM contracts where Android APIs are unnecessary
- **License:** GNU Affero General Public License v3.0 or later (`AGPL-3.0-or-later`) under the current GoreeCloud default fallback licensing policy

## 1. Purpose

GoreeCloud Android Platform provides reusable first-party infrastructure for GoreeCloud Android applications. Its purpose is to reduce duplicated Gradle configuration, fragmented platform adapters, inconsistent Android conventions, and one-off testing infrastructure while preserving clear authority boundaries between consuming applications and GoreeCloud platform systems.

## 2. Architectural boundaries

The repository may define transport contracts, immutable models, validation helpers, test doubles, Android integration helpers, and presentation primitives. It must not become a second source of truth for data or decisions owned by another GoreeCloud system.

In particular:

- GoreeCloud Identity remains authoritative for identity, authentication, sessions, and identity facts.
- GoreeCloud Mesh remains authoritative for ecosystem discovery and coordination state.
- GoreeCloud Policy remains authoritative for shared policy evaluation and decision machinery while preserving domain-owner authority.
- Glaze UI remains authoritative for GoreeCloud design-system rules and semantics.
- Privacy Shield, Wardveil Security, Everkeep, GoreeCloud Manager, and GoreeCloud Observability retain their own authorities.

## 3. Initial modules

### `platform-core`

Pure Kotlin data models and contracts used by the other modules. It must remain Android-framework independent unless an Android-specific need is moved to `android-core`.

### `android-core`

Small Android runtime helpers and integration points. It may depend on `platform-core` but must not accumulate application business logic.

### `identity-adapter`

Interfaces and immutable session/subject models for consuming authoritative GoreeCloud Identity state without embedding authentication secrets in the API surface.

### `mesh-adapter`

Interfaces and immutable discovery models for locating approved GoreeCloud capabilities while preserving producer identity and version metadata.

### `policy-adapter`

Interfaces and immutable request/decision models for policy evaluation. Decision states must support allow, deny, conditional, defer, indeterminate, and error outcomes so callers do not silently coerce unknown states into allow.

### `glaze-ui`

Compose primitives that render semantic application state using the consuming application's `MaterialTheme` and current Glaze UI guidance. It must not hard-code a competing design authority.

### `testing`

Reusable deterministic test doubles for adapter contracts. Test helpers must not contain production credentials, endpoints, user data, or private infrastructure information.

## 4. Build baseline

- Java: 17
- Kotlin: 2.1.21
- Android Gradle Plugin: 8.10.1
- Gradle provisioned in CI: 8.11.1
- Android compile SDK: 36
- Android minimum SDK: 29

The build baseline must be reviewed when Android, Kotlin, AGP, Gradle, or GoreeCloud application requirements change. Target SDK remains a consuming-application responsibility until this repository contains an executable Android target whose target SDK is directly configured and validated.

## 5. Gradle conventions

`build-logic/` is an included build containing precompiled convention plugins. The initial convention set is:

- `goreecloud.kotlin.library`
- `goreecloud.android.library`
- `goreecloud.android.compose`

Conventions may set common language/toolchain levels, Android SDK baselines, compiler behavior, and repository-wide defaults. They must not hide security-sensitive behavior or environment-specific secrets.

## 6. API design

Public/shared APIs should prefer:

- Small immutable Kotlin data classes.
- Interfaces at cross-system boundaries.
- Explicit structured failure values.
- Explicit authority/source metadata where decisions or discovered capabilities could otherwise become ambiguous.
- Forward-compatible fields and enums where ecosystem evolution is expected.
- Correlation/request identifiers that help trace operations without embedding private data.

## 7. Security and privacy

The repository must not store production secrets, signing keys, reusable credentials, private endpoints, private repository inventory, personal data, or sensitive operational evidence. Adapters should minimize data exposed to callers and avoid APIs that encourage persistence of secrets.

Unknown, unavailable, stale, or failed policy/identity/discovery states must remain distinguishable from successful states.

## 8. CI and validation

CI should run independent repository validation, JVM tests, and Android build/lint/test jobs in parallel where safe. The CI implementation should use least-privilege workflow permissions and should not grant production secrets to ordinary pull-request validation.

## 9. Publication

The initial repository does not claim a published Maven coordinate or Stable release. Publication, signing, provenance, SBOM generation, and release compatibility guarantees require separate implementation and verification before any release claim is made.

## 10. Licensing

The repository currently uses the GoreeCloud default fallback license, `AGPL-3.0-or-later`, because no separate project-specific license decision has yet been established. Before a Stable publication, the shared library/SDK distribution model should be evaluated under the authoritative Software Licensing Policy to determine whether a project-specific license should supersede that fallback. Any license change is a controlled decision and must reconcile repository metadata, documentation, dependencies, contributor rights, and previously distributed versions.

## 11. Compatibility contract

`platform-core` contains the Development compatibility-contract model. Contract model version `0.1.0` defines explicit semantic-version requirements, optional Android API requirements, consumer environment facts, and three-state evaluation through `COMPATIBLE`, `INCOMPATIBLE`, and `INDETERMINATE` verdicts.

The evaluator must fail closed when a required fact is unknown: missing platform or required Android API information cannot become an implicit positive compatibility result. A known failed requirement establishes incompatibility even when another fact is unavailable.

The contract is intentionally limited to Android Platform compatibility. It does not assign protocol versions or compatibility state for producer systems. Identity, Mesh, Policy, Privacy Shield, Wardveil Security, Everkeep, Manager, Observability, and other system-specific contract versions remain controlled by their owning authorities.

The repository's minimum/compile SDK values are build inputs rather than a verified Stable runtime-support matrix. Runtime/API support, published coordinates, release support windows, binary compatibility guarantees, and consumer certification remain evidence-gated roadmap work.

## 12. Platform Contract

No `goreecloud.platform.yaml` is created until the authoritative schema supports a component type that truthfully represents this shared platform repository or governance explicitly classifies the repository as an existing supported type.
