package org.example.timetableassistant.model;

public enum Semiyear {
    SEM_1A("1A"),
    SEM_1B("1B"),
    SEM_2A("2A"),
    SEM_2B("2B"),
    SEM_3A("3A"),
    SEM_3B("3B");

    private final String value;

    Semiyear(String value) {
        assert value != null : "value must noe be null";
        this.value = value;
    }

    public String getValue() {
        assert value != null : "Semiyear value must not be null";
        return value;
    }

    public static Semiyear fromString(String value) {
        assert value != null && value.length() > 0 : "Semiyear parameter must not be null nor empty";
        for (Semiyear sem : Semiyear.values()) {
            if (sem.getValue().equals(value)) {
                return sem;
            }
        }
        throw new IllegalArgumentException("Unknown semiyear: " + value);
    }

}
