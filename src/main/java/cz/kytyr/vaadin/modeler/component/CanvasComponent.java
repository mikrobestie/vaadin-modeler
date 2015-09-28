/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.component;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Layout;
import cz.kytyr.vaadin.modeler.component.palette.PaletteButton;

/**
 *
 *
 * @author mikrobestie
 * @since 25.9.2015
 */
public class CanvasComponent<C extends Component> extends DragAndDropWrapper {

    public static final String STYLE_SELECTABLE = "_selectable";


    /**
     * Creates a wrapper for given component. Wrapper is used to add drag and drop
     * functionality to components visible in canvas.
     *
     * @param root Wrapped component
     */
    public CanvasComponent(C root) {
        super(root);
        addStyleName(STYLE_SELECTABLE);
        setSizeUndefined();
        setDragStartMode(DragStartMode.COMPONENT);
        if (root instanceof Layout) {
            setDropHandler(new DropHandler() {

                @Override
                public void drop(DragAndDropEvent event) {

                    Component sourceComponent = event.getTransferable().getSourceComponent();
                    if (sourceComponent instanceof PaletteButton) {
                        CanvasComponent c = ((PaletteButton) sourceComponent).instantiate();
                        ((Layout) getWrappedComponent()).addComponent(c);
                        // setSelectedComponent(c);
                    } else if (sourceComponent instanceof CanvasComponent) {
                        ((Layout) getWrappedComponent()).addComponent(sourceComponent);
                    }
                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
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
