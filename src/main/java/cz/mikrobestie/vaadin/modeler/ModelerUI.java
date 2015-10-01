/*
 * encoding="UTF-8", (greek letters ro psi: ρψ). Do not change this comment!!!
 * Copyright 2014 © Syntea software group a.s.
 */
package cz.mikrobestie.vaadin.modeler;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.mikrobestie.vaadin.modeler.component.palette.PalettePanel;
import cz.mikrobestie.vaadin.modeler.component.PropertiesPanel;
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

    @Autowired
    private PalettePanel palettePanel;

    @Autowired
    private PropertiesPanel propertiesPanel;


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Panel viewContainer = new Panel();
        viewContainer.setSizeFull();

        HorizontalLayout centerContentLayout = new HorizontalLayout();
        centerContentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        centerContentLayout.setPrimaryStyleName("centered-content");
        centerContentLayout.setSizeFull();
        viewContainer.setContent(centerContentLayout);

        VerticalLayout sidebar = new VerticalLayout(palettePanel, propertiesPanel);
        sidebar.setWidth(400, Unit.PIXELS);
        sidebar.setHeight(100, Unit.PERCENTAGE);
        sidebar.setMargin(true);
        sidebar.setSpacing(true);

        final HorizontalLayout root = new HorizontalLayout(viewContainer, sidebar);
        root.setSizeFull();
        root.setExpandRatio(viewContainer, 1.0f);
        setContent(root);

        Navigator navigator = new Navigator(this, centerContentLayout);
        navigator.addProvider(viewProvider);
    }
}
