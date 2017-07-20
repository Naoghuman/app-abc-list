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
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Naoghuman
 */
public class SimpleJsonWriter implements IJSONConfiguration {
    
    public static void main(String[] args) {
        final SimpleJsonWriter writer = new SimpleJsonWriter();
        writer.write();
    }
    
    private void write() {
        final JSONObject jsonObject = new JSONObject();
        final Project project = this.createDummyProject();
//        jsonObject.put(KEY__ABOUT_URL,   project.getAboutURL());
//        jsonObject.put(KEY__LICENSE_URL, project.getLicenseURL());
//        jsonObject.put(KEY__PROJECT_URL, project.getProjectURL());
//        jsonObject.put(KEY__WIKI_URL,    project.getWikiURL());
        
        // List all releases
        final JSONArray releases = new JSONArray();
        project.getReleases().stream()
                .forEach(release -> {
                    releases.add(release);
                });
        jsonObject.put(KEY__RELEASES, releases);
        
        /*
        replace placeholder in template-url with x.y.z version to catch the
        corresponding file
        
        private String downloadURL;
        private String readMeURL;
        private String tagURL;
        */
        try (FileWriter file = new FileWriter("release\\releases.json")) { // NOI18N
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException ex) {
            LoggerFacade.getDefault().error(this.getClass(), "Error during writing the file 'releases.json'", ex); // NOI18N
        }
    }
    
    private Project createDummyProject() {
        final Project project = new Project();
//        project.setAboutURL("aboutURL111111");
//        project.setLicenseURL("licenseURL1111");
//        project.setProjectURL("projectURL1111");
//        project.setWikiURL("wikiURL1111");
        
        final List<String> releases = new ArrayList<>();
        releases.add(RELEASE__0_1_0_PRERELEASE);
        releases.add(RELEASE__0_2_0);
        project.setReleases(releases);
        
        return project;
    }
    
}
