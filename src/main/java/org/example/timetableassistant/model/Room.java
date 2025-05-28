package org.example.timetableassistant.model;

public class Room {
    private int id;
    private String name;
    private String type;
    private int capacity;

    public Room(int id, String name, String type, int capacity) {
        assert id > 0 : "id should be greater than 0";
        assert capacity > 0 : "capacity should be greater than 0";
        assert name != null && !name.isEmpty(): "name should be not null nor empty";
        assert type != null && !type.isEmpty(): "type should be not null nor empty";

        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
    }

    public int getId() {
        assertCurrentState();
        return id; }
    public String getName() {
        assertCurrentState();
        return name; }
    public String getType() {
        assertCurrentState();
        return type; }
    public int getCapacity() {
        assertCurrentState();
        return capacity; }

    public void setId(int id) {
        assert id > 0 : "id must be greater than 0";
        this.id = id; }
    public void setName(String name) {
        assert name != null && !name.isEmpty() : "Name cannot be empty nor null";
        this.name = name; }
    public void setType(String type) {
        assert type != null && !type.isEmpty() : "Type cannot be empty nor null";
        this.type = type; }
    public void setCapacity(int capacity) {
        assert capacity >= 0 : "Capacity must be >= 0";
        this.capacity = capacity; }

    @Override
    public String toString() {
        assertCurrentState();
        return name + " (" + type + ")";
    }

    public void assertCurrentState(){
        assert id > 0 : "id should be greater than 0";
        assert capacity > 0 : "capacity should be greater than 0";
        assert name != null && !name.isEmpty(): "name should be not null nor empty";
        assert type != null && !type.isEmpty(): "type should be not null nor empty";
    }
}
