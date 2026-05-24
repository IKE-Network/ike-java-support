# IKE Java Support

[![Maven Central](https://img.shields.io/maven-central/v/network.ike/ike-java-support)](https://central.sonatype.com/artifact/network.ike/ike-java-support)
[![License](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Documentation](https://img.shields.io/badge/docs-ike.network%2Fike--java--support-blue)](https://ike.network/ike-java-support/)
[![IKE Network](https://img.shields.io/badge/IKE-Network-green)](https://ike.network/)

Shared, enforced-zero-dependency value types for the IKE Network.

`ike-java-support` is the home for small, compiler-visible value types
used across IKE tooling. The shipped artifact depends on nothing but
the JDK — a `maven-enforcer` rule fails the build on any compile- or
runtime-scope dependency.

It is a Tier-0 foundation leaf: the `ike-version-management-extension`
build extension depends on it, so it must resolve *above* every
project that registers the extension — its own repository, parented
directly to `ike-base-parent`, never buried in a Tier-1 application
repo where a build-extension dependency would close a release-graph
cycle.

## Contents

All types live in `network.ike.support.enums`.

- **`ConstantBackedEnum`** — pairs each enum constant with a matched
  `public static final String NAME_*` mirror and verifies the
  one-to-one correspondence at class-load. Java requires annotation
  element values to be constant expressions, which an enum reference
  is not; the mirror constant is. This lets an enum-backed name drive
  a `@Mojo(name = Goal.NAME_X)` annotation while the enum stays the
  single source of truth.
- **`EnumDefinition`** — a `ConstantBackedEnum` that also carries a
  human-readable term and its one-sentence definition: a
  controlled-vocabulary entry expressed as an enum constant.
- **`ReleasePolicy`** — the release-cascade policy ladder
  (`notify → verify → propose → integrate → release`), the first
  `EnumDefinition`.

## Coordinates

`network.ike:ike-java-support` — parent `ike-base-parent`; Java 21.

## Build

    mvn verify

## Tracking

Tracked by [IKE-Network/ike-issues#498](https://github.com/IKE-Network/ike-issues/issues/498).
