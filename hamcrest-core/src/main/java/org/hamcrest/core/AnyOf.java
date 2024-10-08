package org.hamcrest.core;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Calculates the logical disjunction of multiple matchers. Evaluation is shortcut, so
 * subsequent matchers are not called if an earlier matcher returns <code>true</code>.
 */
public class AnyOf<T> extends ShortcutCombination<T> {

    public AnyOf(Iterable<Matcher<? super T>> matchers) {
        super(matchers);
    }

    @Override
    public boolean matches(Object o) {
        return matches(o, true);
    }

    @Override
    public void describeTo(Description description) {
        describeTo(description, "or");
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> matchers) {
        return new AnyOf<>(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     */
    @SafeVarargs
    public static <T> AnyOf<T> anyOf(Matcher<? super T>... matchers) {
        List<Matcher<? super T>> list = Arrays.asList(matchers);
        return anyOf(list);
    }
}
