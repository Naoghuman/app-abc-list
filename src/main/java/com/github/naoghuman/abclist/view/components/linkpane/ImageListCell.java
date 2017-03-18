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

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 *
 * @author Naoghuman
 */
public class ImageListCell extends ListCell<LinkType> {

    private final ImageView view;

    ImageListCell() {
        this.setContentDisplay(ContentDisplay.LEFT);
        view = new ImageView();
    }

    @Override
    protected void updateItem(LinkType linkType, boolean empty) {
        super.updateItem(linkType, empty);

        if (linkType == null || empty) {
            this.setGraphic(null);
            this.setText(null);
        } else {
            view.setImage(linkType.getImage().get());
            this.setGraphic(view);
            
            this.setText(linkType.getType());
        }
    }
    
}
