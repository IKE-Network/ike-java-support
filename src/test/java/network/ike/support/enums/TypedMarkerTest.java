package network.ike.support.enums;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TypedMarker}: the family is complete, every marker
 * carries a term and a definition, role discriminates SEPARATOR vs
 * FACET, and {@link TypedMarker#token()} renders the marker in its
 * position-appropriate form.
 */
class TypedMarkerTest {

    @Test
    void familyHasFourMarkers() {
        // GA, VERSION, POLICY, ALIAS (#526). Future additions (GAV,
        // BOM, SCOPE, …) will bump this; the test is intentionally a
        // tripwire so a forgotten test update flags itself.
        assertThat(TypedMarker.values()).hasSize(4);
    }

    @Test
    void everyMarkerCarriesTermAndDefinition() {
        for (TypedMarker marker : TypedMarker.values()) {
            assertThat(marker.term()).as("%s term", marker).isNotBlank();
            assertThat(marker.definition()).as("%s definition", marker).isNotBlank();
            assertThat(marker.term()).as("%s term == literalName", marker)
                    .isEqualTo(marker.literalName());
        }
    }

    @Test
    void literalsIndexToTheirConstants() {
        Map<String, TypedMarker> index = ConstantBackedEnum.index(TypedMarker.class);
        assertThat(index)
                .containsEntry("GA",      TypedMarker.GA)
                .containsEntry("VERSION", TypedMarker.VERSION)
                .containsEntry("POLICY",  TypedMarker.POLICY)
                .containsEntry("ALIAS",   TypedMarker.ALIAS)
                .hasSize(4);
    }

    @Test
    void gaIsASeparatorAndRendersFlanked() {
        assertThat(TypedMarker.GA.role()).isEqualTo(TypedMarker.Role.SEPARATOR);
        assertThat(TypedMarker.GA.token()).isEqualTo("__GA__");
    }

    @Test
    void versionIsAFacetAndRendersTerminal() {
        assertThat(TypedMarker.VERSION.role()).isEqualTo(TypedMarker.Role.FACET);
        assertThat(TypedMarker.VERSION.token()).isEqualTo("__VERSION");
    }

    @Test
    void policyIsAFacetAndRendersTerminal() {
        assertThat(TypedMarker.POLICY.role()).isEqualTo(TypedMarker.Role.FACET);
        assertThat(TypedMarker.POLICY.token()).isEqualTo("__POLICY");
    }

    @Test
    void aliasIsAFacetAndRendersTerminal() {
        assertThat(TypedMarker.ALIAS.role()).isEqualTo(TypedMarker.Role.FACET);
        assertThat(TypedMarker.ALIAS.token()).isEqualTo("__ALIAS");
    }

    @Test
    void tokensComposeIntoCanonicalPropertyName() {
        String pin = "network.ike"
                + TypedMarker.GA.token()
                + "ike-java-support"
                + TypedMarker.VERSION.token();
        assertThat(pin).isEqualTo("network.ike__GA__ike-java-support__VERSION");

        String policy = "network.ike"
                + TypedMarker.GA.token()
                + "ike-java-support"
                + TypedMarker.POLICY.token();
        assertThat(policy).isEqualTo("network.ike__GA__ike-java-support__POLICY");
    }
}
