package com.datateam.bd;

import java.util.Date;

public class Factura {

    private String fechaFactura;
    private String folioFiscal;
    private String fechaCompra;
    private String productoComprado;
    private String nombre;
    private String correo;

    public Factura(String fechaFactura, String folioFiscal, String fechaCompra, String productoComprado, String nombre, String correo) {
        this.fechaFactura = fechaFactura;
        this.folioFiscal = folioFiscal;
        this.fechaCompra = fechaCompra;
        this.productoComprado = productoComprado;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getFolioFiscal() {
        return folioFiscal;
    }

    public void setFolioFiscal(String folioFiscal) {
        this.folioFiscal = folioFiscal;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
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
