package de.htw_berlin.nguembawrterbuch.model;

public class data_communication {
    private String Key;

    private String name;
    private String nachricht;
    private String datum;
    private Long zeit;
    private String typ;

    public data_communication(String key, String name, String nachricht, String datum, Long zeit, String typ) {
        Key = key;
        this.name = name;
        this.nachricht = nachricht;
        this.datum = datum;
        this.zeit = zeit;
        this.typ = typ;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNachricht() {
        return nachricht;
    }

    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }

    public Long getZeit() {
        return zeit;
    }

    public void setZeit(Long zeit) {
        this.zeit = zeit;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
