# IKE Java Support

Shared, enforced-zero-dependency value types for the IKE Network.

`ike-java-support` is the home for small, compiler-visible value types
used across IKE tooling. The shipped artifact depends on nothing but
the JDK — a `maven-enforcer` rule fails the build on any compile- or
runtime-scope dependency.

## Contents

- **`ConstantBackedEnum`** — an enum whose every constant carries a
  stable string literal, for use where a compile-time `String`
  constant is required, such as annotation arguments.
- **`EnumDefinition`** — a `ConstantBackedEnum` that also carries a
  human-readable term and its one-sentence definition: a
  controlled-vocabulary entry expressed as an enum constant.
- **`ReleasePolicy`** — the release-cascade policy ladder
  (`notify → verify → propose → integrate → release`), the first
  `EnumDefinition`.

## Coordinates

`network.ike:ike-java-support` — a Tier 0 foundation artifact; parent
`ike-base-parent`; Java 21.

## Build

    mvn verify

## Tracking

Tracked by [IKE-Network/ike-issues#498](https://github.com/IKE-Network/ike-issues/issues/498).
