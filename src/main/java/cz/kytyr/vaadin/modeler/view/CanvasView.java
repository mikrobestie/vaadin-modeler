/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.view;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import cz.kytyr.vaadin.modeler.component.CanvasComponent;
import cz.kytyr.vaadin.modeler.component.palette.PaletteButton;
import cz.kytyr.vaadin.modeler.component.PropertiesPanel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal
 * @since 23.9.2015
 */
@SpringView(name = "")
@UIScope
public class CanvasView extends DragAndDropWrapper implements View {

    public static final String STYLE_SELECTED = "_selected";

    private CssLayout layout;
    private CanvasComponent selected;
    private List<SelectionListener> selectionListeners;


    @Autowired
    private PropertiesPanel propertiesPanel;


    public CanvasView() {
        super(new CssLayout());
        setPrimaryStyleName("canvas");
        setWidth(640, Unit.PIXELS);
        setHeight(480, Unit.PIXELS);

        layout = (CssLayout) getCompositionRoot();
        layout.addLayoutClickListener(e -> {
            Component clicked = e.getClickedComponent();
            if (clicked != null) {
                if (clicked instanceof CanvasComponent) {
                    setSelectedComponent((CanvasComponent) clicked);
                } else {
                    if (clicked.getParent() instanceof CanvasComponent) {
                        setSelectedComponent((CanvasComponent) clicked.getParent());
                    } else {
                        throw new IllegalStateException("Illegal component in canvas: " + clicked);
                    }
                }
            }
        });
        layout.setSizeFull();

        setDropHandler(new DropHandler() {

            @Override
            public void drop(DragAndDropEvent event) {

                Component sourceComponent = event.getTransferable().getSourceComponent();
                if (sourceComponent instanceof PaletteButton) {
                    CanvasComponent c = ((PaletteButton) sourceComponent).instantiate();
                    layout.addComponent(c);
                    setSelectedComponent(c);
                }
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });
    }

    @PostConstruct
    void init() {
        addSelectionListener(propertiesPanel::setComponent);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    public void addSelectionListener(SelectionListener listener) {
        if (selectionListeners == null) {
            selectionListeners = new ArrayList<>();
        }
        selectionListeners.add(listener);
    }

    /**
     * Sets the currently selected component.
     *
     * @param c The component
     */
    public void setSelectedComponent(CanvasComponent c) {
        if (c != selected) {
            if (selected != null) {
                selected.removeStyleName(STYLE_SELECTED);
            }
            selected = c;
            if (selected != null) {
                selected.addStyleName(STYLE_SELECTED);
            }
            fireSelectionEvent();
        }
    }

    private void fireSelectionEvent() {
        if (selectionListeners != null) {
            selectionListeners.forEach(l -> l.componentSelected(selected));
        }
    }


    /**
     * Listener for canvas component selection event.
     */
    @FunctionalInterface
    public interface SelectionListener {

        /**
         * Called when component is selected in the canvas.
         *
         * @param component Canvas component
         */
        void componentSelected(CanvasComponent component);
    }
}
