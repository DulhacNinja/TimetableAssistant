package org.example.timetableassistant.model;

public class Discipline {
    private int id;
    private String name;

    public Discipline(int id, String name) {
        assert id > 0 : "id must be greater than 0";
        assert name != null  && !name.isEmpty(): "name must be non-null";

        this.id = id;
        this.name = name;
    }

    public Discipline() {

    }

    public int getId() {
        assertCurrentState();
        return id; }
    public String getName() {
        assertCurrentState();
        return name; }

    public void setId(int id) {
        assert id > 0 : "id must be greater than 0";
        this.id = id; }
    public void setName(String name) {
        assert name != null  && !name.isEmpty(): "name must be non-null";
        this.name = name; }

    @Override
    public String toString() {
        assertCurrentState();
        return this.getName(); // or whatever method returns the teacher's name
    }

    public void assertCurrentState(){
        assert id > 0 : "id must be greater than 0";
        assert name != null  && !name.isEmpty(): "name must be non-null";
    }
}
