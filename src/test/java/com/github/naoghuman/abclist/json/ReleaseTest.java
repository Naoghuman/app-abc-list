package com.github.naoghuman.abclist.json;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReleaseTest {

    @Test
    public void testReleaseDefaultConstructor() {
        Release r = new Release();
        assertEquals("0.0.0", r.getRelease());
    }

    @Test
    public void testReleaseStringConstructor() {
        Release r = new Release("1.1.1");
        assertEquals("1.1.1", r.getRelease());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReleaseStringConstructorThrowsError() {
        new Release("1.1");
    }

    @Test
    public void testGetRelease() {
        Release r = new Release("1.1.1");
        assertEquals("1.1.1", r.getRelease());
    }

    @Test
    public void testSetRelease() {
        Release r = new Release("1.1.1");
        assertEquals("1.1.1", r.getRelease());

        r.setRelease("1.1.2");
        assertEquals("1.1.2", r.getRelease());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetReleaseThrowsError() {
        Release r = new Release("1.1.1");
        r.setRelease("");
    }

    @Test
    public void testEqualsReleaseTrue() {
        Release r1 = new Release("1.1.1");
        Release r2 = new Release("1.1.1");
        assertTrue(r1.equals(r2));
    }

    @Test
    public void testEqualsReleaseFalse() {
        Release r1 = new Release("1.1.1");
        Release r2 = new Release("1.1.2");
        assertFalse(r1.equals(r2));
    }

    @Test
    public void testCompareToNormalReleaseIsZero() {
        Release r1 = new Release("1.1.1");
        Release r2 = new Release("1.1.1");
        int compareTo = r1.compareTo(r2);
        assertEquals(0, compareTo);
    }

    @Test
    public void testCompareToNormalReleaseIsMinusOne() {
        Release r1 = new Release("1.1.2");
        Release r2 = new Release("1.1.1");
        int compareTo = r1.compareTo(r2);
        assertEquals(-1, compareTo);
    }

    @Test
    public void testCompareToExtendedReleaseIsZero() {
        Release r1 = new Release("1.1.1-SNAPSHOT");
        Release r2 = new Release("1.1.1-SNAPSHOT");
        int compareTo = r1.compareTo(r2);
        assertEquals(0, compareTo);

        r1 = new Release("1.1.1-PRERELEASE");
        r2 = new Release("1.1.1-PRERELEASE");
        compareTo = r1.compareTo(r2);
        assertEquals(0, compareTo);
    }

    @Test
    public void testCompareToExtendedReleaseIsLesserZero() {
        Release r1 = new Release("1.1.1");
        Release r2 = new Release("1.1.1-SNAPSHOT");
        int compareTo = r1.compareTo(r2);
        assertTrue(compareTo < 0);

        r1 = new Release("1.1.1");
        r2 = new Release("1.1.1-PRERELEASE");
        compareTo = r1.compareTo(r2);
        assertTrue(compareTo < 0);

        r1 = new Release("1.1.1-PRERELEASE");
        r2 = new Release("1.1.1-SNAPSHOT");
        compareTo = r1.compareTo(r2);
        assertTrue(compareTo < 0);
    }

    @Test
    public void testCompareToExtendedReleaseIsGreaterZero() {
        Release r1 = new Release("1.1.1-SNAPSHOT");
        Release r2 = new Release("1.1.1");
        int compareTo = r1.compareTo(r2);
        assertTrue(compareTo > 0);

        r1 = new Release("1.1.1-PRERELEASE");
        r2 = new Release("1.1.1");
        compareTo = r1.compareTo(r2);
        assertTrue(compareTo > 0);

        r1 = new Release("1.1.1-SNAPSHOT");
        r2 = new Release("1.1.1-PRERELEASE");
        compareTo = r1.compareTo(r2);
        assertTrue(compareTo > 0);
    }

    @Test
    public void testCompareToNormalReleaseIsPlusOne() {
        Release r1 = new Release("1.1.1");
        Release r2 = new Release("1.1.2");
        int compareTo = r1.compareTo(r2);
        assertEquals(1, compareTo);
    }

    @Test
    public void testToString() {
        Release r = new Release("1.1.1");
        assertEquals("{ Release: [1.1.1] }", r.toString());
    }

}
