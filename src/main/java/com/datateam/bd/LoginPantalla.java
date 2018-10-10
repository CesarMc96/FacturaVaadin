package com.datateam.bd;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;

public class LoginPantalla extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "";
    private ArrayList<Usuario> array;

    public LoginPantalla() {
        Panel panel = new Panel("Login");
        panel.setSizeUndefined();
        addComponent(panel);

        FormLayout content = new FormLayout();
        TextField username = new TextField("Correo");
        content.addComponent(username);
        PasswordField password = new PasswordField("Contraseña");
        content.addComponent(password);

        Button send = new Button("Enter");
        send.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                DataSourcePostgreSQL d = new DataSourcePostgreSQL();

                System.out.println(username.getValue() + " " + password.getValue());

                String contrasena;
                contrasena = MD5.convertir(password.getValue());

                array = d.buscarUsuario(String.format("SELECT id, correo, contrasena, rol, nombre, apellidos"
                        + "  FROM public.usuario where correo = '%s' and contrasena = '%s';", username.getValue(), contrasena));

                if (array.size() > 0) {
                    System.out.println(array.get(0));
                    if ("SA".equals(array.get(0).getRol())) {
                        VaadinSession.getCurrent().setAttribute("user", username.getValue());
                        getUI().getNavigator().addView(TablaPantalla.NAME, TablaPantalla.class);
                        Page.getCurrent().setUriFragment("Tabla");

                        getUI().getNavigator().navigateTo(TablaPantalla.NAME);
                    } else {
                        Notification.show("Informacion Incorrecta", Notification.Type.ERROR_MESSAGE);
                    }
                } else {
                    Notification.show("Informacion Incorrecta", Notification.Type.ERROR_MESSAGE);
                }
            }

        });
        content.addComponent(send);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    public String nombrePersona() {
        return this.array.get(0).getNombre() + " " + this.array.get(0).getApellidos();
    }

}
