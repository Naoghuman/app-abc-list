package com.github.naoghuman.abclist.json;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReleasesTest {

    Releases releases;

    @Before
    public void setUp() throws Exception {
        releases = new Releases();
    }

    @After
    public void tearDown() throws Exception {
        releases.clear();
        releases = null;
    }

    @Test
    public void testAdd() {
        assertTrue(releases.getReleases().size() == 0);

        Release release = new Release();
        releases.add(release);
        assertTrue(releases.getReleases().size() == 1);
    }

    @Test
    public void testClear() {
        Release release = new Release();
        releases.add(release);
        assertTrue(releases.getReleases().size() == 1);

        releases.clear();
        assertTrue(releases.getReleases().size() == 0);
    }

    @Test
    public void testGetNewestReleaseIsDefaultRelease() {
        assertEquals("0.0.0", releases.getNewestRelease().getRelease());
    }

    @Test
    public void testGetNewestReleaseIsDefinedNormalRelease() {
        Release release1 = new Release("0.1.0");
        releases.add(release1);

        Release release2 = new Release("1.1.1");
        releases.add(release2);

        Release newestRelease = releases.getNewestRelease();
        assertEquals("1.1.1", newestRelease.getRelease());
    }

    @Test
    public void testGetReleasesCheckSize() {
        assertTrue(releases.getReleases().size() == 0);

        Release release1 = new Release("3.2.1");
        releases.add(release1);

        Release release2 = new Release("1.2.3");
        releases.add(release2);

        assertTrue(releases.getReleases().size() == 2);
    }

    @Test
    public void testGetReleasesCheckOrder() {

        Release release = new Release("1.0.0-PRERELEASE");
        releases.add(release);

        release = new Release("2.0.0");
        releases.add(release);

        release = new Release("3.0.1");
        releases.add(release);

        release = new Release("3.0.0");
        releases.add(release);

        release = new Release("3.0.2");
        releases.add(release);

        release = new Release("1.0.0");
        releases.add(release);

        release = new Release("1.0.0-SNAPSHOT");
        releases.add(release);

        List<Release> orderedReleases = new ArrayList<>();
        orderedReleases.addAll(releases.getReleases());

        /* SOLL:
            { Release: [3.0.2] }
            { Release: [3.0.1] }
            { Release: [3.0.0] }
            { Release: [2.0.0] }
            { Release: [1.0.0] }
            { Release: [1.0.0-PRERELEASE] }
            { Release: [1.0.0-SNAPSHOT] }
         */
        assertTrue(releases.getReleases().size() == 7);

        assertEquals("3.0.2", orderedReleases.get(0).getRelease());
        assertEquals("3.0.1", orderedReleases.get(1).getRelease());
        assertEquals("3.0.0", orderedReleases.get(2).getRelease());
        assertEquals("2.0.0", orderedReleases.get(3).getRelease());
        assertEquals("1.0.0", orderedReleases.get(4).getRelease());
        assertEquals("1.0.0-PRERELEASE", orderedReleases.get(5).getRelease());
        assertEquals("1.0.0-SNAPSHOT", orderedReleases.get(6).getRelease());
    }

}
