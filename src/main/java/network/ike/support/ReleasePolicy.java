package network.ike.support;

/**
 * The release-cascade policy ladder: what a project does when an
 * upstream it depends on is released.
 *
 * <p>The five rungs are ordered by increasing autonomy, each
 * subsuming the intent of the ones before it — {@link #VERIFY}
 * confirms what {@link #NOTIFY} only observed, {@link #PROPOSE}
 * prepares the change {@link #VERIFY} validated, and so on up to
 * {@link #RELEASE}, which carries the cascade onward to its own
 * downstream consumers. A project declares its policy as a
 * {@code ${G·A·policy}} POM property; the version-management
 * extension validates the value and the release orchestrator
 * dispatches on it.
 *
 * @since 1
 */
public enum ReleasePolicy implements EnumDefinition {

    /** Record the upstream release; take no action on this project. */
    NOTIFY("notify",
            "Record that the upstream released; take no further action on this project."),

    /** Build against the released upstream to confirm compatibility. */
    VERIFY("verify",
            "Build this project against the released upstream to confirm compatibility, "
                    + "without changing or releasing it."),

    /** Open a pull request with the version bump for human review. */
    PROPOSE("propose",
            "Open a pull request that bumps the upstream version, "
                    + "for a human to review and merge."),

    /** Apply the version bump to the main branch without releasing. */
    INTEGRATE("integrate",
            "Bump the upstream version on the main branch, without releasing this project."),

    /** Bump, release, and carry the cascade downstream. */
    RELEASE("release",
            "Bump the upstream version, release this project, and continue the cascade "
                    + "to its downstream consumers.");

    private final String constant;
    private final String definition;

    ReleasePolicy(String constant, String definition) {
        this.constant = constant;
        this.definition = definition;
    }

    /**
     * {@inheritDoc}
     *
     * <p>For {@code ReleasePolicy} the backing literal is the rung
     * name as it appears in a {@code ${G·A·policy}} POM property.
     */
    @Override
    public String constant() {
        return constant;
    }

    /**
     * {@inheritDoc}
     *
     * <p>For {@code ReleasePolicy} the term and the {@link #constant()}
     * coincide: the rung name is both the vocabulary term and the
     * value declared in the POM.
     */
    @Override
    public String term() {
        return constant;
    }

    /** {@inheritDoc} */
    @Override
    public String definition() {
        return definition;
    }
}
