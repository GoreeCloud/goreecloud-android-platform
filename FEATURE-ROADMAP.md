# GoreeCloud Android Platform — Feature Roadmap

**Lifecycle:** Development  
**Roadmap authority:** Repository-local roadmap for this shared Android platform foundation.

## Foundation

- [x] Establish standard Gradle/Kotlin/Android repository structure.
- [x] Add version catalog and shared build properties.
- [x] Add reusable Kotlin, Android library, and Android Compose convention plugins.
- [x] Add pure Kotlin platform-core contracts and structured result primitives.
- [x] Add first Identity, Mesh, and Policy adapter contracts.
- [x] Add Android runtime foundation module.
- [x] Add initial Glaze UI Compose semantic status primitive.
- [x] Add reusable adapter test doubles.
- [x] Add repository validation, CI, issue templates, pull-request template, CODEOWNERS, and dependency automation configuration.

## Near-term development

- [ ] Add Privacy Shield Android adapter contracts after the authoritative privacy integration contract is verified.
- [ ] Add Wardveil Security Android adapter contracts after the authoritative security integration contract is verified.
- [ ] Add Everkeep Android adapter contracts after the authoritative continuity/recovery contract is verified.
- [ ] Add GoreeCloud Manager Android administration-client contracts where Android clients require them.
- [ ] Add GoreeCloud Observability Android evidence/telemetry contracts with Privacy Shield-compatible minimization.
- [ ] Expand Glaze UI Android primitives from current authoritative Glaze UI tokens/components without duplicating design authority.
- [ ] Add adapter contract-test suites that can be consumed by GoreeCloud Android applications.
- [ ] Add sample integration module(s) only when they provide executable validation rather than placeholder scaffolding.

## Build and distribution

- [ ] Establish verified Maven coordinates and publication repositories.
- [ ] Add source/Javadoc artifacts where applicable.
- [ ] Add SBOM generation for published artifacts.
- [ ] Add build provenance and checksums for releases.
- [ ] Add signing/attestation after an approved signing mechanism is verified.
- [x] Pin GitHub Actions to verified immutable revisions through the approved GoreeCloud workflow process.
- [ ] Add a verified self-hosted Android runner path after current runner labels, isolation, and capabilities are authoritatively known.
- [ ] Evaluate the shared library/SDK distribution model under the authoritative Software Licensing Policy before the first Stable publication and decide whether a project-specific license should supersede the current `AGPL-3.0-or-later` fallback.

## Compatibility and adoption

- [ ] Define the first versioned Android Platform compatibility contract.
- [ ] Pilot consumption in one GoreeCloud Android application through a reviewed dependency path.
- [ ] Validate a second consumer to prove the abstractions are reusable rather than application-specific.
- [ ] Document migration patterns for existing GoreeCloud Android repositories.
- [ ] Add automated compatibility tests across supported Android API levels.

## Governance

- [ ] Resolve authoritative Platform Contract component-type applicability for shared platform/library repositories.
- [ ] Protect `main` with the applicable GoreeCloud branch/ruleset baseline when provider administration capability is available.
- [ ] Define release support windows and deprecation policy before the first Stable artifact.

Roadmap items remain open until implemented and verified or explicitly superseded/cancelled through the authoritative process.
