# GoreeCloud Android Platform

`goreecloud-android-platform` is the shared Android foundation for GoreeCloud-owned Android applications. It centralizes reusable Gradle conventions, Android/Kotlin platform primitives, first-party adapter contracts, Glaze UI Android components, test utilities, and common validation tooling so individual applications do not have to reimplement the same platform plumbing.

## Status

**Development.** This repository is a platform foundation and does not by itself establish Stable, release, production, or Integral Platform System conformance for any consuming application.

## Responsibilities

This repository is intended to provide:

- Shared Gradle convention plugins for GoreeCloud Android and Kotlin modules.
- Common Android and Kotlin platform primitives that are safe to reuse across applications.
- Android-facing adapter contracts for GoreeCloud Identity, GoreeCloud Mesh, and GoreeCloud Policy.
- A versioned, fail-closed Android Platform compatibility-contract model for explicitly declared consumer requirements.
- Glaze UI Android/Compose primitives that consume authoritative design-system guidance without redefining it.
- Reusable test doubles and contract-test helpers.
- Repository validation and CI patterns for Android platform development.

It is **not** an alternate authority for Identity, Mesh, Policy, Glaze UI, privacy, security, continuity, management, or observability state. Adapters transport or present authoritative state produced by the owning GoreeCloud systems.

## Repository layout

| Path | Purpose |
|---|---|
| `build-logic/` | Reusable Gradle convention plugins used by this repository and suitable for extraction/reuse. |
| `platform-core/` | Pure Kotlin platform contracts, capability metadata, request context, versions, compatibility evaluation, and structured results. |
| `android-core/` | Android-runtime helpers that depend on `platform-core`. |
| `identity-adapter/` | GoreeCloud Identity client contracts for Android consumers. |
| `mesh-adapter/` | GoreeCloud Mesh discovery/client contracts for Android consumers. |
| `policy-adapter/` | GoreeCloud Policy evaluation/client contracts for Android consumers. |
| `glaze-ui/` | Android Compose primitives aligned to Glaze UI semantics. |
| `testing/` | Reusable test doubles for platform contracts. |
| `docs/` | Architecture, compatibility, and development guidance. |
| `scripts/` | Repository validation and maintenance scripts. |

## Build

The current baseline uses Java 17, Kotlin 2.1.21, Android Gradle Plugin 8.10.1, compile SDK 36, and minimum SDK 29. These values are repository build inputs and may be raised through normal compatibility review.

A Gradle installation compatible with AGP 8.10.1 is required. CI provisions Gradle 8.11.1 explicitly.

```bash
gradle platformCheck
gradle :android-core:assembleDebug :glaze-ui:assembleDebug
```

## Compatibility

The Development compatibility-contract model is version `0.1.0`. It evaluates only explicitly declared platform-version and Android API requirements and returns `COMPATIBLE`, `INCOMPATIBLE`, or `INDETERMINATE`. Unknown required facts are not treated as compatible.

The model does not establish a Stable runtime-support matrix or invent producer-system protocol versions. See [Compatibility model](docs/COMPATIBILITY.md) and [Compatibility contract](docs/COMPATIBILITY-CONTRACT.md).

## Design rules

1. Keep shared contracts small and producer-authority aware.
2. Do not copy application-specific business logic into the platform repository.
3. Do not encode secrets, tokens, production endpoints, private inventory, or environment-specific credentials.
4. Prefer interfaces and immutable data models at adapter boundaries.
5. Preserve Android/Gradle conventions instead of inventing a parallel build system.
6. Keep Glaze UI rendering separate from authoritative policy, identity, security, privacy, and operational decisions.
7. Treat compatibility claims as evidence-backed statements, not assumptions.

## Platform Contract applicability

The current GoreeCloud Platform Contract schema is governed for application/service components. This repository is a shared development foundation, so it does not fabricate an application/service `goreecloud.platform.yaml`. If governance adds an applicable shared-library/platform component type, this repository should adopt it through a reviewed migration.

## Documentation

- [Specification](SPECIFICATIONS.md)
- [Feature roadmap](FEATURE-ROADMAP.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Development guide](docs/DEVELOPMENT.md)
- [Compatibility model](docs/COMPATIBILITY.md)
- [Compatibility contract](docs/COMPATIBILITY-CONTRACT.md)
- [Security guidance](SECURITY.md)
- [Contributing](CONTRIBUTING.md)

## License

GNU Affero General Public License v3.0 or later (`AGPL-3.0-or-later`) under the current GoreeCloud default fallback licensing policy. A project-specific library/SDK license may supersede this only through a controlled licensing decision. See [LICENSE](LICENSE).
