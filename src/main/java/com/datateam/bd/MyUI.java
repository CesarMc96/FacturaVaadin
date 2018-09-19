package com.datateam.bd;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.time.LocalDate;

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        DateField date = new DateField();
        date.setValue(LocalDate.now());

        Button btn = new Button("Actualizar");

        btn.addClickListener(e -> {
            System.out.println(date.getValue());
            System.out.println("Hola");
            DataSourcePostgreSQL s = new DataSourcePostgreSQL();
            s.ejecutarConsulta("SELECT id, fecha_factura, folio_fiscal, fecha_mx, nombre_producto, medico, correo\n" +
"  FROM public.vista_factura_por_compra_plan;");
        });

//        Label l = new Label("HOLA");
        Grid g = new Grid();
        layout.addComponents(date);
        layout.addComponent(btn);
        layout.addComponent(g);        
//        layout.addComponent(l);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
