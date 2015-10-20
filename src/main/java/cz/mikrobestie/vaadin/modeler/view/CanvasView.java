/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.view;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;
import cz.mikrobestie.vaadin.modeler.component.PropertiesPanel;
import cz.mikrobestie.vaadin.modeler.component.palette.PaletteButton;
import cz.mikrobestie.vaadin.modeler.event.ComponentSelectedEvent;
import cz.mikrobestie.vaadin.modeler.event.RootComponentChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

/**
 * @author Michal
 * @since 23.9.2015
 */
@SpringView(name = "")
public class CanvasView extends DragAndDropWrapper implements View {

    public static final String STYLE_SELECTED = "_selected";

    private CssLayout layout;
    private Component selected;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PropertiesPanel propertiesPanel;


    public CanvasView() {
        super(new Panel(new CssLayout()));
        setPrimaryStyleName("canvas");
        setWidth(640, Unit.PIXELS);
        setHeight(480, Unit.PIXELS);

        layout = (CssLayout) ((Panel) getCompositionRoot()).getContent();
        layout.addLayoutClickListener(e -> setSelectedComponent(e.getClickedComponent()));
        layout.setSizeFull();
        getCompositionRoot().setSizeFull();
        getCompositionRoot().addStyleName(ValoTheme.PANEL_BORDERLESS);

        setDropHandler(new DropHandler() {

            @Override
            public void drop(DragAndDropEvent event) {

                // Find source component
                Component sourceComponent = event.getTransferable().getSourceComponent();
                Component component;
                if (sourceComponent instanceof PaletteButton) {
                    component = ((PaletteButton) sourceComponent).instantiate();
                } else {
                    throw new IllegalArgumentException("Zdrojová komponenta " + sourceComponent + " není typu PaletteButton");
                }

                // Find target component
                DropTarget target = event.getTargetDetails().getTarget();
                Layout targetLayout = null;
                if (target == CanvasView.this) {
                    if (layout.getComponentCount() != 0) {
                        layout.removeAllComponents();
                    }
                    targetLayout = layout;
                }
                targetLayout.addComponent(component);
                setSelectedComponent(component);
                publisher.publishEvent(new RootComponentChangedEvent(this, component));
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });
    }

    @EventListener
    private void handleComponentSelected(ComponentSelectedEvent event) {
        if (event.getSource() != this) {
            setSelectedComponent(event.getComponent());
        }
    }

    @EventListener
    private void handleHierarchyChanged(RootComponentChangedEvent event) {
        if (layout.getComponentCount() == 0 || event.getRoot() != layout.getComponent(0)) {
            layout.removeAllComponents();
            layout.addComponent(event.getRoot());
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    /**
     * Sets the currently selected component.
     *
     * @param c The component
     */
    public void setSelectedComponent(Component c) {
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
        publisher.publishEvent(new ComponentSelectedEvent(this, selected));
    }
}
