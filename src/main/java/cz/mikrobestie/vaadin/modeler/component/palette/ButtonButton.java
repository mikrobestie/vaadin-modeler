/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component.palette;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

/**
 * Palette button creating HorizontalLayout on canvas drop.
 *
 * @author mikrobestie
 * @since 28.9.2015
 */
public class ButtonButton extends PaletteButton<Button> {

    /**
     * Initializes a component representing available type of component for use in canvas.
     */
    public ButtonButton() {
        super(Button.class, FontAwesome.ALIGN_CENTER);
    }

    @Override
    public Button instantiate() {
        Button instance = super.instantiate();
        instance.setCaption("Button");
        return instance;
    }
}
