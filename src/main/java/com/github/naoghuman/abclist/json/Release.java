package com.github.naoghuman.abclist.json;

public class Release implements Comparable<Release> {

    private final static int RELEASE_lENGHT = 5;

    private final static String RELEASE_DEFAULT_NUMBER = "0.0.0"; // NOI18N
    private final static String RELEASE_NUMBER_PATTERN = "\\d{1}.\\d{1}.\\d{1}"; // NOI18N
    private final static String RELEASE_SUFFIX_SNAPSHOT = "-SNAPSHOT"; // NOI18N
    private final static String RELEASE_SUFFIX_PRERELEASE = "-PRERELEASE"; // NOI18N
    private final static String RELEASE_ERROR_MESSAGE = "Parameter [release] isn't valid. Format must be [x.y.z] with optional one of following prefixes [-SNAPSHOT] or [-PRERELEASE]"; // NOI18N

    private String release;

    public Release() {
        this(RELEASE_DEFAULT_NUMBER);
    }

    public Release(String release) throws IllegalArgumentException {
        if (!this.valid(release)) {
            throw new IllegalArgumentException(RELEASE_ERROR_MESSAGE);
        }

        this.release = release;
    }

    public String getRelease() throws IllegalArgumentException {
        return release;
    }

    public void setRelease(String release) {
        if (!this.valid(release)) {
            throw new IllegalArgumentException(RELEASE_ERROR_MESSAGE);
        }

        this.release = release;
    }

    private boolean valid(String release) {
        boolean matches = false;
        if (release.length() == RELEASE_lENGHT) {
            matches = release.matches(RELEASE_NUMBER_PATTERN);
        }

        if (release.length() > RELEASE_lENGHT) {
            matches = release.endsWith(RELEASE_SUFFIX_SNAPSHOT) || release.endsWith(RELEASE_SUFFIX_PRERELEASE);
        }

        return matches;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 7;
        result = prime * result + ((this.getRelease() == null) ? 0 : this.getRelease().hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        final Release other = (Release) obj;
        if (this.getRelease() == null) {
            if (other.getRelease() != null) {
                return false;
            }
        } else if (!this.getRelease().equals(other.getRelease())) {
            return false;
        }

        return true;
    }

    /*
     * The order should be:
        { Release: [3.0.2] }
        { Release: [3.0.1] }
        { Release: [3.0.0] }
        { Release: [2.0.0] }
        { Release: [1.0.0]            } // before
        { Release: [1.0.0-PRERELEASE] } // middle
        { Release: [1.0.0-SNAPSHOT]   } // last
     */
    @Override
    public int compareTo(Release other) {
        int compareTo = this.getRelease().substring(RELEASE_lENGHT).compareTo(other.getRelease().substring(RELEASE_lENGHT));
        if (compareTo == 0) {
            compareTo = other.getRelease().compareTo(this.getRelease());
        }

        return compareTo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{ Release: ["); // NOI18N
        sb.append(this.getRelease());
        sb.append("] }"); // NOI18N

        return sb.toString();
    }

}
