package com.example.demo.modelos;

public class Table {
    private int id;

    public Table() {}
    public Table(int id) { this.id = id; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}