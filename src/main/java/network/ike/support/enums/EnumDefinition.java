package network.ike.support.enums;

/**
 * A {@link ConstantBackedEnum} that also carries a human-readable term
 * and its definition.
 *
 * <p>An {@code EnumDefinition} expresses a controlled-vocabulary entry
 * as an enum constant: the {@link #term()} is the word being defined,
 * the {@link #definition()} states what it means, and the inherited
 * {@link #literalName()} is the stable literal the term is encoded as —
 * in a POM property, on the command line, and so on. For many
 * vocabularies the term and the literal coincide.
 *
 * @since 1
 */
public interface EnumDefinition extends ConstantBackedEnum {

    /**
     * The human-readable term this constant names.
     *
     * @return the term; never {@code null} or blank
     */
    String term();

    /**
     * A one-sentence definition of {@link #term()}.
     *
     * @return the definition; never {@code null} or blank
     */
    String definition();
}
