# GoreeCloud Android Platform — Compatibility Contract

**Document version:** v0.1.1  
**Status:** Development  
**Contract model version:** 0.1.0  
**Repository:** `GoreeCloud/goreecloud-android-platform`

## Purpose

This document defines the first versioned compatibility-contract model for GoreeCloud Android Platform consumers. It provides a deterministic, fail-closed way to compare a consumer environment with explicitly declared platform and Android API requirements.

This is a **Development contract model**, not a Stable support matrix, publication promise, or statement that every GoreeCloud Android application has already adopted the platform.

## Contract ownership boundary

This repository owns only the Android platform compatibility model that it defines.

It does not assign or manufacture protocol versions for GoreeCloud Identity, GoreeCloud Mesh, GoreeCloud Policy, Privacy Shield, Wardveil Security, Everkeep, GoreeCloud Manager, GoreeCloud Observability, or any other producer system. Producer-system protocol and capability versions remain authoritative in their owning systems and must be incorporated only after those contracts are verified.

## Model

The contract model is implemented in `platform-core` through:

- `VersionRequirement` — an inclusive minimum and optional exclusive maximum semantic-version range.
- `AndroidApiRequirement` — an inclusive Android API-level range.
- `ConsumerEnvironment` — the consumer facts available for evaluation.
- `AndroidPlatformCompatibilityContract` — the versioned declaration and evaluator.
- `CompatibilityVerdict` — `COMPATIBLE`, `INCOMPATIBLE`, or `INDETERMINATE`.
- `CompatibilityAssessment` — a verdict plus explanatory reasons.

## Android runtime fact bridge

`android-core` now provides a narrow bridge from verified local Android runtime facts into the compatibility model through `AndroidRuntimeSnapshot`.

`AndroidRuntime.snapshot()` reads `Build.VERSION.SDK_INT` and `Build.VERSION.PREVIEW_SDK_INT`. A snapshot can then create a `ConsumerEnvironment` or evaluate a compatibility contract while a caller supplies the exact GoreeCloud Android Platform version it is actually using.

The bridge intentionally does **not** infer platform artifact identity from a branch name, package name, Gradle setting, or Android build. If the caller cannot establish the platform version, that fact remains unknown and the compatibility model can return `INDETERMINATE` where the version is required.

Preview builds are also not promoted to an unreleased final API level. The compatibility environment uses the runtime-reported `SDK_INT`; `PREVIEW_SDK_INT` is retained separately as evidence that the device is running a preview build.

This runtime bridge improves evidence quality for an individual process. It is not a substitute for the still-open automated multi-API/runtime validation needed before declaring a supported Android range.

## Evaluation rules

Compatibility evaluation follows these rules:

1. A known platform version outside the declared platform range is `INCOMPATIBLE`.
2. When an Android API requirement is declared, a known API level outside that range is `INCOMPATIBLE`.
3. A required fact that is unavailable is `INDETERMINATE`; unknown state is never silently treated as compatible.
4. A concrete incompatibility takes precedence over an additional unknown fact because a verified failed requirement is sufficient to reject the environment.
5. `COMPATIBLE` is returned only when every declared requirement can be evaluated and is satisfied.
6. Omitting an Android API requirement means the contract makes no Android API compatibility claim for that dimension; it does not mean every Android API level is supported.

## Current repository baseline

The repository currently records the Development artifact version as `0.1.0-SNAPSHOT` and builds with minimum SDK 29 and compile/target SDK 36.

Those SDK values are current build inputs. They do **not** by themselves establish a verified runtime-support range for published artifacts or consuming applications. Runtime/API-level support remains evidence-gated until the applicable automated and representative-device compatibility validation exists.

## Version-range semantics

Platform ranges use an inclusive minimum and optional exclusive maximum. For example:

`[0.1.0, 0.2.0)`

accepts `0.1.0` through versions lower than `0.2.0` and rejects `0.2.0` and later versions.

An omitted upper bound is permitted by the model but should be used only when the owning compatibility decision intentionally allows an open-ended range.

## Publication boundary

No Maven coordinate, Stable artifact, release support window, binary-compatibility guarantee, signed release, SBOM/provenance guarantee, or production consumer certification is established by this document.

Those remain separate roadmap and release-lifecycle gates.

## Adoption requirements

Before a consuming GoreeCloud application treats an Android Platform version as an accepted dependency, the consumer should have evidence for the exact platform artifact or source revision it uses, the compatibility requirement it declares, and any producer-system contracts on which the selected adapters depend.

The first and second consumer pilots remain required to prove that the shared abstractions work across independently governed applications.
