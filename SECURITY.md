# Security Policy

## Current support state

GoreeCloud Android Platform is in **Development**. No Stable release or production-support window is claimed yet.

## Reporting a vulnerability

Do not publish credentials, private infrastructure details, personal data, exploit details, or other sensitive security information in a public issue.

Use GitHub private vulnerability reporting/security-advisory facilities for this repository when available. If those facilities are unavailable, use an approved private GoreeCloud communication channel with the repository owner rather than a public issue.

A useful report includes the affected component, exact revision/version, reproduction conditions, impact, and any safe mitigation information.

## Security boundaries

This repository must not contain:

- Production credentials, API tokens, signing keys, or private keys.
- Active environment files containing secrets.
- Private infrastructure inventory or restricted network details.
- Real user data or production database extracts.
- Reusable authentication/session tokens in fixtures, examples, or logs.

Adapter APIs should preserve fail-closed/explicit unknown states where authorization, policy, or discovery results cannot be established. Consuming applications remain responsible for enforcing the authoritative decisions returned by the owning platform systems.

## Dependencies and automation

Dependency updates and GitHub Actions changes must remain reviewable and validated. Automated updates do not receive release, Stable, or production acceptance merely because CI succeeds.
