package com.datateam.bd;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.FormLayout;

public class TablaPantalla extends VerticalLayout implements View {

    private ArrayList<Factura> arreglo;
    private ArrayList<Factura> arregloTemporal;

    private Grid<Factura> grid = new Grid<>();
    private TextField txt, nombre, correo, folio;
    private DateField date, date2;
    private Label valor, paginas;
    private Integer pagina = 1, paginaSiguiente, paginaFinal, items, fecha = 0;
    private String datos;
    private String consultaDefault = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
            + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan;";
    public static final String NAME = "Tabla";

    Button bt2, bt, btnFiltroAvanzado;
    Label lblIzquierda, lblDerecha;
    private ArrayList<Factura> filtroA;
    private ComboBox<String> compras;

    public TablaPantalla() {

        date = new DateField();
        date.setValue(LocalDate.now());

        date2 = new DateField();
        date2.setValue(LocalDate.now());

        /*date.addValueChangeListener(e
                -> filtrar("SELECT fecha_factura, folio_fiscal, fecha_mx, "
                        + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan"
                        + " where fecha_factura = '" + date.getValue() + "';")
        );*/
        items = 20;

        txt = new TextField();
        txt.setPlaceholder("Buscar");
        txt.addValueChangeListener(e -> filtrarNombre());
        txt.setValueChangeMode(ValueChangeMode.EAGER);

        Button btn = new Button("Limpiar");

        llenarTabla();

        btn.addClickListener(e -> {
            txt.setValue("");
            nombre.setValue("");
            correo.setValue("");
            folio.setValue("");
            compras.setValue("");
            filtrarNombre();
        });

        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        datos = consultaDefault;
        arreglo = s.crearArreglo(datos);
        Integer contador = 0;
        for (int i = 0; i < arreglo.size(); i++) {
            contador++;
        }
        valor = new Label("Numero de registros: " + contador);
        bt = new Button("Siguiente");
        bt.addClickListener(e -> {
            if (paginaFinal == pagina) {

            } else {
                paginaSiguiente = pagina + 1;
                cambiarPagina(pagina, paginaSiguiente, datos);
                pagina++;

                if (pagina == paginaFinal) {
                    bt.setVisible(false);
                    lblDerecha.setVisible(true);
                }
            }
        });
        bt.setStyleName("btn");

        paginas = new Label();
        bt2 = new Button("Anterior");
        bt2.addClickListener(e -> {
            if (pagina == 1) {

            } else {
                paginaSiguiente = pagina - 1;
                cambiarPagina(pagina, paginaSiguiente, datos);
                pagina--;
                botonesPag();
            }
        });

        Button salir = new Button("Cerrar Sesion");
        Label asd = new Label();

        salir.addClickListener(e -> {
            getUI().getNavigator().removeView(TablaPantalla.NAME);
            getUI().getNavigator().navigateTo(LoginPantalla.NAME);
            VaadinSession.getCurrent().setAttribute("user", null);
            Page.getCurrent().setUriFragment("");
        });

        grid.setSizeFull();
        columnas();

        final HorizontalLayout l = new HorizontalLayout();
        final HorizontalLayout l2 = new HorizontalLayout();
        Label la = new Label();
        la.setWidth("700px");
        Label la2 = new Label();
        la2.setWidth("450px");
        lblIzquierda = new Label();
        lblIzquierda.setWidth("90px");
        lblDerecha = new Label();
        lblDerecha.setWidth("90px");
        Label laa = new Label("Datos: ");
        ComboBox<Integer> combo = new ComboBox<>();
        combo.setPlaceholder("20");
        combo.setItems(10, 20, 50, 100);
        combo.setWidth("100px");
        combo.addValueChangeListener(e -> tamanoPagina(combo.getValue()));

        final HorizontalLayout l3 = new HorizontalLayout();
        btnFiltroAvanzado = new Button("Busqueda Avanzada");
        FormLayout content = new FormLayout();
        nombre = new TextField();
        nombre.setPlaceholder("Nombre");
        correo = new TextField();
        correo.setPlaceholder("Correo");
        folio = new TextField();
        folio.setPlaceholder("Folio");
        compras = new ComboBox<>();
        compras.setPlaceholder("Producto");
        compras.setItems("Extension Cuenta", "Pago anual", "Paquete", "Contratacion");
        Button btnBuscarA = new Button("Buscar");
        Button activarFechas = new Button("Elegir fecha");

        btnFiltroAvanzado.addClickListener(e -> {
            if (btnFiltroAvanzado.getCaption() == "Busqueda Avanzada") {
                content.setVisible(true);
                btnFiltroAvanzado.setCaption("Busqueda Avanzada ");
            } else {
                content.setVisible(false);
                activarFechas.setVisible(true);
                date.setVisible(false);
                date2.setVisible(false);
                fecha = 0;
                btnFiltroAvanzado.setCaption("Busqueda Avanzada");
            }
        });

        activarFechas.addClickListener(e -> {
            date.setVisible(true);
            date2.setVisible(true);
            activarFechas.setVisible(false);
            fecha = 1;
        });

        btnBuscarA.addClickListener(e -> {
            filtroAvanzado(compras.getValue());
        });

        l3.addComponent(activarFechas);
        l3.addComponent(date);
        l3.addComponent(date2);
        l3.addComponent(nombre);
        l3.addComponent(correo);
        l3.addComponent(folio);
        l3.addComponent(compras);
        content.addComponent(l3);
        content.addComponent(btnBuscarA);

        l.addComponent(txt);
        l.addComponent(btn);
        l.addComponent(btnFiltroAvanzado);
        l.addComponent(la2);
        l.addComponent(valor);
        l2.addComponent(laa);
        l2.addComponent(combo);
        l2.addComponent(la);
        l2.addComponent(bt2);
        l2.addComponent(lblIzquierda);
        l2.addComponent(paginas);
        l2.addComponent(lblDerecha);
        l2.addComponent(bt);
        addComponent(l);
        addComponent(content);
        addComponent(grid);
        addComponent(l2);
        addComponent(asd);
        addComponent(salir);

        content.setVisible(false);
        lblDerecha.setVisible(false);
        date.setVisible(false);
        date2.setVisible(false);

        paginacion();
    }

