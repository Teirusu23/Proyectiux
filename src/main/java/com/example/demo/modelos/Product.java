package com.example.demo.modelos;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String category;
    private String imagePath;
    private BigDecimal price;

    public Product() { }

    public Product(int id, String name, String category, String imagePath, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imagePath = imagePath;
        this.price = price;
    }

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {this.price = price; }
}
