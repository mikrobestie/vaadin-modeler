/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component.palette;

import com.vaadin.ui.Accordion;
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
        layout.addComponent(new VerticalLayoutButton());
        layout.addComponent(new HorizontalLayoutButton());
        layout.addComponent(new ButtonButton());

        Tab tab = addTab(layout);
        tab.setCaption("Layouts");
        setSizeFull();
        addStyleName(ValoTheme.ACCORDION_BORDERLESS);
    }
}
