package com.example.demo.modelos;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int clienteId;
    private int tableId;
    private LocalDateTime dateTime;
    private int persons;
    private String notes;

    public Reservation() {}
    public Reservation(int id, int clienteId, int tableId,
                       LocalDateTime dateTime, int persons, String notes) {
        this.id = id;
        this.clienteId = clienteId;
        this.tableId = tableId;
        this.dateTime = dateTime;
        this.persons = persons;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public int getPersons() { return persons; }
    public void setPersons(int persons) { this.persons = persons; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
