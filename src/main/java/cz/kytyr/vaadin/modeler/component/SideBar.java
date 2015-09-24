/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.component;

import com.vaadin.ui.VerticalLayout;

/**
 * @author Michal
 * @since 23.9.2015
 */
public class SideBar extends VerticalLayout {

    public SideBar() {
        setWidth(300, Unit.PIXELS);
        setHeight(100, Unit.PERCENTAGE);
        setMargin(true);
        setSpacing(true);
        addComponent(new Palette());
    }
}
