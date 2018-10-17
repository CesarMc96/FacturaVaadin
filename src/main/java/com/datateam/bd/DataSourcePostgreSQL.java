package com.datateam.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSourcePostgreSQL implements DataSource {

    private Connection connection;
    private Statement st;
    private ArrayList<Factura> arreglo;
    private ArrayList<Usuario> array;
    private Integer contador = 1;
    private Usuario user;

    public DataSourcePostgreSQL() {
        System.out.println("Entro");
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.253:5432/jul2818", "recetas", "datateam");
            this.st = this.connection.createStatement();
            System.out.println("Opened database successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al conectarse a la base de datos.");
        } catch (SQLException ex) {
            Logger.getLogger(DataSourcePostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

    }

    @Override
    public Object ejecutarConsulta(String consulta) {
        System.out.println(consulta);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(consulta);
            System.out.println(rs);

            while (rs.next()) {
                arreglo.add(new Factura(contador, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
                contador++;
                System.out.println(rs.getString(2));
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return (Object) rs;
    }

    public ArrayList crearArreglo(String consulta) {
        System.out.println(consulta);
        ResultSet rs = null;

        arreglo = new ArrayList();

        try {
            rs = st.executeQuery(consulta);
            System.out.println(rs);

            while (rs.next()) {
                arreglo.add(new Factura(contador, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
                contador++;
            }

            rs.close();
            st.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return arreglo;
    }

    public ArrayList buscarUsuario(String consulta) {
        System.out.println(consulta);
        ResultSet rs = null;

        array = new ArrayList();

        try {
            rs = st.executeQuery(consulta);
            System.out.println(rs);

            while (rs.next()) {
                System.out.println("Hola");
                user = new Usuario(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                array.add(user);
//                System.out.println(rs.getString(0));
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }
    
}
