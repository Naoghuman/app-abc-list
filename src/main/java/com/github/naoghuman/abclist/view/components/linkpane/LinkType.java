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
package com.github.naoghuman.abclist.view.components.linkpane;

import com.github.naoghuman.abclist.images.ImageLoader;
import java.util.Optional;
import javafx.scene.image.Image;

/**
 *
 * @author Naoghuman
 */
public enum LinkType {
    
    EMAIL             ("Email",   "email.png"), // NOI18N
    FILM              ("Film",    "film.png"), // NOI18N
    FOLDER            ("Folder",  "folder.png"), // NOI18N
    HTML              ("Html",    "html.png"), // NOI18N
    IMAGE             ("Image",   "image.png"), // NOI18N
    LINK              ("Link",    "link.png"), // NOI18N
    MUSIC             ("Music",   "music.png"), // NOI18N
    NOTE              ("Note",    "note.png"), // NOI18N
    PAGE              ("Page",    "page.png"), // NOI18N
    PAGE_EXCEL        ("Excel",   "page_excel.png"), // NOI18N
    PAGE_WHITE_ACROBAT("Acrobat", "page_white_acrobat.png"), // NOI18N
    PAGE_WHITE_WORD   ("Word",    "page_white_word.png"), // NOI18N
    PHOTO             ("Photo",   "photo.png"), // NOI18N
    REPORT            ("Report",  "report.png"), // NOI18N
    SCRIPT            ("Script",  "script.png"); // NOI18N
    
    public final static Optional<LinkType> getLinkType(String imageName) {
        Optional<LinkType> optional = Optional.empty();
        
        for (LinkType value : values()) {
            if (value.getImageName().equals(imageName)) {
                optional = Optional.of(value);
                break;
            }
        }
        
        return optional;
    }
    
    private final String imageName;
    private final String type;

    private LinkType(final String type, final String imageName) {
        this.type = type;
        this.imageName = imageName;
    }
    
    public Optional<Image> getImage() {
        return ImageLoader.getDefault().load(imageName);
    }
    
    public String getImageName() {
        return imageName;
    }
    
    public String getType() {
        return type;
    }
    
}
