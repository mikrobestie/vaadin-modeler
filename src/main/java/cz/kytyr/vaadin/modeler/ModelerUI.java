/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.kytyr.vaadin.modeler;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import cz.kytyr.vaadin.modeler.component.SideBar;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Main UI.
 *
 * @author mikrobestie
 * @since 23.9.2015
 */
@SpringUI
@Theme("modeler")
public class ModelerUI extends UI {

    // we can use either constructor autowiring or field autowiring
    @Autowired
    private SpringViewProvider viewProvider;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        setContent(root);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();

        HorizontalLayout centerContentLayout = new HorizontalLayout();
        centerContentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        centerContentLayout.setPrimaryStyleName("centered-content");
        centerContentLayout.setSizeFull();
        viewContainer.setContent(centerContentLayout);

        root.addComponent(viewContainer);
        root.addComponent(new SideBar());
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, centerContentLayout);
        navigator.addProvider(viewProvider);
    }
}
