package com.datateam.bd;

import java.util.Date;

public class Factura {

    private Date fechaFactura;
    private String folioFiscal;
    private Date fechaCompra;
    private String productoComprado;
    private String nombre;
    private String correo;

    public Factura(Date fechaFactura, String folioFiscal, Date fechaCompra, String productoComprado, String nombre, String correo) {
        this.fechaFactura = fechaFactura;
        this.folioFiscal = folioFiscal;
        this.fechaCompra = fechaCompra;
        this.productoComprado = productoComprado;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getFolioFiscal() {
        return folioFiscal;
    }

    public void setFolioFiscal(String folioFiscal) {
        this.folioFiscal = folioFiscal;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getProductoComprado() {
        return productoComprado;
    }

    public void setProductoComprado(String productoComprado) {
        this.productoComprado = productoComprado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
