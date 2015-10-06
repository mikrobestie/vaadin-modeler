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
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import cz.mikrobestie.vaadin.modeler.component.palette.PaletteButton;
import cz.mikrobestie.vaadin.modeler.event.RootComponentChangedEvent;
import cz.mikrobestie.vaadin.modeler.event.ComponentSelectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * @author Michal
 * @since 2.10.2015
 */
@Component
@UIScope
public class TreePanel extends Panel {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreePanel.class);

    private Tree tree;


    @Autowired
    private ApplicationEventPublisher publisher;


    public TreePanel() {
        this.tree = new Tree();
        tree.setSizeFull();
        tree.setSelectable(false);
        tree.setDragMode(Tree.TreeDragMode.NODE);
        tree.setDropHandler(new DropHandler() {

            @Override
            public void drop(DragAndDropEvent event) {
                Transferable transferable = event.getTransferable();
                AbstractSelect.AbstractSelectTargetDetails targetDetails = (AbstractSelect.AbstractSelectTargetDetails) event.getTargetDetails();
                CanvasComponent itemIdOver = (CanvasComponent) targetDetails.getItemIdOver();

                // Determine component
                CanvasComponent component;
                if (transferable.getSourceComponent() instanceof CanvasComponent) {
                    component = (CanvasComponent) transferable.getSourceComponent();
                } else if (transferable.getSourceComponent() instanceof PaletteButton) {
                    component = ((PaletteButton) transferable.getSourceComponent()).instantiate();
                } else {
                    throw new IllegalArgumentException("Drop source unknown: " + transferable.getSourceComponent());
                }

                // Determine drop target
                if (itemIdOver != null) {
                    com.vaadin.ui.Component target = itemIdOver.getWrappedComponent();
//                    if (target instanceof AbstractOrderedLayout) {
//                        switch (targetDetails.getDropLocation()) {
//                            case TOP:
//                                ((AbstractOrderedLayout) target).addComponent(component);
//                            case MIDDLE:
//                                ((AbstractOrderedLayout) target).addComponent(component);
//                                break;
//                        }
                    if (target instanceof Layout) {
                        ((Layout) target).addComponent(component);
                    }
                }
                add(itemIdOver, component);
                publisher.publishEvent(new RootComponentChangedEvent(this, getRoot()));
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
        if (event.getRoot() != getRoot()) {
            tree.removeAllItems();
            add(null, event.getRoot());
        }
    }

    public CanvasComponent getRoot() {
        Collection<?> objects = tree.rootItemIds();
        return objects.isEmpty() ? null : (CanvasComponent) objects.iterator().next();
    }

    private void add(CanvasComponent parent, CanvasComponent item) {
        if (parent == null) {
            tree.removeAllItems();
        }
        tree.addItem(item);
        tree.setItemIcon(item, item.getIcon());
        tree.setItemCaption(item, item.getWrappedComponent().getClass().getSimpleName());
        tree.setChildrenAllowed(item, item.isContainer());
        if (parent != null) {
            tree.setParent(item, parent);
            tree.expandItem(parent);
        }
    }
}
