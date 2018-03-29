package org.hamcrest.core;

import java.util.ArrayList;
import org.hamcrest.Matcher;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.core.StringContains.containsString;

public final class EveryTest {

    private final Matcher<Iterable<? extends String>> matcher = Every.everyItem(containsString("a"));

    @Test
    public void
    copesWithNullsAndUnknownTypes() {
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test
    public void
    matchesOnlyWhenEveryItemMatches() {
        assertMatches(matcher, asList("AaA", "BaB", "CaC"));
        assertDoesNotMatch(matcher, asList("AaA", "BXB", "CaC"));
    }

    @Test
    public void
    matchesEmptyLists() {
        assertMatches("didn't match empty list", matcher, new ArrayList<String>());
    }

    @Test
    public void
    describesItself() {
        assertDescription("every item is a string containing \"a\"", matcher);
    }

    @Test
    public void
    describesAMismatch() {
        assertMismatchDescription("an item was \"BXB\"", matcher, singletonList("BXB"));
    }
}