    public void tamanoPagina(Integer dato) {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arregloTemporal = this.getArreglo();
        items = dato;
        int a = arreglo.size();
        System.out.println("arreglo " + a);
        valor.setValue("Numero de registros: " + a);

        if (arreglo.size() == (arreglo.size() / items) * items) {
            paginas.setValue("Pagina: 1 / " + (a / items));
            paginaFinal = arreglo.size() / items;
        } else {
            paginas.setValue("Pagina: 1 / " + ((a / items) + 1));
            paginaFinal = (arreglo.size() / items) + 1;
        }

        if (arreglo.size() == 0) {
            paginas.setValue("Pagina: 1 / 1");
        }

        arregloTemporal = new ArrayList<>();
        if (arreglo.size() > items) {
            for (int i = 0; i < items; i++) {
                arregloTemporal.add(arreglo.get(i));
            }
        } else {
            for (int i = 0; i < arreglo.size(); i++) {
                arregloTemporal.add(arreglo.get(i));
            }
        }

        pagina = 1;

        botonesPag();

        grid.setItems(arregloTemporal);
    }

    public void paginacion() {
        pagina = 1;
        if (arreglo.size() == (arreglo.size() / items) * items) {
            paginas.setValue("Pagina: " + pagina + " / " + (arreglo.size() / items));
            paginaFinal = arreglo.size() / items;
        } else {
            paginas.setValue("Pagina: " + pagina + " / " + ((arreglo.size() / items) + 1));
            paginaFinal = (arreglo.size() / items) + 1;
        }

        botonesPag();
    }

