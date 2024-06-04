package smr.shop.libs.common.helper;

import java.text.Normalizer;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Slugify {


    private static final Pattern PATTERN_NON_ASCII = Pattern.compile("[^\\p{ASCII}]+");
    private static final Pattern PATTERN_HYPHEN_SEPARATOR = Pattern.compile("[\\W\\s+]+");
    private static final Pattern PATTERN_TRIM_DASH = Pattern.compile("^-|-$");

    public static String make(String text) {
        return Optional.ofNullable(text)
                .map(String::trim)
                .filter(Predicate.not(""::equals))
                .map(Slugify::normalize)
                .map(str -> PATTERN_NON_ASCII.matcher(str).replaceAll(""))
                .map(str -> PATTERN_HYPHEN_SEPARATOR.matcher(str).replaceAll("-"))
                .map(str -> PATTERN_TRIM_DASH.matcher(str).replaceAll(""))
                .map(String::toLowerCase)
                .orElse("");
    }

    private static String normalize(final String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFKD);
    }
}
