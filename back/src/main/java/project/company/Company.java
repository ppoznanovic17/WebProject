package project.company;

import java.io.Serializable;

public class Company implements Serializable {

    private int id;
    private String name;
    private int version;

    public Company(String name) {
        this.name = name;
        version = 1;
    }

    public Company(){
        version = 1;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
