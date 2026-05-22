package network.ike.support;

import java.util.Optional;

/**
 * An enum whose every constant is paired with a stable string literal.
 *
 * <p>Java annotations require their arguments to be compile-time
 * constant expressions, so an enum constant cannot be passed to an
 * annotation directly. The pattern this interface names keeps both
 * forms in step: a {@code public static final String} literal is
 * declared for annotation use, and an enum constant carrying the same
 * value is declared for type-safe use in code. Implementing
 * {@code ConstantBackedEnum} makes that bridge explicit and gives
 * tooling a uniform way to recover the literal from the constant — and
 * the constant from the literal.
 *
 * @since 1
 */
public interface ConstantBackedEnum {

    /**
     * The stable string literal this enum constant is backed by.
     *
     * @return the backing literal; never {@code null}
     */
    String constant();

    /**
     * Finds the constant of {@code type} whose {@link #constant()}
     * equals {@code value}.
     *
     * @param <E>   the enum type, which must implement
     *              {@code ConstantBackedEnum}
     * @param type  the enum class to search; never {@code null}
     * @param value the backing literal to match; may be {@code null}
     * @return the matching constant, or {@link Optional#empty()} if no
     *         constant of {@code type} has that backing literal
     */
    static <E extends Enum<E> & ConstantBackedEnum> Optional<E> fromConstant(
            Class<E> type, String value) {
        for (E candidate : type.getEnumConstants()) {
            if (candidate.constant().equals(value)) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }
}
