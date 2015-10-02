/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler.component;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import cz.mikrobestie.vaadin.modeler.component.palette.PaletteButton;
import cz.mikrobestie.vaadin.modeler.event.ComponentSelectedEvent;
import cz.mikrobestie.vaadin.modeler.event.RootComponentChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Michal
 * @since 2.10.2015
 */
@Component
@UIScope
public class TreePanel extends Panel {

    private Tree tree;


    @Autowired
    private ApplicationEventPublisher publisher;


    public TreePanel() {
        this.tree = new Tree();
        tree.setSizeFull();
        tree.setDragMode(Tree.TreeDragMode.NODE);
        tree.setDropHandler(new DropHandler() {

            @Override
            public void drop(DragAndDropEvent event) {
                Transferable transferable = event.getTransferable();
                if (transferable.getSourceComponent() instanceof CanvasComponent) {
                    CanvasComponent component = (CanvasComponent) transferable.getSourceComponent();
                    add(null, component);
                } else if (transferable.getSourceComponent() instanceof PaletteButton) {
                    PaletteButton button = (PaletteButton) transferable.getSourceComponent();
                    add(null, button.instantiate());
                }
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });
        setContent(tree);
    }

    @PostConstruct
    private void init() {
        tree.addItemClickListener(e -> publisher.publishEvent(new ComponentSelectedEvent(this, (CanvasComponent) e.getItemId())));
    }

    @EventListener
    private void handleComponentSelected(ComponentSelectedEvent event) {
        if (event.getSource() != this) {
            tree.setValue(event.getComponent());
        }
    }

    @EventListener
    private void handleRootComponentChanged(RootComponentChangedEvent event) {
        if (event.getSource() != this) {
            add(null, event.getRoot());
        }
    }

    private void add(CanvasComponent parent, CanvasComponent item) {
        if (parent == null) {
            tree.removeAllItems();
        }
        tree.addItem(item);
        tree.setItemIcon(item, item.getIcon());
        tree.setItemCaption(item, item.getWrappedComponent().getClass().getSimpleName());
        tree.setChildrenAllowed(item, item.hasChidlren());
        if (parent != null) {
            tree.setParent(item, parent);
        }
    }
}
