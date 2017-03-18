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
package com.github.naoghuman.abclist.images;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import javafx.scene.image.Image;

/**
 *
 * @author Naoghuman
 */
public final class ImageLoader {
    
    private static final Optional<ImageLoader> INSTANCE = Optional.of(new ImageLoader());

    public static final ImageLoader getDefault() {
        return INSTANCE.get();
    }
    
    private ImageLoader() {
        
    }
    
    public Optional<Image> load(String image) {
        Optional<Image> optional = Optional.empty();
        try {
            final URI uri = this.getClass().getResource(image).toURI();
            optional = Optional.ofNullable(new Image(uri.toString(), 16.0d, 16.0d, true, true, true));
        } catch (URISyntaxException ex) {
            LoggerFacade.getDefault().error(this.getClass(), "Can't load image: " + image, ex); // NOI18N
        }
        
        return optional;
    }
    
}
