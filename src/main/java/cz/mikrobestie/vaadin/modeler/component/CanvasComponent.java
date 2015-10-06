/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Layout;
import com.vaadin.ui.declarative.DesignContext;
import cz.mikrobestie.vaadin.modeler.component.palette.PaletteButton;
import org.jsoup.nodes.Element;

/**
 *
 *
 * @author mikrobestie
 * @since 25.9.2015
 */
public class CanvasComponent<C extends Component> extends DragAndDropWrapper {

    public static final String STYLE_SELECTABLE = "_selectable";

    private final FontAwesome icon;

    /**
     * Creates a wrapper for given component. Wrapper is used to add drag and drop
     * functionality to components visible in canvas.
     *
     * @param root Wrapped component
     */
    public CanvasComponent(C root, FontAwesome icon) {
        super(root);
        this.icon = icon;
        addStyleName(STYLE_SELECTABLE);
        setSizeUndefined();
        setDragStartMode(DragStartMode.COMPONENT);
        if (root instanceof Layout) {
            setDropHandler(new DropHandler() {

                @Override
                public void drop(DragAndDropEvent event) {

                    // Find source component
                    Component sourceComponent = event.getTransferable().getSourceComponent();
                    CanvasComponent component;
                    if (sourceComponent instanceof PaletteButton) {
                        component = ((PaletteButton) sourceComponent).instantiate();
                    } else if (sourceComponent instanceof CanvasComponent) {
                        component = (CanvasComponent) sourceComponent;
                    } else {
                        throw new IllegalArgumentException("Source component " + sourceComponent + " is not CanvasComponent or PaletteButton");
                    }

                    // Find target component
                    ((Layout) getWrappedComponent()).addComponent(component);
                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return SourceIsTarget.get();
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    public C getWrappedComponent() {
        return (C) getCompositionRoot();
    }

    @Override
    public FontAwesome getIcon() {
        return icon;
    }

    public boolean isContainer() {
        return getWrappedComponent() instanceof ComponentContainer;
    }

    public boolean hasChildren() {
        return getWrappedComponent() instanceof Layout && ((Layout) getWrappedComponent()).getComponentCount() > 0;
    }

    public void remove() {
        HasComponents parent = getParent();
        if (parent instanceof Layout) {
            ((Layout) parent).removeComponent(this);
        }
    }

    @Override
    public void readDesign(Element design, DesignContext designContext) {
        getWrappedComponent().readDesign(design, designContext);
    }

    @Override
    public void writeDesign(Element design, DesignContext designContext) {
        getWrappedComponent().writeDesign(design, designContext);
    }
}
