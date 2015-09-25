/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.component;

import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Layout;

/**
 * @author Michal
 * @since 25.9.2015
 */
public class CanvasComponent<C extends Component> extends DragAndDropWrapper {

    /**
     * Creates wrapper for given component.
     *
     * @param root Wrapped component
     */
    public CanvasComponent(C root) {
        super(root);
    }

    public C getWrappedComponent() {
        return (C) getCompositionRoot();
    }

    public void remove() {
        HasComponents parent = getParent();
        if (parent instanceof Layout) {
            ((Layout) parent).removeComponent(this);
        }
    }
}
