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
import java.util.ArrayList;

@Theme("mytheme")
public class MyUI extends UI {

    private ArrayList<Factura> arreglo;
    private Grid<Factura> grid = new Grid<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        DateField date = new DateField();
        date.setValue(LocalDate.now());

        Button btn = new Button("Actualizar");

        btn.addClickListener(e -> {
            System.out.println(date.getValue());
        });

        llenarTabla();
        grid.setSizeFull();
        
        layout.addComponents(date);
        layout.addComponent(btn);
        layout.addComponent(grid);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public void llenarTabla() {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = s.crearArreglo("SELECT fecha_factura, folio_fiscal, fecha_mx, "
                + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan;");
        grid.setItems(arreglo);
        grid.addColumn(Factura::getFechaFactura).setCaption("Fecha de Factura").setResizable(false);
        grid.addColumn(Factura::getFolioFiscal).setCaption("Folio Fiscal").setResizable(false);
        grid.addColumn(Factura::getFechaCompra).setCaption("Fecha de Compra").setResizable(false);
        grid.addColumn(Factura::getProductoComprado).setCaption("Producto comprado").setResizable(false);
        grid.addColumn(Factura::getNombre).setCaption("Nombre de Usuario").setResizable(false);
        grid.addColumn(Factura::getCorreo).setCaption("Correo").setResizable(false);

    }
}
