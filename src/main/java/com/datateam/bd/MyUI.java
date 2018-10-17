package com.datateam.bd;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        new Navigator(this, this);
        
        getNavigator().addView(LoginPantalla.NAME, LoginPantalla.class);
        getNavigator().setErrorView(LoginPantalla.class);
     
        Page.getCurrent().addUriFragmentChangedListener((Page.UriFragmentChangedEvent event) -> {
            router(event.getUriFragment());
        });

        router("");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    private void router(String route) {
        System.out.println(route);
        if (getSession().getAttribute("user") != null) {
            getNavigator().addView(TablaPantalla.NAME, TablaPantalla.class);
            if (route.equals("Tabla")) {
                getNavigator().navigateTo(TablaPantalla.NAME);
            } else {
                getNavigator().navigateTo(TablaPantalla.NAME);
            }
        } else {
            getNavigator().navigateTo(LoginPantalla.NAME);
        }
    }
}
