/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import cz.mikrobestie.vaadin.modeler.component.custom.FormCheckBox;
import cz.mikrobestie.vaadin.modeler.component.custom.SizeField;
import cz.mikrobestie.vaadin.modeler.event.ComponentSelectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal
 * @since 25.9.2015
 */
@org.springframework.stereotype.Component
@UIScope
public class PropertiesPanel extends Panel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesPanel.class);

    private FormLayout layout;
    private Component component;
    private BeanFieldGroup bfg = null;

    public PropertiesPanel() {

        layout = new FormLayout();
        layout.setSpacing(true);
        layout.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        setContent(layout);
        setCaption("Properties");
        setSizeFull();
    }

    /**
     * Sets the component for the property editor.
     *
     * @param cc Component
     */
    public void setComponent(Component cc) {
        this.component = cc;
        layout.removeAllComponents();
        if (cc != null) {

            setCaption(component.getClass().getSimpleName() + " properties");

            // Size
            layout.addComponent(new SizeField(cc, SizeField.Mode.WIDTH));
            layout.addComponent(new SizeField(cc, SizeField.Mode.HEIGHT));

            bfg = new BeanFieldGroup(component.getClass());
            bfg.setItemDataSource(component);
            bfg.setBuffered(false);
            try {

                for (Method method : component.getClass().getMethods()) {
                    if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                        String propertyId = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
                        Field<?> field = createField(propertyId, method.getParameterTypes()[0]);
                        if (field != null) {
                            bfg.bind(field, propertyId);
                            layout.addComponent(field);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Error exploring properties", e);
            }

            Button butRemove = new Button("Remove");
            butRemove.addStyleName(ValoTheme.BUTTON_DANGER);
            butRemove.addClickListener(event -> cc.setParent(null));

            layout.addComponent(butRemove);
            layout.setComponentAlignment(butRemove, Alignment.MIDDLE_CENTER);
        }
    }

    public Component getComponent() {
        return component;
    }

    @EventListener
    private void handleComponentSelected(ComponentSelectedEvent event) {
        if (event.getSource() != this) {
            setComponent(event.getComponent());
        }
    }

    private Field<?> createField(String propertyId, Class<?> type) {

        Field<?> field;
        switch (propertyId) {

            case "caption":
            case "description":
            case "iconAlternateText":
            case "id":
            case "inputPrompt":
            case "primaryStyleName":
            case "styleName":
            case "width":

                field = new TextField();
                break;

            case "margin":
                return null;

            case "captionAsHtml":
            case "disableOnClick":
            case "enabled":
            case "htmlContentAllowed":
            case "immediate":
            case "readOnly":
            case "responsive":
            case "spacing":
            case "visible":

                field = new FormCheckBox();
                break;

            case "data":
            case "errorHandler":
            case "parent":
                return null;

            case "defaultComponentAlignment":
                ComboBox combo = new ComboBox("Default comp. alignment");
                combo.addItem(Alignment.TOP_LEFT);
                combo.setItemCaption(Alignment.TOP_LEFT, "TOP_LEFT");
                combo.addItem(Alignment.TOP_CENTER);
                combo.setItemCaption(Alignment.TOP_CENTER, "TOP_CENTER");
                combo.addItem(Alignment.TOP_RIGHT);
                combo.setItemCaption(Alignment.TOP_RIGHT, "TOP_RIGHT");
                combo.addItem(Alignment.MIDDLE_LEFT);
                combo.setItemCaption(Alignment.MIDDLE_LEFT, "MIDDLE_LEFT");
                combo.addItem(Alignment.MIDDLE_CENTER);
                combo.setItemCaption(Alignment.MIDDLE_CENTER, "MIDDLE_CENTER");
                combo.addItem(Alignment.MIDDLE_RIGHT);
                combo.setItemCaption(Alignment.MIDDLE_RIGHT, "MIDDLE_RIGHT");
                combo.addItem(Alignment.BOTTOM_LEFT);
                combo.setItemCaption(Alignment.BOTTOM_LEFT, "BOTTOM_LEFT");
                combo.addItem(Alignment.BOTTOM_CENTER);
                combo.setItemCaption(Alignment.BOTTOM_CENTER, "BOTTOM_CENTER");
                combo.addItem(Alignment.BOTTOM_RIGHT);
                combo.setItemCaption(Alignment.BOTTOM_RIGHT, "BOTTOM_RIGHT");
                combo.setTextInputAllowed(false);
                combo.setNullSelectionAllowed(false);
                combo.addValueChangeListener(e -> {

                    // Re-add inner components to take effect
                    Layout layout = (Layout) getComponent();
                    List<Component> contents = new ArrayList<>();
                    for (Component component : layout) {
                        contents.add(component);
                    }
                    layout.removeAllComponents();
                    for (Component content : contents) {
                        layout.addComponent(content);
                    }
                });
                field = combo;
                break;

            case "icon":
                ComboBox cmbIcon = new ComboBox();
                for (FontAwesome fontAwesome : FontAwesome.values()) {
                    cmbIcon.addItem(fontAwesome);
                    cmbIcon.setItemIcon(fontAwesome, fontAwesome);
                }
                field = cmbIcon;
                break;

            default:

                LOGGER.warn("Cannot create field for propertyId " + propertyId);
                field = null;
                break;
        }

        if (field instanceof AbstractTextField) {
            ((AbstractTextField) field).setNullSettingAllowed(true);
            ((AbstractTextField) field).setNullRepresentation("");
        }
        if (field instanceof AbstractComponent) {
            ((AbstractComponent) field).setImmediate(true);
        }
        if (field != null) {
            field.setCaption(propertyId);
            field.addValueChangeListener(e -> {
                try {
                    bfg.commit();
                } catch (FieldGroup.CommitException e1) {
                }
            });
        }
        return field;
    }
}
