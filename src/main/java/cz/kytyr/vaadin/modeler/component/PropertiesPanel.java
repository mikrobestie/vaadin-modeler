/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Michal
 * @since 25.9.2015
 */
public class PropertiesPanel extends Panel {

    private VerticalLayout layout = new VerticalLayout();

    public PropertiesPanel() {

        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        setCaption("Properties");
        setSizeFull();
    }

    /**
     * Sets the component for the property editor.
     *
     * @param cc Component
     */
    public void setComponent(CanvasComponent cc) {
        layout.removeAllComponents();
        if (cc != null) {

            Button butRemove = new Button("Remove");
            butRemove.addStyleName(ValoTheme.BUTTON_DANGER);
            butRemove.addClickListener(event -> cc.remove());

            layout.addComponent(butRemove);
            layout.setComponentAlignment(butRemove, Alignment.MIDDLE_CENTER);
        }
    }
}
