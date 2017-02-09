package com.github.naoghuman.abclist.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Releases {

    private final static int INDEX__NEWEST_VERSION = 0;

    private final static List<Release> releases = new ArrayList<>();

    public void clear() {
        releases.clear();
    }

    public void add(Release release) {
        if (!releases.contains(release)) {
            releases.add(release);
        }
    }

    public Release getNewestRelease() {
        Release release = new Release();
        if (!this.getReleases().isEmpty()) {
            release = this.getReleases().get(INDEX__NEWEST_VERSION);
        }

        return release;
    }

    public List<Release> getReleases() {
        Collections.sort(releases);

        return releases;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        this.getReleases().stream()
                .forEach(release -> {
                    sb.append(release.toString()).append("\n"); // NOI18N
                });

        return sb.toString();
    }

}
