# Contributing

GoreeCloud Android Platform is a shared foundation, so changes should optimize for reusable value without introducing application-specific coupling.

## Workflow

1. Start from the current `main` revision.
2. Use a short-lived purpose-specific branch such as `feature/...`, `fix/...`, `refactor/...`, `test/...`, `ci/...`, or `docs/...`.
3. Keep one coherent change set per branch.
4. Add or update tests for behavior changes.
5. Run the repository validation and applicable Gradle checks.
6. Open a pull request describing purpose, scope, validation, compatibility impact, and any security/privacy implications.

## Local validation

```bash
bash scripts/validate-repository.sh
gradle :platform-core:test :identity-adapter:test :mesh-adapter:test :policy-adapter:test :testing:test
gradle :android-core:lintDebug :android-core:testDebugUnitTest :android-core:assembleDebug
gradle :glaze-ui:lintDebug :glaze-ui:testDebugUnitTest :glaze-ui:assembleDebug
```

## API changes

Shared API changes must preserve authority boundaries and should be backward-compatible unless a reviewed breaking change is justified. Avoid leaking Android framework types into pure Kotlin modules unless the module's purpose explicitly requires them.

## Documentation

Repository documentation is Markdown-first. Update the README, specification, architecture, compatibility guidance, and roadmap whenever the corresponding implementation state materially changes.

## Sensitive information

Never commit secrets, private endpoints, production identifiers, personal data, or restricted operational evidence. Use sanitized examples and test fixtures.
