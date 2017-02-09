package com.github.naoghuman.abclist.json;

public class VisualTestForOrderReleases {

    public static void main(String[] args) {
        Releases releases = new Releases();

        Release release = new Release("3.0.1");
        releases.add(release);

        release = new Release("2.0.0");
        releases.add(release);

        release = new Release("3.0.2");
        releases.add(release);

        release = new Release("3.0.0");
        releases.add(release);

        release = new Release("1.0.0-SNAPSHOT");
        releases.add(release);

        release = new Release("1.0.0");
        releases.add(release);

        release = new Release("1.0.0-PRERELEASE");
        releases.add(release);

        System.out.println(releases.toString());
    }

}
