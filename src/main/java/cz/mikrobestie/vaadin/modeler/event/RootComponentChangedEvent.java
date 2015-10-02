/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.event;

import cz.mikrobestie.vaadin.modeler.component.CanvasComponent;
import org.springframework.context.ApplicationEvent;

/**
 * @author Michal
 * @since 2.10.2015
 */
public class RootComponentChangedEvent extends ApplicationEvent {

    private CanvasComponent root;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RootComponentChangedEvent(Object source, CanvasComponent root) {
        super(source);
        this.root = root;
    }

    public CanvasComponent getRoot() {
        return root;
    }
}
