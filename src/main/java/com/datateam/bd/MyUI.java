package com.datateam.bd;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.time.LocalDate;
import java.util.ArrayList;

@Theme("mytheme")
public class MyUI extends UI {

    private ArrayList<Factura> arreglo;
    private Grid<Factura> grid = new Grid<>();
    private TextField txt;
    private Label valor;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        DateField date = new DateField();
        date.setValue(LocalDate.now());

        txt = new TextField();
        txt.setPlaceholder("Filtrar por nombre");
        txt.addValueChangeListener(e -> filtrarNombre());
        txt.setValueChangeMode(ValueChangeMode.EAGER);

        Button btn = new Button("Buscar");

        llenarTabla();

        btn.addClickListener(e -> {
            System.out.println(date.getValue());
            filtrar("SELECT fecha_factura, folio_fiscal, fecha_mx, "
                    + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan"
                    + " where fecha_factura = '" + date.getValue() + "';");
        });

        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = s.crearArreglo("SELECT fecha_factura, folio_fiscal, fecha_mx, "
                + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan;");
        Integer contador = 0;
        for (int i = 0; i < arreglo.size(); i++) {
            contador++;
        }
        valor = new Label("Numero de items: " + contador);
//        Label bt = new Label("Siguiente pagina");
//        Label paginas = new Label(" de"  );
//        Label bt2 = new Label("Anterior pagina");

        
        grid.setSizeFull();
        columnas();
        final HorizontalLayout l = new HorizontalLayout();
        final HorizontalLayout l2 = new HorizontalLayout();

        l.addComponent(txt);
        l.addComponent(date);
        l.addComponent(btn);
        l2.addComponent(valor);  
//        l2.addComponent(bt);
//        l2.addComponent(paginas);
//        l2.addComponent(bt2);
        layout.addComponent(l);
        layout.addComponent(grid);
        layout.addComponent(l2);
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
    }

    public void filtrar(String consulta) {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = s.crearArreglo(consulta);
        int a = arreglo.size();
        System.out.println("arreglo " + a);
        valor.setValue("Numero de items: " + a);
        grid.setItems(arreglo);
    }

    public void filtrarNombre() {
        if (txt.getValue().equals("")) {
            DataSourcePostgreSQL s = new DataSourcePostgreSQL();
            arreglo = s.crearArreglo("SELECT fecha_factura, folio_fiscal, fecha_mx, "
                    + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan;");
            Integer contador = 0;
            for (int i = 0; i < arreglo.size(); i++) {
                contador++;
            }
            valor.setValue("Numero de items: " + contador);
            llenarTabla();
        } else {
            filtrar("SELECT fecha_factura, folio_fiscal, fecha_mx, "
                    + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan"
                    + " where medico ilike '%" + txt.getValue() + "%'");
        }
    }

    public void columnas() {
        grid.addColumn(Factura::getId).setResizable(true);;
        grid.addColumn(Factura::getFechaFactura).setCaption("Fecha de Factura").setResizable(false);
        grid.addColumn(Factura::getFechaCompra).setCaption("Fecha de Compra").setResizable(false);
        grid.addColumn(Factura::getNombre).setCaption("Nombre de Usuario").setResizable(false);
        grid.addColumn(Factura::getCorreo).setCaption("Correo").setResizable(false);
        grid.addColumn(Factura::getFolioFiscal).setCaption("Folio Fiscal").setResizable(false);
        grid.addColumn(Factura::getProductoComprado).setCaption("Producto comprado").setExpandRatio(1);
    }
}
