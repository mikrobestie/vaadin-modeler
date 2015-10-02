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
public class ComponentSelectedEvent extends ApplicationEvent {

    private CanvasComponent component;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ComponentSelectedEvent(Object source, CanvasComponent component) {
        super(source);
        this.component = component;
    }

    public CanvasComponent getComponent() {
        return component;
    }
}
