# Architecture

## Goals

GoreeCloud Android Platform centralizes reusable Android platform infrastructure while keeping application ownership and platform-system authority explicit.

The architecture is intentionally layered:

```text
GoreeCloud Android applications
        |
        +--> glaze-ui
        +--> android-core
        +--> identity-adapter
        +--> mesh-adapter
        +--> policy-adapter
                 |
                 +--> platform-core

Reusable tests --> testing --> adapter contracts / platform-core
Build behavior  --> build-logic --> all modules
```

## Layer 1 — `platform-core`

This is the lowest shared contract layer. It has no Android framework dependency. It owns generic request context, capability metadata, semantic version representation, authority references, and structured results/errors.

Keeping this module Android-free allows contracts to be unit-tested quickly and potentially shared with tooling that runs on the JVM.

## Layer 2 — platform adapters

Adapters expose narrow interfaces to authoritative GoreeCloud systems. They do not implement those systems locally.

- `identity-adapter` represents identity/session facts received from GoreeCloud Identity.
- `mesh-adapter` represents capability discovery results received from GoreeCloud Mesh.
- `policy-adapter` represents policy evaluation requests and decisions produced by GoreeCloud Policy.

Adapters should not persist authoritative remote state unless a separately reviewed cache contract defines freshness, invalidation, and privacy behavior.

## Layer 3 — Android integration

`android-core` contains Android-specific runtime helpers that do not belong in pure Kotlin contracts. Android framework types should remain here or in a more specific Android module rather than leaking into `platform-core`.

## Layer 4 — experience

`glaze-ui` contains Android Compose primitives. It consumes semantic state and the application's active theme. It must not infer security, privacy, identity, or policy conclusions from presentation state.

## Test architecture

`testing` provides deterministic fakes rather than networked integration implementations. This allows consuming repositories to verify behavior around allow/deny/unknown states without connecting to production services.

## Dependency direction

Dependencies should point inward toward smaller contracts. Core contracts must not depend on adapters, Android UI, applications, or production services. Cross-adapter dependencies require a documented reason because they can blur authority boundaries.

## Future modules

Additional adapters for Privacy Shield, Wardveil Security, Everkeep, GoreeCloud Manager, and GoreeCloud Observability should be introduced only after their authoritative Android-facing contracts are verified. The repository should prefer several focused modules over a single all-powerful client that obscures permissions and dependency boundaries.
