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
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Naoghuman
 */
public class SimpleJsonReader implements IJSONConfiguration {
    
    public static void main(String[] args) {
        final SimpleJsonReader reader = new SimpleJsonReader();
        final Project project = reader.read();
            
        System.out.println(project.toString());
    }
    
    private Project read() {
        final JSONParser parser = new JSONParser();
        final Project project = new Project();
        try {
            String fileName = "https://raw.githubusercontent.com/Naoghuman/ABC-List/master/release/releases.json"; // NOI18N
            URL url = new URL(fileName);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            Map<String, List<String>> headerMap = httpURLConnection.getHeaderFields();
            
            // If URL is getting 301 and 302 redirection HTTP code then get new URL link.
            // This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
            for (String header : headerMap.get(null)) {
                if (header.contains(" 302 ") || header.contains(" 301 ")) {
                    fileName = headerMap.get("Location").get(0);
                    url = new URL(fileName);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    headerMap = httpURLConnection.getHeaderFields();
                }
            }
            
            final InputStream inputStream = httpURLConnection.getInputStream();
            final Object object = parser.parse(new BufferedReader(new InputStreamReader(inputStream))); // NOI18N
            final JSONObject jsonObject = (JSONObject) object;
//            project.setAboutURL(String.valueOf(jsonObject.get(KEY__ABOUT_URL)));
//            project.setLicenseURL(String.valueOf(jsonObject.get(KEY__LICENSE_URL)));
//            project.setProjectURL(String.valueOf(jsonObject.get(KEY__PROJECT_URL)));
//            project.setWikiURL(String.valueOf(jsonObject.get(KEY__WIKI_URL)));
            
            final JSONArray jsonArray = (JSONArray) jsonObject.get(KEY__RELEASES);
            final List<String> releases = new ArrayList<>();
            jsonArray.stream()
                    .forEach(release -> {
                        releases.add(String.valueOf(release));
                    });
            project.setReleases(releases);
        } catch (FileNotFoundException ex) {
            LoggerFacade.getDefault().error(this.getClass(), "Error during reading the file 'releases.json'", ex); // NOI18N
        } catch (IOException | ParseException ex) {
            LoggerFacade.getDefault().error(this.getClass(), "Error during reading the file 'releases.json'", ex); // NOI18N
        }
        
        return project;
    }
    
}
