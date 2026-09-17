#!/usr/bin/env bash
set -euo pipefail

required_files=(
  README.md
  SPECIFICATIONS.md
  FEATURE-ROADMAP.md
  SECURITY.md
  CONTRIBUTING.md
  LICENSE
  .editorconfig
  .gitignore
  settings.gradle.kts
  build.gradle.kts
  gradle.properties
  gradle/libs.versions.toml
  build-logic/build.gradle.kts
  docs/ARCHITECTURE.md
  docs/DEVELOPMENT.md
  docs/COMPATIBILITY.md
)

missing=0
for file in "${required_files[@]}"; do
  if [[ ! -f "$file" ]]; then
    echo "Missing required repository file: $file" >&2
    missing=1
  fi
done

if [[ "$missing" -ne 0 ]]; then
  exit 1
fi

if grep -RInE '(BEGIN (RSA|OPENSSH|EC|PGP) PRIVATE KEY|gh[pousr]_[A-Za-z0-9_]{20,}|AIza[0-9A-Za-z_-]{20,})' \
  --exclude-dir=.git --exclude='validate-repository.sh' .; then
  echo "Potential secret material detected." >&2
  exit 1
fi

for module in platform-core android-core identity-adapter mesh-adapter policy-adapter glaze-ui testing; do
  if [[ ! -f "$module/build.gradle.kts" ]]; then
    echo "Missing build file for module: $module" >&2
    exit 1
  fi
done

echo "Repository structure validation passed."
