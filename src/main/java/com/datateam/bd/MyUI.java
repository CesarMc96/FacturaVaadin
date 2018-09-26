package com.datateam.bd;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
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
    private ArrayList<Factura> arregloTemporal;

    private Grid<Factura> grid = new Grid<>();
    private TextField txt;
    private Label valor, paginas;
    private Integer pagina = 1, paginaSiguiente, paginaFinal;
    private String datos;

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
        datos = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
                + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan;";
        arreglo = s.crearArreglo(datos);
        Integer contador = 0;
        for (int i = 0; i < arreglo.size(); i++) {
            contador++;
        }
        valor = new Label("Numero de registros totales: " + contador);
        Button bt = new Button("Siguiente");
        bt.addClickListener(e -> {
            if (paginaFinal == pagina) {

            } else {
                paginaSiguiente = pagina + 1;
                cambiarPagina(pagina, paginaSiguiente, datos);
                pagina++;
            }
        });

        paginas = new Label();
        Button bt2 = new Button("Anterior");
        bt2.addClickListener(e -> {
            if (pagina == 1) {

            } else {
                paginaSiguiente = pagina - 1;
                cambiarPagina(pagina, paginaSiguiente,datos);
                pagina--;
            }
        });

        if (arreglo.size() == arreglo.size() / 20) {
            paginas.setValue("Pagina: " + pagina + " / " + (arreglo.size() / 20));
            paginaFinal = arreglo.size() / 20;
        } else {
            paginas.setValue("Pagina: " + pagina + " / " + ((arreglo.size() / 20) + 1));
            paginaFinal = (arreglo.size() / 20) + 1;

        }

        grid.setSizeFull();
        columnas();
        final HorizontalLayout l = new HorizontalLayout();
        final HorizontalLayout l2 = new HorizontalLayout();
        Label la = new Label();
        la.setWidth("600px");

        l.addComponent(txt);
        l.addComponent(date);
        l.addComponent(btn);
        l2.addComponent(valor);
        l2.addComponent(la);
        l2.addComponent(bt2);
        l2.addComponent(paginas);
        l2.addComponent(bt);
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
        arreglo = new ArrayList<>();
        datos = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
                + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan;";
        arreglo = s.crearArreglo(datos);
        arregloTemporal = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arregloTemporal.add(arreglo.get(i));
        }

        grid.setItems(arregloTemporal);
    }
    
    public void filtrar(String consulta) {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = s.crearArreglo(consulta);
        int a = arreglo.size();
        System.out.println("arreglo " + a);
        valor.setValue("Numero de items: " + a);

        if (arreglo.size() == arreglo.size() / 20) {
            paginas.setValue("Pagina: 1 / " + (a / 20));
            paginaFinal = arreglo.size() / 20;
        } else {
            paginas.setValue("Pagina: 1 / " + ((a / 20) + 1));
            paginaFinal = (arreglo.size() / 20) + 1;
        }

        if (arreglo.size() == 0) {
            paginas.setValue("Pagina: 1 / 1");
        }

        arregloTemporal = new ArrayList<>();
        if (arreglo.size() > 20) {
            for (int i = 0; i < 20; i++) {
                arregloTemporal.add(arreglo.get(i));
            }
        } else {
            for (int i = 0; i < arreglo.size(); i++) {
                arregloTemporal.add(arreglo.get(i));
            }
        }

        grid.setItems(arregloTemporal);
    }
    
    public void cambiarPagina(Integer paginaA, Integer paginaS, String datos) {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = new ArrayList<>();
        arreglo = s.crearArreglo(datos);
        arregloTemporal = new ArrayList<>();

        if (paginaS > paginaA) {

            if (arreglo.size() < (20 * paginaS)) {
                for (int i = 0; i < arreglo.size(); i++) {
                    if (i >= (20 * paginaA)) {
                        arregloTemporal.add(arreglo.get(i));
                    }
                }
            } else {
                for (int i = 0; i < (20 * paginaS); i++) {
                    if (i >= (20 * paginaA)) {
                        arregloTemporal.add(arreglo.get(i));
                    }
                }
            }
            if (arreglo.size() == arreglo.size() / 20) {
                paginas.setValue("Pagina: " + paginaS + " / " + (arreglo.size() / 20));
            } else {
                paginas.setValue("Pagina: " + paginaS + " / " + ((arreglo.size() / 20) + 1));
            }

        } else {
            for (int i = 0; i < arreglo.size(); i++) {
                if (i >= (20 * (paginaS - 1)) && i < (20 * paginaS)) {
                    arregloTemporal.add(arreglo.get(i));
                }
            }

            if (arreglo.size() == arreglo.size() / 20) {
                paginas.setValue("Pagina: " + paginaS + " / " + (arreglo.size() / 20));
            } else {
                paginas.setValue("Pagina: " + paginaS + " / " + ((arreglo.size() / 20) + 1));
            }
        }

        grid.setItems(arregloTemporal);
    }


    public void filtrarNombre() {
        if (txt.getValue().equals("")) {
            DataSourcePostgreSQL s = new DataSourcePostgreSQL();
            datos = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
                    + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan;";
            arreglo = s.crearArreglo(datos);
            Integer contador = 0;
            for (int i = 0; i < arreglo.size(); i++) {
                contador++;
            }
            valor.setValue("Numero de items: " + contador);
            paginas.setValue("Pagina: 1 / " + ((arreglo.size() / 20) + 1));
            pagina = 1;
            paginaFinal = (arreglo.size() / 20) + 1;
            llenarTabla();
        } else {
            datos = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
                    + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan"
                    + " where medico ilike '%" + txt.getValue() + "%'";
            filtrar(datos);
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
