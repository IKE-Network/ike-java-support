---
date_published: 1980-01-31
date_modified: 1980-01-31
canonical_url: https://github.com/IKE-Network/ike-java-support/index.html
---

# IKE Java Support

[https://central.sonatype.com/artifact/network.ike/ike-java-support](https://central.sonatype.com/artifact/network.ike/ike-java-support)[1]

`network.ike:ike-java-support` is a Tier-0 foundation leaf — shared, enforced-zero-dependency value types for the IKE Network.

The shipped artifact depends on nothing but the JDK. A `maven-enforcer` rule fails the build on any compile- or runtime-scope dependency.

## [#contents](#contents)Contents

All types live in `network.ike.support.enums`.

- **`ConstantBackedEnum`** — pairs each enum constant with a matched `public static final String NAME_*` mirror and verifies the one-to-one correspondence at class-load. Java requires annotation element values to be constant expressions, which an enum reference is not; the mirror constant is. This lets an enum-backed name drive a `@Mojo(name = Goal.NAME_X)` annotation while the enum stays the single source of truth.
- **`EnumDefinition`** — a `ConstantBackedEnum` that also carries a human-readable term and its one-sentence definition: a controlled-vocabulary entry expressed as an enum constant.
- **`ReleasePolicy`** — the release-cascade policy ladder (`notify → verify → propose → integrate → release`), the first `EnumDefinition`.

## [#foundation-position](#foundation-position)Foundation position

ike-java-support sits just below `ike-base-parent` in the build-and-release dependency chain. It must resolve **above** every project that registers `ike-version-management-extension` (which depends on it), so it lives in its own repository, parented directly to `ike-base-parent` — never buried in a Tier-1 application repo where a build-extension dependency would close a release-graph cycle.

```
ike-base-parent          (Tier 0 parent inheritance)
        |
        v
ike-java-support         (this artifact)
        |
        v
ike-version-management-extension
        |
        v
all consumer builds
```

## [#coordinates](#coordinates)Coordinates

```
<dependency>
    <groupId>network.ike</groupId>
    <artifactId>ike-java-support</artifactId>
    <version>1</version>
</dependency>
```

Available from Maven Central.

## [#tracking](#tracking)Tracking

- [IKE-Network/ike-issues#498](https://github.com/IKE-Network/ike-issues/issues/498)[2] — Release Policy Layer, the epic that established this artifact.
