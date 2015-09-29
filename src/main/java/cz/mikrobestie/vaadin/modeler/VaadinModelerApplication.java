package cz.mikrobestie.vaadin.modeler;

import com.vaadin.spring.annotation.UIScope;
import cz.mikrobestie.vaadin.modeler.component.palette.PalettePanel;
import cz.mikrobestie.vaadin.modeler.component.PropertiesPanel;
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
