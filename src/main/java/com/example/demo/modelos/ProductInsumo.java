package com.example.demo.modelos;

public class ProductInsumo {
    private int productId;
    private int insumoId;
    private String insumoName;
    private double quantity;

    public ProductInsumo() { }

    public ProductInsumo(int productId, int insumoId, String insumoName, double quantity) {
        this.productId   = productId;
        this.insumoId    = insumoId;
        this.insumoName  = insumoName;
        this.quantity    = quantity;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getInsumoId() { return insumoId; }
    public void setInsumoId(int insumoId) { this.insumoId = insumoId; }

    public String getInsumoName() { return insumoName; }
    public void setInsumoName(String insumoName) { this.insumoName = insumoName; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}
