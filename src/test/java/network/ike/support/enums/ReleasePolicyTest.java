package network.ike.support.enums;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ReleasePolicy}: the ladder is complete, every rung
 * carries a term and a definition, and the literals index cleanly to
 * their constants.
 */
class ReleasePolicyTest {

    @Test
    void ladderHasFiveRungs() {
        assertThat(ReleasePolicy.values()).hasSize(5);
    }

    @Test
    void everyRungCarriesTermAndDefinition() {
        for (ReleasePolicy policy : ReleasePolicy.values()) {
            assertThat(policy.term()).as("%s term", policy).isNotBlank();
            assertThat(policy.definition()).as("%s definition", policy).isNotBlank();
            assertThat(policy.term()).as("%s term == literalName", policy)
                    .isEqualTo(policy.literalName());
        }
    }

    @Test
    void literalsIndexToTheirConstants() {
        Map<String, ReleasePolicy> index = ConstantBackedEnum.index(ReleasePolicy.class);
        assertThat(index)
                .containsEntry("notify",    ReleasePolicy.NOTIFY)
                .containsEntry("verify",    ReleasePolicy.VERIFY)
                .containsEntry("propose",   ReleasePolicy.PROPOSE)
                .containsEntry("integrate", ReleasePolicy.INTEGRATE)
                .containsEntry("release",   ReleasePolicy.RELEASE)
                .hasSize(5);
    }
}
