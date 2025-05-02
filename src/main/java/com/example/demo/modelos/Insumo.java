package com.example.demo.modelos;

public class Insumo {
    private int id;
    private String name;
    private String unit;
    private int supplierId;

    public Insumo() {
    }

    public Insumo(int id, String name, String unit, int supplierId) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.supplierId = supplierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return name;
    }
}
