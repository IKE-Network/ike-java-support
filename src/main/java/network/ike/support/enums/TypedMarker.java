package network.ike.support.enums;

/**
 * The IKE typed-marker family — the closed set of name-segments that
 * the IKE property-naming convention recognizes inside an
 * {@code <G>__GA__<A>} or {@code <G>__GA__<A>__FACET} property name.
 *
 * <p>The convention's one rule: {@code __} is a separator between
 * adjacent name-parts in a property name; trailing {@code __} only
 * appears when something follows. {@link #GA} is a <em>separator</em>
 * marker (the {@code __} on both sides identify it as sitting between
 * two name-parts — the groupId on the left and the artifactId on the
 * right). {@link #VERSION} and {@link #POLICY} are <em>terminal facet</em>
 * markers (no trailing {@code __} because the XML structural terminator
 * — {@code >} for declarations, {@code }} for references — closes the
 * final segment).
 *
 * <p>The {@link Role} distinguishes the two positions and selects the
 * shape of the token returned by {@link #token()} — {@code __GA__} for
 * separators, {@code __VERSION} or {@code __POLICY} for terminal facets.
 * Future markers (e.g. {@code GAV}, {@code BOM}, {@code SCOPE},
 * {@code PARENT}, {@code CLASSIFIER}) slot in as additional enum
 * constants without further design.
 *
 * <p>Replaced the U+00B7 ({@code ·}) separator after IntelliJ's narrower
 * {@code [\w.\-]+}-style property-resolver regex was found to reject the
 * middle dot, breaking the {@code ${G·A}} chain and falsely flagging
 * plugin parameters as unknown. The typed-marker family stays inside
 * every resolver's identifier regex while remaining self-documenting and
 * greppable (e.g. {@code grep -E '__VERSION[>}]'}). See
 * IKE-Network/ike-issues#525.
 *
 * <p>Each rung carries a {@code NAME_*} mirror constant — the
 * {@link ConstantBackedEnum} pattern — so the rung name is usable where
 * a compile-time {@code String} constant is required; the class-load
 * {@link ConstantBackedEnum#verify verify} guard keeps the constants
 * and mirrors in lockstep.
 *
 * @since 6
 */
public enum TypedMarker implements EnumDefinition {

    /** Separator between groupId and artifactId in a canonical pin. */
    GA(TypedMarker.NAME_GA, Role.SEPARATOR,
            "Separator between the groupId and artifactId of a canonical "
                    + "version-pin property name. Appears as __GA__ — flanked "
                    + "by __ on both sides because it sits between two "
                    + "name-parts."),

    /** Terminal facet marking the version-pin value. */
    VERSION(TypedMarker.NAME_VERSION, Role.FACET,
            "Terminal facet marker for a version-pin property. A property "
                    + "named <G>__GA__<A>__VERSION pins the version of the "
                    + "G:A coordinate. Appears as __VERSION — no trailing "
                    + "__ because the XML terminator closes the final "
                    + "segment."),

    /** Terminal facet marking the release-policy value. */
    POLICY(TypedMarker.NAME_POLICY, Role.FACET,
            "Terminal facet marker for a release-policy property. A "
                    + "property named <G>__GA__<A>__POLICY declares the "
                    + "ReleasePolicy a project follows when the G:A "
                    + "upstream releases. Appears as __POLICY.");

    /**
     * Where in a property name a typed marker sits. Selects the shape
     * of the rendered token returned by {@link #token()}.
     */
    public enum Role {
        /** Marker sits between two name-parts: rendered as {@code __NAME__}. */
        SEPARATOR,
        /** Marker is terminal: rendered as {@code __NAME} (no trailing __). */
        FACET
    }

    /** Mirror constant for {@link #GA} — usable in annotations. */
    public static final String NAME_GA      = "GA";
    /** Mirror constant for {@link #VERSION}. */
    public static final String NAME_VERSION = "VERSION";
    /** Mirror constant for {@link #POLICY}. */
    public static final String NAME_POLICY  = "POLICY";

    private final String literalName;
    private final Role role;
    private final String definition;

    TypedMarker(String literalName, Role role, String definition) {
        this.literalName = literalName;
        this.role = role;
        this.definition = definition;
    }

    /**
     * {@inheritDoc}
     *
     * <p>For {@code TypedMarker} the literal is the bare marker name
     * (without {@code __} boundaries) — e.g. {@code "GA"},
     * {@code "VERSION"}, {@code "POLICY"}. Use {@link #token()} to get
     * the actual form that appears in a property name.
     */
    @Override
    public String literalName() {
        return literalName;
    }

    /**
     * {@inheritDoc}
     *
     * <p>For {@code TypedMarker} the term and {@link #literalName()}
     * coincide — the marker name is both the vocabulary term and the
     * literal that appears (between {@code __} boundaries) in a
     * property name.
     */
    @Override
    public String term() {
        return literalName;
    }

    /** {@inheritDoc} */
    @Override
    public String definition() {
        return definition;
    }

    /**
     * The position this marker occupies in a property name.
     *
     * @return {@link Role#SEPARATOR} when the marker sits between two
     *         name-parts (rendered as {@code __NAME__}),
     *         {@link Role#FACET} when the marker is terminal (rendered
     *         as {@code __NAME})
     */
    public Role role() {
        return role;
    }

    /**
     * This marker as it appears in a property name. Separators are
     * flanked by {@code __} on both sides; terminal facets carry only
     * the leading {@code __}. For example, {@code GA.token()} returns
     * {@code "__GA__"} and {@code POLICY.token()} returns
     * {@code "__POLICY"}.
     *
     * @return the rendered token, suitable for string-concatenating
     *         into a property name
     */
    public String token() {
        return role == Role.SEPARATOR
                ? "__" + literalName + "__"
                : "__" + literalName;
    }

    static {
        ConstantBackedEnum.verify(TypedMarker.class);
    }
}
