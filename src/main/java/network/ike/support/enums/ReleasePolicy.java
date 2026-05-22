package network.ike.support.enums;

/**
 * The release-cascade policy ladder: what a project does when an
 * upstream it depends on is released.
 *
 * <p>The five rungs are ordered by increasing autonomy, each subsuming
 * the intent of the ones before it — {@link #VERIFY} confirms what
 * {@link #NOTIFY} only observed, {@link #PROPOSE} prepares the change
 * {@link #VERIFY} validated, and so on up to {@link #RELEASE}, which
 * carries the cascade onward to its own downstream consumers. A
 * project declares its policy as a {@code ${G·A·policy}} POM property;
 * the version-management extension validates the value and the release
 * orchestrator dispatches on it.
 *
 * <p>Each rung carries a {@code NAME_*} mirror constant — the
 * {@link ConstantBackedEnum} pattern — so the rung name is usable
 * where a compile-time {@code String} constant is required; the
 * class-load {@link ConstantBackedEnum#verify verify} guard keeps the
 * constants and mirrors in lockstep.
 *
 * @since 1
 */
public enum ReleasePolicy implements EnumDefinition {

    /** Record the upstream release; take no action on this project. */
    NOTIFY(ReleasePolicy.NAME_NOTIFY,
            "Record that the upstream released; take no further action on this project."),

    /** Build against the released upstream to confirm compatibility. */
    VERIFY(ReleasePolicy.NAME_VERIFY,
            "Build this project against the released upstream to confirm compatibility, "
                    + "without changing or releasing it."),

    /** Open a pull request with the version bump for human review. */
    PROPOSE(ReleasePolicy.NAME_PROPOSE,
            "Open a pull request that bumps the upstream version, "
                    + "for a human to review and merge."),

    /** Apply the version bump to the main branch without releasing. */
    INTEGRATE(ReleasePolicy.NAME_INTEGRATE,
            "Bump the upstream version on the main branch, without releasing this project."),

    /** Bump, release, and carry the cascade downstream. */
    RELEASE(ReleasePolicy.NAME_RELEASE,
            "Bump the upstream version, release this project, and continue the cascade "
                    + "to its downstream consumers.");

    /** Mirror constant for {@link #NOTIFY} — usable in annotations. */
    public static final String NAME_NOTIFY    = "notify";
    /** Mirror constant for {@link #VERIFY}. */
    public static final String NAME_VERIFY    = "verify";
    /** Mirror constant for {@link #PROPOSE}. */
    public static final String NAME_PROPOSE   = "propose";
    /** Mirror constant for {@link #INTEGRATE}. */
    public static final String NAME_INTEGRATE = "integrate";
    /** Mirror constant for {@link #RELEASE}. */
    public static final String NAME_RELEASE   = "release";

    private final String literalName;
    private final String definition;

    ReleasePolicy(String literalName, String definition) {
        this.literalName = literalName;
        this.definition = definition;
    }

    /**
     * {@inheritDoc}
     *
     * <p>For {@code ReleasePolicy} the literal is the rung name as it
     * appears in a {@code ${G·A·policy}} POM property.
     */
    @Override
    public String literalName() {
        return literalName;
    }

    /**
     * {@inheritDoc}
     *
     * <p>For {@code ReleasePolicy} the term and {@link #literalName()}
     * coincide — the rung name is both the vocabulary term and the
     * value declared in the POM.
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

    static {
        ConstantBackedEnum.verify(ReleasePolicy.class);
    }
}
