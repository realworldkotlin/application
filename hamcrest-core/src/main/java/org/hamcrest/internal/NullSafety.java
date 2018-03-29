package org.hamcrest.internal;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

public class NullSafety {
    @SuppressWarnings("unchecked")
    public static <E> List<Matcher<? super E>> nullSafe(Matcher<? super E>[] itemMatchers) {
        final List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>(itemMatchers.length);
        for (final Matcher<? super E> itemMatcher : itemMatchers) {
            matchers.add((Matcher<? super E>) (itemMatcher == null ? IsNull.nullValue() : itemMatcher));
        }
        return matchers;
    }
}
