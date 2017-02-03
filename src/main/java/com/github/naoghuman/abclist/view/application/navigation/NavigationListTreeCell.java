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
package com.github.naoghuman.abclist.view.application.navigation;

import com.github.naoghuman.abclist.configuration.IActionConfiguration;
import com.github.naoghuman.abclist.model.NavigationEntity;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;

/**
 *
 * @author Naoghuman
 */
public final class NavigationListTreeCell extends TreeCell<NavigationEntity> implements IActionConfiguration {

    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem menuItem = new MenuItem();
    private final MenuItem menuItem2 = new MenuItem();

    public NavigationListTreeCell() {
        contextMenu.getItems().add(menuItem);
    }

    private void configureMenuItem(NavigationEntity navigationEntity) {
        if (navigationEntity.getNavigation().getNavigationType().equals(ENavigationType.EXERCISE)) {
            menuItem.setText("Open Exercise"); // NOI18N
            menuItem.setOnAction(value -> {
                final TransferData transferData = new TransferData();
                transferData.setActionId(ACTION__APPLICATION__OPEN_EXERCISE);
                
                final long entityId = navigationEntity.getNavigation().getEntityId();
                transferData.setLong(entityId);
                
                ActionFacade.getDefault().handle(transferData);
            });

            if (contextMenu.getItems().contains(menuItem2)) {
                contextMenu.getItems().remove(menuItem2);
            }
        }

        if (navigationEntity.getNavigation().getNavigationType().equals(ENavigationType.TOPIC)) {
            menuItem2.setText("Open Topic"); // NOI18N
            menuItem2.setOnAction(value -> {
                final TransferData transferData = new TransferData();
                transferData.setActionId(ACTION__APPLICATION__OPEN_TOPIC);
                
                final long entityId = navigationEntity.getNavigation().getEntityId();
                transferData.setLong(entityId);
                
                ActionFacade.getDefault().handle(transferData);
            });
            
            if (!contextMenu.getItems().contains(menuItem2)) {
                contextMenu.getItems().add(menuItem2);
            }

            menuItem.setText("New Exercise"); // NOI18N
            menuItem.setOnAction(value -> {
                final TransferData transferData = new TransferData();
                transferData.setActionId(ACTION__APPLICATION__CREATE_NEW_EXERCISE);
                
                final long entityId = navigationEntity.getNavigation().getEntityId();
                transferData.setLong(entityId);
                
                ActionFacade.getDefault().handle(transferData);
            });
        }
    }

    private void configureMouseClick(NavigationEntity navigationEntity) {
        if (navigationEntity.getNavigation().getNavigationType().equals(ENavigationType.EXERCISE)) {
            this.setOnMouseClicked(value -> {
                final int mouseClickCount = value.getClickCount();
                if (mouseClickCount >= 2) {
                    final TransferData transferData = new TransferData();
                    transferData.setActionId(ACTION__APPLICATION__OPEN_EXERCISE);
                
                    final long entityId = navigationEntity.getNavigation().getEntityId();
                    transferData.setLong(entityId);

                    ActionFacade.getDefault().handle(transferData);
                }
            });
        }
    }

    @Override
    public void updateItem(NavigationEntity navigationEntity, boolean empty) {
        super.updateItem(navigationEntity, empty);
        
        // Check if ...
        if (navigationEntity == null) {
            this.setGraphic(null);
            this.setText(null);

            return;
        }

        // Tweak the [ListTreeCell]
        this.configureMenuItem(navigationEntity);
        this.configureMouseClick(navigationEntity);
        this.setContextMenu(!empty ? contextMenu : null);
        this.setGraphic(null);
        this.setText(!empty ? navigationEntity.getEntityConverter().getRepresentation() : null);
        
        final String tooltip = navigationEntity.getEntityConverter().getTooltip();
        if (!tooltip.isEmpty()) {
            this.setTooltip(new Tooltip(tooltip));
        }
    }
    
}
