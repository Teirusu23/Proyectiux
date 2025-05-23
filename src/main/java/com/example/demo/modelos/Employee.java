package com.example.demo.modelos;

public class Employee {
    private int id;
    private String name;
    private String rfc;
    private String phone;
    private String position;

    public Employee() {}

    public Employee(int id, String name, String rfc, String phone, String position) {
        this.id = id;
        this.name = name;
        this.rfc = rfc;
        this.phone = phone;
        this.position = position;
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

    public String getRfc() {
        return rfc;
    }
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
}
