/*
 * Copyright (C) 2017 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.abclist.json;

import com.github.naoghuman.abclist.configuration.IJSONConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Naoghuman
 */
public class Project implements IJSONConfiguration {
    
    private final List<String> releases = new ArrayList<>();
    
    private String aboutURL;
    private String licenseURL;
    private String projectURL;
    private String wikiURL;

    public Project() {
    }

    public String getAboutURL() {
        return aboutURL;
    }

    public void setAboutURL(String aboutURL) {
        this.aboutURL = aboutURL;
    }
    
    public String getLicenseURL() {
        return licenseURL;
    }

    public String getProjectURL() {
        return projectURL;
    }

    public String getWikiURL() {
        return wikiURL;
    }

    public List<String> getReleases() {
        return releases;
    }

    public void setLicenseURL(String licenseURL) {
        this.licenseURL = licenseURL;
    }

    public void setProjectURL(String projectURL) {
        this.projectURL = projectURL;
    }

    public void setReleases(List<String> releases) {
        this.releases.clear();
        this.releases.addAll(releases);
    }

    public void setWikiURL(String wikiURL) {
        this.wikiURL = wikiURL;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
//                .append(KEY__ABOUT_URL, this.getAboutURL())
//                .append(KEY__LICENSE_URL, this.getLicenseURL())
//                .append(KEY__PROJECT_URL, this.getProjectURL())
//                .append(KEY__WIKI_URL, this.getWikiURL())
                .append(KEY__RELEASES, this.getReleases())
                .toString();
    }
    
}
