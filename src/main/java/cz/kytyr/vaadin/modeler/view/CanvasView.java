/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler.view;

import com.vaadin.event.LayoutEvents;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Label;
import cz.kytyr.vaadin.modeler.component.PaletteButton;

import javax.annotation.PostConstruct;

/**
 * @author Michal
 * @since 23.9.2015
 */
@SpringView(name = "")
public class CanvasView extends DragAndDropWrapper implements View {

    public static final String STYLE_SELECTABLE = "_selectable";
    public static final String STYLE_SELECTED = "_selected";

    private CssLayout layout;
    private Component selected;


    public CanvasView() {
        super(new CssLayout());
    }

    @PostConstruct
    void init() {
        setPrimaryStyleName("canvas");
        setWidth(640, Unit.PIXELS);
        setHeight(480, Unit.PIXELS);

        layout = (CssLayout) getCompositionRoot();
        layout.addLayoutClickListener(this::onComponentSelected);
        layout.setSizeFull();

        setDropHandler(new DropHandler() {

            @Override
            public void drop(DragAndDropEvent event) {

                Component sourceComponent = event.getTransferable().getSourceComponent();
                if (sourceComponent instanceof PaletteButton) {
                    Component c = ((PaletteButton) sourceComponent).instantiate();
                    c.addStyleName(STYLE_SELECTABLE);
                    layout.addComponent(c);
                }
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        Label label = new Label("Hello world!");
        label.addStyleName(STYLE_SELECTABLE);
        layout.addComponent(label);
    }

    private void onComponentSelected(LayoutEvents.LayoutClickEvent e) {
        if (e.getClickedComponent() != selected) {
            if (selected != null) {
                selected.removeStyleName(STYLE_SELECTED);
            }
            selected = e.getClickedComponent();
            if (selected != null) {
                selected.addStyleName(STYLE_SELECTED);
            }
        }
    }
}
