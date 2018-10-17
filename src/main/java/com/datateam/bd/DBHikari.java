package com.datateam.bd;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHikari {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:postgresql://192.168.1.253:5432/jul2818");
        config.setUsername("recetas");
        config.setPassword("datateam");
        config.setMaximumPoolSize(10);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DBHikari() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    public static void personaUsuario() throws SQLException {
        String consulta = "SELECT id, correo, contrasena, rol, nombre, apellidos FROM public.usuario";
        
        Connection con = DBHikari.getConnection();
        PreparedStatement stm = con.prepareStatement(consulta);
        ResultSet rs = stm.executeQuery();
        
        while(rs.next()){
            System.out.println(rs.getString(2));
        }
    }

}
