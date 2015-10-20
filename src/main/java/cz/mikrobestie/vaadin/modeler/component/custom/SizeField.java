/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component.custom;

import com.vaadin.data.Property;
import com.vaadin.server.UserError;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Michal
 * @since 16.10.2015
 */
public class SizeField extends HorizontalLayout {

    private TextField amount;
    private ComboBox unit;

    private Component component;
    private Mode mode;


    public SizeField(Component component, Mode mode) {
        this.component = component;
        this.mode = mode;

        amount = new TextField();
        amount.setNullRepresentation("");
        amount.setNullSettingAllowed(false);
        amount.setValue(mode == Mode.WIDTH ? "" + component.getWidth() : "" + component.getHeight());
        amount.addValueChangeListener(this::onSet);
        amount.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        amount.setColumns(5);

        unit = new ComboBox();
        for (Unit u : Unit.values()) {
            unit.addItem(u);
            unit.setItemCaption(u, u.getSymbol());
        }

        unit.setValue(mode == Mode.WIDTH ? component.getWidthUnits() : component.getHeightUnits());
        unit.addValueChangeListener(this::onSet);
        unit.setNullSelectionAllowed(false);
        unit.setTextInputAllowed(false);

        addComponent(amount);
        addComponent(unit);
        setCaption(mode == Mode.WIDTH ? "width" : "height");
    }

    private void onSet(Property.ValueChangeEvent valueChangeEvent) {
        amount.setComponentError(null);
        try {
            float size = Float.parseFloat(amount.getValue());
            if (mode == Mode.WIDTH) {
                component.setWidth(size, (Unit) unit.getValue());
            } else {
                component.setHeight(size, (Unit) unit.getValue());
            }
        } catch (NumberFormatException ex) {
            amount.setComponentError(new UserError("Invalid float number"));
        }
    }

    public enum Mode {
        WIDTH,
        HEIGHT;
    }
}
