package com.datateam.bd;

public class Factura {

    private Integer id;
    private String fechaFactura;
    private String folioFiscal;
    private String fechaCompra;
    private String productoComprado;
    private String nombre;
    private String correo;

    public Factura(Integer id, String fechaFactura, String folioFiscal, String fechaCompra, String productoComprado, String nombre, String correo) {
        this.id = id;
        
        this.folioFiscal = folioFiscal;
        this.fechaCompra = fechaCompra;
        this.productoComprado = productoComprado;
        this.nombre = nombre;
        this.correo = correo;
        
        String otro = fechaFactura;
        String [] parts = otro.split("-");
        String par1 = parts[0]; 
        String par2 = parts[1];
        String par3 = parts[2];
                
        this.fechaFactura = par3+"/"+par2+"/"+par1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", fechaFactura=" + fechaFactura + ", folioFiscal=" + folioFiscal + ", fechaCompra=" + fechaCompra + ", productoComprado=" + productoComprado + ", nombre=" + nombre + ", correo=" + correo + '}';
    }

    
}
