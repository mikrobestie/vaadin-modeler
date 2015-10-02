/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component.palette;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import cz.mikrobestie.vaadin.modeler.component.CanvasComponent;

/**
 * Abstract button which can be draggen onto canvas to add a specific component.
 *
 * @author mikrobestie
 * @since 24.9.2015
 */
public abstract class PaletteButton<C extends Component> extends DragAndDropWrapper {

    private final Class<C> type;
    private final FontAwesome icon;


    /**
     * Initializes a component representing available type of component for use in canvas.
     *
     * @param type Type
     * @param icon Icon
     */
    protected PaletteButton(Class<C> type, FontAwesome icon) {
        super(new Label(icon.getHtml() + "&nbsp;" + type.getSimpleName(), ContentMode.HTML));
        setDragStartMode(DragStartMode.COMPONENT);
        setSizeUndefined();
        this.type = type;
        this.icon = icon;
    }

    /**
     * Creates new instance of component.
     *
     * @return Component instance
     */
    public CanvasComponent<C> instantiate() {
        try {

            C component = type.newInstance();
            if (component instanceof Layout) {
                component.setSizeFull();
            }

            return new CanvasComponent<>(component, icon);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create component instance", e);
        }
    }

    @Override
    public Resource getIcon() {
        return icon;
    }
}
