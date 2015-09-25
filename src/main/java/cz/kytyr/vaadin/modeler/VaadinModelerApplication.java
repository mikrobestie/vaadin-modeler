package cz.kytyr.vaadin.modeler;

import com.vaadin.spring.annotation.UIScope;
import cz.kytyr.vaadin.modeler.component.PalettePanel;
import cz.kytyr.vaadin.modeler.component.PropertiesPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VaadinModelerApplication {

    @Bean
    @UIScope
    public PalettePanel palettePanel() {
        return new PalettePanel();
    }

    @Bean
    @UIScope
    public PropertiesPanel propertiesPanel() {
        return new PropertiesPanel();
    }


    public static void main(String[] args) {
        SpringApplication.run(VaadinModelerApplication.class, args);
    }
}
