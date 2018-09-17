
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
    private ArrayList arreglo;

    public DataSourcePostgreSQL() {
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.252/jul2818", "recetas", "datateam");
            this.st = this.connection.createStatement();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            Logger.getLogger(DataSourcePostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        System.out.println("Opened database successfully");
    }
    
    public Object ejecutarConsulta(String consulta) {
        System.out.println(consulta);
        ResultSet rs = null;
        try {
            rs = st.executeQuery(consulta);
            System.out.println(rs);

//            while (rs.next()) {
//                String nombre = rs.getString(0);
//                System.out.println(nombre);
//            }

            rs.close();
            st.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return (Object) rs;
    }
    
}
