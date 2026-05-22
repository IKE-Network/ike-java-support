package network.ike.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ReleasePolicy}: the ladder is complete, every rung
 * carries a term and a definition, and backing literals round-trip
 * through {@link ConstantBackedEnum#fromConstant}.
 */
class ReleasePolicyTest {

    @Test
    void ladderHasFiveRungs() {
        assertEquals(5, ReleasePolicy.values().length);
    }

    @Test
    void everyRungCarriesTermAndDefinition() {
        for (ReleasePolicy policy : ReleasePolicy.values()) {
            assertFalse(policy.term().isBlank(),
                    () -> policy + " has a blank term");
            assertFalse(policy.definition().isBlank(),
                    () -> policy + " has a blank definition");
            assertEquals(policy.constant(), policy.term(),
                    () -> policy + " constant and term should coincide");
        }
    }

    @Test
    void backingLiteralsRoundTrip() {
        for (ReleasePolicy policy : ReleasePolicy.values()) {
            Optional<ReleasePolicy> found =
                    ConstantBackedEnum.fromConstant(ReleasePolicy.class, policy.constant());
            assertTrue(found.isPresent(),
                    () -> "no round-trip for " + policy.constant());
            assertSame(policy, found.orElseThrow());
        }
    }

    @Test
    void fromConstantRejectsUnknownAndNull() {
        assertTrue(ConstantBackedEnum.fromConstant(ReleasePolicy.class, "publish").isEmpty());
        assertTrue(ConstantBackedEnum.fromConstant(ReleasePolicy.class, null).isEmpty());
    }
}