    public void llenarTabla() {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = new ArrayList<>();
        datos = consultaDefault;
        arreglo = s.crearArreglo(datos);
        arregloTemporal = new ArrayList<>();
        for (int i = 0; i < items; i++) {
            arregloTemporal.add(arreglo.get(i));
        }

        this.setArreglo(arreglo);

        grid.setItems(arregloTemporal);
    }

    public void filtrar(String consulta) {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = s.crearArreglo(consulta);
        int a = arreglo.size();
        System.out.println("arreglo " + a);
        valor.setValue("Numero de registros: " + a);

        if (arreglo.size() == (arreglo.size() / items) * items) {
            paginas.setValue("Pagina: 1 / " + (a / items));
            paginaFinal = arreglo.size() / items;
            activar();
            botonesPag();
        } else {
            paginas.setValue("Pagina: 1 / " + ((a / items) + 1));
            paginaFinal = (arreglo.size() / items) + 1;
            activar();
            botonesPag();
        }

        if (arreglo.size() == 0) {
            paginas.setValue("Pagina: 1 / 1");
            pagina = 1;
            paginaFinal = 1;
            botonesPag();
        }

        arregloTemporal = new ArrayList<>();
        if (arreglo.size() > items) {
            for (int i = 0; i < items; i++) {
                arregloTemporal.add(arreglo.get(i));
            }
        } else {
            for (int i = 0; i < arreglo.size(); i++) {
                arregloTemporal.add(arreglo.get(i));
            }
        }

        this.setArreglo(arreglo);
        pagina = 1;
        grid.setItems(arregloTemporal);
    }

    public void cambiarPagina(Integer paginaA, Integer paginaS, String datos) {
        DataSourcePostgreSQL s = new DataSourcePostgreSQL();
        arreglo = new ArrayList<>();
        arreglo = s.crearArreglo(datos);
        arregloTemporal = new ArrayList<>();

        if (paginaS > paginaA) {
            if (arreglo.size() < (items * paginaS)) {
                for (int i = 0; i < arreglo.size(); i++) {
                    if (i >= (items * paginaA)) {
                        arregloTemporal.add(arreglo.get(i));
                    }
                }
            } else {
                for (int i = 0; i < (items * paginaS); i++) {
                    if (i >= (items * paginaA)) {
                        arregloTemporal.add(arreglo.get(i));
                    }
                }
            }
            paginacion(paginaS);
        } else {
            for (int i = 0; i < arreglo.size(); i++) {
                if (i >= (items * (paginaS - 1)) && i < (items * paginaS)) {
                    arregloTemporal.add(arreglo.get(i));
                }
            }
            paginacion(paginaS);
        }

        bt2.setVisible(true);
        lblIzquierda.setVisible(false);
        grid.setItems(arregloTemporal);
    }

    public void paginacion(Integer paginaS) {
        if (arreglo.size() == (arreglo.size() / items) * items) {
            paginas.setValue("Pagina: " + paginaS + " / " + (arreglo.size() / items));
            paginaFinal = arreglo.size() / items;
        } else {
            paginas.setValue("Pagina: " + paginaS + " / " + ((arreglo.size() / items) + 1));
            paginaFinal = (arreglo.size() / items) + 1;
        }
    }

    public void botonesPag() {
        if (pagina == paginaFinal) {
            bt.setVisible(false);
            lblDerecha.setVisible(true);
        } else {
            bt.setVisible(true);
            lblDerecha.setVisible(false);
        }

        if (pagina == 1) {
            bt2.setVisible(false);
            lblIzquierda.setVisible(true);
        } else {
            bt2.setVisible(true);
            lblIzquierda.setVisible(false);
        }

        if (paginaFinal == 1) {
            bt.setVisible(false);
            lblDerecha.setVisible(true);
        } else {
            bt.setVisible(true);
            lblDerecha.setVisible(false);
        }
    }

