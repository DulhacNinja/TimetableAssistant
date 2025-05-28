package org.example.timetableassistant.model;

public class RoomType {
    private int id;
    private String name;

    // Getters and setters
    public int getId() {
        assertCurrentState();
        return id; }
    public void setId(int id) {
        assert id > 0 : "received id should be greater than 0";
        this.id = id; }
    public String getName() {
        assertCurrentState();
        return name; }
    public void setName(String name) {
        assert name != null && !name.isEmpty() : "name should not be empty";
        this.name = name; }

    public void assertCurrentState(){
        assert id > 0 : "id should be greater than 0";
        assert name != null && !name.isEmpty() : "name should not be empty";
    }
}
