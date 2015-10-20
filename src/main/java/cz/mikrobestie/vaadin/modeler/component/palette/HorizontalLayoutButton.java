/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component.palette;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.HorizontalLayout;

/**
 * Palette button creating HorizontalLayout on canvas drop.
 *
 * @author mikrobestie
 * @since 28.9.2015
 */
public class HorizontalLayoutButton extends PaletteButton<HorizontalLayout> {

    /**
     * Initializes a component representing available type of component for use in canvas.
     */
    public HorizontalLayoutButton() {
        super(HorizontalLayout.class, FontAwesome.ALIGN_CENTER);
    }

    @Override
    public HorizontalLayout instantiate() {
        HorizontalLayout instance = super.instantiate();
        instance.setWidth(100, Unit.PIXELS);
        instance.setHeight(100, Unit.PERCENTAGE);
        return instance;
    }
}