    public void activar() {
        bt2.setVisible(true);
        bt.setVisible(true);
        lblDerecha.setVisible(false);
        lblIzquierda.setVisible(false);
    }

    public void filtrarNombre() {
        if (txt.getValue().equals("")) {
            DataSourcePostgreSQL s = new DataSourcePostgreSQL();
            datos = consultaDefault;
            arreglo = s.crearArreglo(datos);
            Integer contador = 0;
            for (int i = 0; i < arreglo.size(); i++) {
                contador++;
            }
            valor.setValue("Numero de registros: " + contador);
            paginacion();
            pagina = 1;
            paginaFinal = (arreglo.size() / items) + 1;
            botonesPag();
            llenarTabla();
        } else {
            datos = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
                    + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan"
                    + " where medico ilike '%" + txt.getValue() + "%' or nombre_producto ilike '%"
                    + txt.getValue() + "%' or folio_fiscal ilike '%" + txt.getValue() + "%' or correo ilike '%"
                    + txt.getValue() + "%'";
            filtrar(datos);
        }
    }

    public void filtroAvanzado(String comprao) {
        String nom = nombre.getValue();
        String fol = folio.getValue();
        String email = correo.getValue();
        LocalDate fechaI = date.getValue();
        LocalDate fechaF = date2.getValue();
        String comprado = comprao;
        String v1 = "", v2 = "", v3 = "", v4 = "", v5 = "";
        Integer contador = 0;

        datos = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
                + "nombre_producto, medico, correo   FROM public.vista_factura_por_compra_plan ";

        String datos1 = "SELECT fecha_factura, folio_fiscal, fecha_mx, "
                + "nombre_producto, medico, correo FROM ";

        v1 = "where nombre_producto ilike '%" + comprado + "%'";
        v2 = "where medico ilike '%" + nom + "%'";
        v3 = "where folio_fiscal ilike '%" + fol + "%'";
        v4 = "where correo ilike '%" + email + "%'";
        v5 = "where fecha_factura >='" + fechaI + "' and fecha_factura <='" + fechaF + "'";

        if (comprado == null) {
            v1 = "where nombre_producto ilike '%%'";
        }

        if (fecha == 1) {
            if (fechaI == null || fechaF == null) {
                v5 = "";
            } else {
                v5 = "where fecha_factura >='" + fechaI + "' and fecha_factura <='" + fechaF + "'";
            }
        } else {
            v5 = "";
        }

        datos1 = datos1 + "(" + datos1 + "(" + datos1 + "(" + datos1 + "(" + (datos + v2) + ") A " + v1 + ") B " + v4 + ") C " + v3 + ") D " + v5;
        System.out.println("datos1 " + datos1);
        filtrar(datos1);

        if (fecha == 1) {

        } else {
            System.out.println("no activo fech");
        }

    }

    public void columnas() {
        grid.addColumn(Factura::getId).setWidth(46.0);
        grid.addColumn(Factura::getNombre).setCaption("Nombre de Usuario").setResizable(false);
        grid.addColumn(Factura::getCorreo).setCaption("Correo").setResizable(false);
        grid.addColumn(Factura::getFechaFactura).setCaption("Fecha de Factura").setResizable(false);
        grid.addColumn(Factura::getFechaCompra).setCaption("Fecha de Compra").setResizable(false);
        grid.addColumn(Factura::getFolioFiscal).setCaption("Folio Fiscal").setResizable(false);
        grid.addColumn(Factura::getProductoComprado).setCaption("Producto comprado").setExpandRatio(1);
    }

    public ArrayList<Factura> getArreglo() {
        return arreglo;
    }

    public void setArreglo(ArrayList<Factura> arreglo) {
        this.arreglo = arreglo;
    }

    public ArrayList<Factura> getFiltroA() {
        return filtroA;
    }

    public void setFiltroA(ArrayList<Factura> filtroA) {
        this.filtroA = filtroA;
    }

}
