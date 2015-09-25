/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Michal
 * @since 24.9.2015
 */
public class PalettePanel extends Accordion {

    public PalettePanel() {

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(new PaletteButton<>(VerticalLayout.class, FontAwesome.ALIGN_CENTER));
        layout.addComponent(new PaletteButton<>(HorizontalLayout.class, FontAwesome.ALIGN_LEFT));

        Tab tab = addTab(layout);
        tab.setCaption("Layouts");
        setSizeFull();
        addStyleName(ValoTheme.ACCORDION_BORDERLESS);
    }
}
