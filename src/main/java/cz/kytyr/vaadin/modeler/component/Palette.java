/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Michal
 * @since 24.9.2015
 */
public class Palette extends Accordion {

    public Palette() {

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new PaletteButton<>(VerticalLayout.class, FontAwesome.ALIGN_CENTER));
        layout.addComponent(new PaletteButton<>(HorizontalLayout.class, FontAwesome.ALIGN_LEFT));

        Tab tab = addTab(layout);
        tab.setCaption("Layouts");
        setSizeFull();
    }
}
