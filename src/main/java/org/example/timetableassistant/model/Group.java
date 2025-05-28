package org.example.timetableassistant.model;

public class Group {
    private int id;
    private int number;
    private Semiyear semiyear;

    public Group(int id, int number, Semiyear semiyear) {
        assert id > 0 : "id must be greater than 0";
        assert number > 0 : "number must be greater than 0";
        assert semiyear != null : "semiyear cannot be null";

        this.id = id;
        this.number = number;
        this.semiyear = semiyear;
    }

    public int getId() {
        assertCurrentState();
        return id; }
    public int getNumber() {
        assertCurrentState();
        return number; }
    public Semiyear getSemiyear() {
        assertCurrentState();
        return semiyear; }

    public void setNumber(int number) {
        assert number > 0 : "recevied number must be greater than 0";
        this.number = number; }
    public void setSemiyear(Semiyear semiyear) {
        assert semiyear != null : "received semiyear cannot be null";
        this.semiyear = semiyear; }

    @Override
    public String toString() {
        assertCurrentState();
        return semiyear.getValue() + number;
    }

    public void assertCurrentState(){
        assert id > 0 : "id must be greater than 0";
        assert number > 0 : "number must be greater than 0";
        assert semiyear != null : "semiyear cannot be null";
    }
}
